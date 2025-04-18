package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.config.ServerConfig;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.network.ClientboundWailingSoundsMessage;
import fuzs.puzzleslib.api.network.v4.MessageSender;
import fuzs.puzzleslib.api.network.v4.PlayerSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WailingLanternBlockEntity extends LanternBlockEntity {

    public WailingLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.WAILING_LANTERN_BLOCK_ENTITY.value(), pos, state);
    }

    @Override
    public void serverTick() {
        ServerConfig.EffectLanternConfig config = ArcaneLanterns.CONFIG.get(ServerConfig.class).wailingLantern;
        if (++this.ticks <= config.delay) return;
        final int horizontalRange = config.horizontalRange;
        final int verticalRange = config.verticalRange;
        this.getLevel()
                .getEntitiesOfClass(Player.class,
                        new AABB(this.getBlockPos().getX() + 0.5 - horizontalRange,
                                this.getBlockPos().getY() + 0.5 - verticalRange,
                                this.getBlockPos().getZ() + 0.5 - horizontalRange,
                                this.getBlockPos().getX() + 0.5 + horizontalRange,
                                this.getBlockPos().getY() + 0.5 + verticalRange,
                                this.getBlockPos().getZ() + 0.5 + horizontalRange),
                        EntitySelector.NO_SPECTATORS)
                .forEach((Player player) -> {
                    if (!player.blockPosition().closerThan(this.getBlockPos(), 5.0)) {
                        MessageSender.broadcast(PlayerSet.nearBlockEntity(this),
                                new ClientboundWailingSoundsMessage(this.getBlockPos(), false));
                    } else {
                        player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, config.effectDuration * 20, 0));
                        MessageSender.broadcast(PlayerSet.nearBlockEntity(this),
                                new ClientboundWailingSoundsMessage(this.getBlockPos(), true));
                    }
                });
        this.ticks = 0;
    }
}
