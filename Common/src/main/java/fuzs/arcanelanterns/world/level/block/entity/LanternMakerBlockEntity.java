package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundCraftLanternParticlesMessage;
import fuzs.arcanelanterns.world.item.crafting.LanternMakingRecipe;
import fuzs.puzzleslib.util.ContainerImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class LanternMakerBlockEntity extends BlockEntity implements ContainerImpl {
    private final RecipeManager.CachedCheck<Container, LanternMakingRecipe> quickCheck;
    private final NonNullList<ItemStack> items = NonNullList.withSize(16, ItemStack.EMPTY);

    public LanternMakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.LANTERN_MAKER_BLOCK_ENTITY.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(ModRegistry.LANTERN_MAKING_RECIPE_TYPE.get());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LanternMakerBlockEntity blockEntity) {
        BlockState above = level.getBlockState(pos.above());
        if (above.is(Blocks.LANTERN) || above.is(Blocks.SOUL_LANTERN)) {
            ItemStack result = blockEntity.quickCheck.getRecipeFor(blockEntity, level).map(recipe -> recipe.assemble(blockEntity)).orElse(ItemStack.EMPTY);
            if (!result.isEmpty()) {
                for (ItemStack stack : blockEntity.items) {
                    if (!stack.isEmpty()) stack.shrink(1);
                }
                blockEntity.setChanged();
                level.destroyBlock(pos.above(), false);
                dropItemStack(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, result);
                ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundCraftLanternParticlesMessage(pos), pos, level);
            } else {
                level.destroyBlock(pos.above(), true);
            }
        }
    }

    public static void dropItemStack(Level level, double posX, double posY, double posZ, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(level, posX, posY, posZ, stack);
        itemEntity.setDeltaMovement(Vec3.ZERO);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
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
