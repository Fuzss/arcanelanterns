package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
        level.getEntities(null, new AABB(pos.getX() + 0.5 - 10, pos.getY() + 0.5 - 4, pos.getZ() + 0.5 - 10, pos.getX() + 0.5 + 10, pos.getY() + 0.5 + 4, pos.getZ() + 0.5 + 10)).forEach((entity) -> {
            if (entity instanceof LivingEntity && !(entity instanceof Player) && !entity.blockPosition().closerThan(pos, 6)) {
                if (level.getBlockState(pos.above()).isAir()) {
                    entity.teleportToWithTicket(pos.getX(), pos.getY() + 1, pos.getZ());
                } else {
                    entity.teleportToWithTicket(pos.getX(), pos.getY() - 1, pos.getZ());
                }
                FriendlyByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(pos);
                PlayerLookup.tracking((ServerLevel) level, entity.blockPosition()).forEach((serverPlayerEntity) -> {
                    ServerPlayNetworking.send(serverPlayerEntity, new ResourceLocation(ArcaneLanterns.MODID, "containing_lantern"), buf);
                });
            }
        });
        blockEntity.count = 0;
    }
}
