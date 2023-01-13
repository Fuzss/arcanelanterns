package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WardingLanternBlockEntity extends BlockEntity {
    private int count;

    public WardingLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.WARDING_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WardingLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 10) return;
        level.getEntities(null, new AABB(pos.getX() - 10, pos.getY() - 5, pos.getZ() - 10, pos.getX() + 10, pos.getY() + 5, pos.getZ() + 10)).forEach((entity) -> {
            if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                entity.push((double) (-pos.getX() + entity.blockPosition().getX()) / 10, (double) (-pos.getY() + entity.blockPosition().getY()) / 10, (double) (-pos.getZ() + entity.blockPosition().getZ()) / 10);
            }
        });
        blockEntity.count = 0;
    }
}
