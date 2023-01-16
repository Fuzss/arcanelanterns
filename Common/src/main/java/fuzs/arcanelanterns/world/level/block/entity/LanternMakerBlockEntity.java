package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundCraftLanternParticlesMessage;
import fuzs.arcanelanterns.world.item.crafting.LanternMakingRecipe;
import fuzs.puzzleslib.util.ContainerImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class LanternMakerBlockEntity extends BlockEntity implements ContainerImpl {
    private final RecipeManager.CachedCheck<Container, LanternMakingRecipe> quickCheck;
    private final NonNullList<ItemStack> items = NonNullList.withSize(16, ItemStack.EMPTY);

    public LanternMakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.LANTERN_MAKER_BLOCK_ENTITY.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(ModRegistry.LANTERN_MAKING_RECIPE_TYPE.get());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LanternMakerBlockEntity blockEntity) {
        if (level.getBlockState(pos.above()).getBlock() instanceof LanternBlock) {
            ItemStack[] stacks = getRecipeStacks(blockEntity);
            if (stacks.length > 0) {
                ItemStack result = assembleRecipe(level, blockEntity, stacks);
                if (attemptCrafting(level, pos, blockEntity, stacks, result)) {
                    return;
                }
            }
            level.destroyBlock(pos.above(), true);
        }
    }

    private static ItemStack[] getRecipeStacks(LanternMakerBlockEntity blockEntity) {
        if (!blockEntity.items.isEmpty()) {
            ItemStack[] itemStacks = blockEntity.items.stream().filter(Predicate.not(ItemStack::isEmpty)).toArray(ItemStack[]::new);
            if (itemStacks.length > 0) {
                return itemStacks;
            }
        }
        return new ItemStack[0];
    }

    private static ItemStack assembleRecipe(Level level, LanternMakerBlockEntity blockEntity, ItemStack[] itemStacks) {
        Container container = new SimpleContainer(itemStacks);
        return blockEntity.quickCheck.getRecipeFor(container, level).map(recipe -> recipe.assemble(container)).orElse(ItemStack.EMPTY);
    }

    private static boolean attemptCrafting(Level level, BlockPos pos, LanternMakerBlockEntity blockEntity, ItemStack[] itemStacks, ItemStack result) {
        if (!result.isEmpty()) {
            itemStacks = Stream.of(itemStacks).peek(stack -> stack.shrink(1)).filter(Predicate.not(ItemStack::isEmpty)).toArray(ItemStack[]::new);
            blockEntity.items = NonNullList.of(ItemStack.EMPTY, itemStacks);
            blockEntity.setChanged();
            level.destroyBlock(pos.above(), false);
            Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, result);
            ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundCraftLanternParticlesMessage(pos.above()), pos, level);
            return true;
        }
        return false;
    }

    @Override
    public NonNullList<ItemStack> items() {
        return this.items;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}
