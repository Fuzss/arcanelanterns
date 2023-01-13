package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundContainingSoundsMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ContainingLanternBlockEntity extends BlockEntity {
    private int count;

    public ContainingLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.CONTAINING_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ContainingLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 5) return;
        final int horizontalRange = 5;
        final int verticalRange = 3;
        level.getEntities(null, new AABB(pos.getX() + 0.5 - horizontalRange, pos.getY() + 0.5 - verticalRange, pos.getZ() + 0.5 - horizontalRange, pos.getX() + 0.5 + horizontalRange, pos.getY() + 0.5 + verticalRange, pos.getZ() + 0.5 + horizontalRange)).forEach((entity) -> {
            if (entity instanceof LivingEntity && !(entity instanceof Player) && !entity.blockPosition().closerThan(pos, 6)) {
                if (level.getBlockState(pos.above()).isAir()) {
                    entity.teleportToWithTicket(pos.getX(), pos.getY() + 1, pos.getZ());
                } else {
                    entity.teleportToWithTicket(pos.getX(), pos.getY() - 1, pos.getZ());
                }
                ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundContainingSoundsMessage(pos), pos, level);
            }
        });
        blockEntity.count = 0;
    }
}
