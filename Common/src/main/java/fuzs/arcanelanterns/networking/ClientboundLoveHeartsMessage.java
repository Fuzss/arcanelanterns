package fuzs.arcanelanterns.networking;

import fuzs.puzzleslib.api.networking.v3.ClientMessageListener;
import fuzs.puzzleslib.api.networking.v3.ClientboundMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;

public record ClientboundLoveHeartsMessage(BlockPos pos) implements ClientboundMessage<ClientboundLoveHeartsMessage> {

    @Override
    public ClientMessageListener<ClientboundLoveHeartsMessage> getHandler() {
        return new ClientMessageListener<>() {

            @Override
            public void handle(ClientboundLoveHeartsMessage message, Minecraft client, ClientPacketListener handler, LocalPlayer player, ClientLevel level) {
                for (int i = 0; i < 10; i++) {
                    level.addParticle(ParticleTypes.HEART, message.pos.getX() + level.random.nextDouble() * 1.5, message.pos.getY() + level.random.nextDouble() * 2, message.pos.getZ() - 0.7 + level.random.nextDouble() * 1.5, 0, 0, 0);
                }
            }
        };
    }
}
