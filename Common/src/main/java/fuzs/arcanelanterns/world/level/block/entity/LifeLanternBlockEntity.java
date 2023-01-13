package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class LifeLanternBlockEntity extends BlockEntity {
    private final RandomSource random = RandomSource.create();
    private int count;

    public LifeLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.LIFE_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LifeLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 30) return;
        final int horizontalRange = 5;
        final int verticalRange = 3;
        BlockPos targetPos = pos.offset(blockEntity.random.nextInt(horizontalRange * 2) - horizontalRange, blockEntity.random.nextInt(verticalRange * 2) - verticalRange, (double) blockEntity.random.nextInt(horizontalRange * 2) - horizontalRange);
        while (!(level.getBlockState(targetPos).getBlock() instanceof BonemealableBlock) && targetPos.closerThan(pos, 6.0)) {
            targetPos = targetPos.subtract(new Vec3i(0, 1, 0));
        }

        BlockState targetState = level.getBlockState(targetPos);
        if (targetState.getBlock() instanceof BonemealableBlock cropBlock && cropBlock.isValidBonemealTarget(level, targetPos, targetState, false) && cropBlock.isBonemealSuccess(level, level.random, targetPos, targetState)) {
            cropBlock.performBonemeal((ServerLevel) level, level.random, targetPos, targetState);
            level.levelEvent(2005, targetPos, 0);
        }
        AABB aabb = new AABB(pos.getX() + 0.5 - horizontalRange, pos.getY() + 0.5 - verticalRange, pos.getZ() + 0.5 - horizontalRange, pos.getX() + 0.5 + horizontalRange, pos.getY() + 0.5 + verticalRange, pos.getZ() + 0.5 + horizontalRange);
        level.getEntitiesOfClass(LivingEntity.class, aabb).forEach(entity -> {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, true, true));
        });
        blockEntity.count = 0;
    }
}
