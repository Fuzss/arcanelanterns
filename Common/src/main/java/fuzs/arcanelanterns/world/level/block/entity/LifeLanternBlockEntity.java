package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundLifeGrowMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
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
        BlockPos checkedPos = pos.subtract(new Vec3i(5, 0, 5));
        checkedPos = checkedPos.offset(blockEntity.random.nextInt(10), blockEntity.random.nextInt(5), (double) blockEntity.random.nextInt(10));
        while (!(level.getBlockState(checkedPos).getBlock() instanceof CropBlock) && checkedPos.closerThan(pos, 6d)) {
            checkedPos = checkedPos.subtract(new Vec3i(0, 1, 0));
        }

        if (level.getBlockState(checkedPos).getBlock() instanceof CropBlock cropBlock) {
            cropBlock.growCrops(level, checkedPos, level.getBlockState(checkedPos));
            ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundLifeGrowMessage(checkedPos), checkedPos, level);
        }
        AABB box = new AABB(pos.getX() + 0.5 - 5, pos.getY() + 0.5 - 5, pos.getZ() + 0.5 - 5, pos.getX() + 0.5 + 5, pos.getY() + 0.5 + 5, pos.getZ() + 0.5 + 5);
        level.getEntities(null, box).forEach((entity) -> {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5 * 20, 2));
            }
        });
        blockEntity.count = 0;
    }
}
