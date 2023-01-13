package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WitheringLanternBlockEntity extends BlockEntity {
    private int count;

    public WitheringLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.WITHERING_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WitheringLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 20) return;
        level.getEntities(null, new AABB(pos.getX() + 0.5 - 7, pos.getY() + 0.5 - 7, pos.getZ() + 0.5 - 7, pos.getX() + 0.5 + 7, pos.getY() + 0.5 + 7, pos.getZ() + 0.5 + 7)).forEach((entity) -> {
            if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.WITHER, 3 * 20, 1));
            }
        });
        blockEntity.count = 0;
    }
}
