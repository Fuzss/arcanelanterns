package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.config.ServerConfig;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundWailingSoundsMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WailingLanternBlockEntity extends LanternBlockEntity {

    public WailingLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.WAILING_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WailingLanternBlockEntity blockEntity) {
        ServerConfig.EffectLanternConfig config = ArcaneLanterns.CONFIG.get(ServerConfig.class).wailingLantern;
        if (++blockEntity.count <= config.delay) return;
        final int horizontalRange = config.horizontalRange;
        final int verticalRange = config.verticalRange;
        level.getEntitiesOfClass(Player.class, new AABB(pos.getX() + 0.5 - horizontalRange, pos.getY() + 0.5 - verticalRange, pos.getZ() + 0.5 - horizontalRange, pos.getX() + 0.5 + horizontalRange, pos.getY() + 0.5 + verticalRange, pos.getZ() + 0.5 + horizontalRange)).forEach((player) -> {
            if (!player.blockPosition().closerThan(pos, 5)) {
                ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundWailingSoundsMessage(pos, false), pos, level);
            } else {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, config.effectDuration * 20, 0));
                ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundWailingSoundsMessage(pos, true), pos, level);
            }
        });
        blockEntity.count = 0;
    }
}
