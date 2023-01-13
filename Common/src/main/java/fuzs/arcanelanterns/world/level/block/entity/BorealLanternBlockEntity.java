package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundBorealParticlesMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BorealLanternBlockEntity extends BlockEntity {
    private int count;

    public BorealLanternBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModRegistry.BOREAL_LANTERN_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BorealLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 2) return;
        level.getEntities(null, new AABB(pos.getX() + 0.5 - 10, pos.getY() + 0.5 - 10, pos.getZ() + 0.5 - 10, pos.getX() + 0.5 + 10, pos.getY() + 0.5 + 10, pos.getZ() + 0.5 + 10)).forEach((entity) -> {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 5, 3));
                entity.setRemainingFireTicks(0);
                ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundBorealParticlesMessage(pos, entity.blockPosition()), entity.blockPosition(), level);
            }
        });
        blockEntity.count = 0;
    }
}
