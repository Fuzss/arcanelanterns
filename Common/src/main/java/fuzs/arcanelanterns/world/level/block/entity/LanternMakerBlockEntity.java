package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundCraftLanternMessage;
import fuzs.puzzleslib.util.ContainerImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicBoolean;

public class LanternMakerBlockEntity extends BlockEntity implements ContainerImpl {
    private final NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);

    public LanternMakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.LANTERN_MAKER_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LanternMakerBlockEntity blockEntity) {
        if (!(level.getBlockState(pos.above()).getBlock() instanceof LanternBlock)) return;
        String recipe = blockEntity.items().toString();
        if (recipe.contains("egg") && recipe.contains("melon") && recipe.contains("bone_meal") && recipe.contains("golden_apple") && recipe.contains("sugar_cane")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.LIFE_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("glowstone") && recipe.contains("jack_o_lantern") && recipe.contains("blaze_powder") && recipe.contains("gold_ingot") && recipe.contains("fire_charge")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.FERAL_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("diamond") && recipe.contains("golden_carrot") && recipe.contains("beetroot") && recipe.contains("rabbit_foot") && recipe.contains("honey_bottle") && recipe.contains("life_lantern")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.LOVE_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("ghast_tear") && recipe.contains("warped_roots") && recipe.contains("flint") && recipe.contains("pufferfish") && recipe.contains("ink_sac")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.WAILING_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("cobweb") && recipe.contains("snowball") && recipe.contains("packed_ice") && recipe.contains("quartz")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.BOREAL_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("shulker_shell") && recipe.contains("paper") && recipe.contains("snowball") && recipe.contains("phantom_membrane")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.BRILIANT_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("warped_fungus") && recipe.contains("pufferfish") && recipe.contains("iron_door") && recipe.contains("obsidian")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.WARDING_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("warding_lantern") && recipe.contains("fishing_rod") && recipe.contains("cobweb") && recipe.contains("chain")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.CONTAINING_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("wither_rose") && recipe.contains("soul_sand") && recipe.contains("firework_star") && recipe.contains("coal")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.WITHERING_LANTERN_BLOCK.get().asItem()));
        } else if (recipe.contains("phantom_membrane") && recipe.contains("soul_torch") && recipe.contains("snow_block") && recipe.contains("white_wool")) {
            craft(level, pos, state, blockEntity, new ItemStack(ModRegistry.CLOUD_LANTERN_BLOCK.get().asItem()));
        } else {
            level.destroyBlock(pos.above(), true);
        }
    }

    private static void craft(Level level, BlockPos pos, BlockState state, LanternMakerBlockEntity blockEntity, ItemStack stack) {
        AtomicBoolean shouldSendItems = new AtomicBoolean(false);
        blockEntity.items().forEach(itemStack -> {
            if (itemStack.getCount() == 1) {
                shouldSendItems.set(true);
            }
            if (!itemStack.isEmpty()) {
                itemStack.shrink(1);
            }
        });
        if (shouldSendItems.get()) {
            blockEntity.setChanged();
        }
        ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundCraftLanternMessage(pos.above()), pos, level);
        level.destroyBlock(pos.above(), false);
        Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, stack);
    }

    @Override
    public NonNullList<ItemStack> items() {
        return this.items;
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
