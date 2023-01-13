package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundWailingSoundsMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WailingLanternBlockEntity extends BlockEntity {
    private int count;

    public WailingLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.WAILING_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WailingLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 20) return;
        level.getEntities(null, new AABB(pos.getX() + 0.5 - 10, pos.getY() + 0.5 - 10, pos.getZ() + 0.5 - 10, pos.getX() + 0.5 + 10, pos.getY() + 0.5 + 10, pos.getZ() + 0.5 + 10)).forEach((entity) -> {
            if (entity instanceof Player player) {
                if (!player.blockPosition().closerThan(pos, 5)) {
                    ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundWailingSoundsMessage(pos, false), pos, level);
                } else {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5 * 20, 3));
                    ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundWailingSoundsMessage(pos, true), pos, level);
                }
            }
        });
        blockEntity.count = 0;
    }
}
