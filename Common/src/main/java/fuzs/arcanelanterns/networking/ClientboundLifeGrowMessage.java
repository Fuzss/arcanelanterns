package fuzs.arcanelanterns.networking;

import fuzs.puzzleslib.api.networking.v3.ClientMessageListener;
import fuzs.puzzleslib.api.networking.v3.ClientboundMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;

public record ClientboundLifeGrowMessage(BlockPos pos) implements ClientboundMessage<ClientboundLifeGrowMessage> {

    @Override
    public ClientMessageListener<ClientboundLifeGrowMessage> getHandler() {
        return new ClientMessageListener<>() {

            @Override
            public void handle(ClientboundLifeGrowMessage message, Minecraft client, ClientPacketListener handler, LocalPlayer player, ClientLevel level) {
                for (int i = 0; i < 20; i++) {
                    level.addParticle(ParticleTypes.COMPOSTER, message.pos.getX() + level.random.nextDouble(), message.pos.getY() + level.random.nextDouble(), message.pos.getZ() + level.random.nextDouble(), 0, 0, 0);
                }
            }
        };
    }
}
