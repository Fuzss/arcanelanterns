package fuzs.arcanelanterns.world.level.block;

import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.world.level.block.entity.LanternMakerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LanternMakerBlock extends BaseEntityBlock {

    public LanternMakerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LanternMakerBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide ? createTickerHelper(blockEntityType, ModRegistry.LANTERN_MAKER_BLOCK_ENTITY.get(), LanternMakerBlockEntity::tick) : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.01, 0, 0.01, 0.99, 1, 0.99);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof LanternMakerBlockEntity blockEntity) {
            if (!player.getItemInHand(hand).isEmpty()) {
                for (int i = 0; i < blockEntity.getContainerSize(); i++) {
                    if (blockEntity.getItem(i).isEmpty()) {
                        if (!level.isClientSide) {
                            blockEntity.setItem(i, player.getItemInHand(hand).copy());
                            blockEntity.setChanged();
                            player.getItemInHand(hand).setCount(0);
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            } else if (player.isSecondaryUseActive()) {
                for (int i = blockEntity.getContainerSize() - 1; i > -1; i--) {
                    if (!blockEntity.getItem(i).isEmpty()) {
                        if (!level.isClientSide) {
                            blockEntity.setItem(i, ItemStack.EMPTY);
                            blockEntity.setChanged();
                            player.setItemInHand(hand, blockEntity.getItem(i).copy());
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof LanternMakerBlockEntity blockEntity) {
                Containers.dropContents(level, pos, blockEntity);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
