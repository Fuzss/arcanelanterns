package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BriliantLanternBlockEntity extends BlockEntity {
    private int count;

    public BriliantLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.BRILIANT_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BriliantLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 20) return;
        level.getEntities(null, new AABB(pos.getX() + 0.5 - 4, pos.getY() + 0.5 - 4, pos.getZ() + 0.5 - 4, pos.getX() + 0.5 + 4, pos.getY() + 0.5 + 4, pos.getZ() + 0.5 + 4)).forEach((entity) -> {
            if (entity instanceof LivingEntity && !(entity instanceof Player) && !(entity instanceof EnderDragon)) {
                level.addFreshEntity(new ExperienceOrb(level, entity.getX(), entity.getY(), entity.getZ(), (int) ((((LivingEntity) entity).getMaxHealth()) / 2)));
                entity.discard();
            }
        });
        blockEntity.count = 0;
    }
}
