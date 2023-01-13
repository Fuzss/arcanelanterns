package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CloudLanternBlockEntity extends BlockEntity {
    private int count;

    public CloudLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.CLOUD_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CloudLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 10) return;
        level.getEntities(null, new AABB(pos.getX() + 0.5 - 10, pos.getY() + 0.5 - 10, pos.getZ() + 0.5 - 10, pos.getX() + 0.5 + 10, pos.getY() + 0.5 + 10, pos.getZ() + 0.5 + 10)).forEach((entity) -> {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 5 * 20, 1));
            }
        });
        blockEntity.count = 0;
    }
}
