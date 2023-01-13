package fuzs.arcanelanterns.networking;

import fuzs.puzzleslib.api.networking.v3.ClientMessageListener;
import fuzs.puzzleslib.api.networking.v3.ClientboundMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public record ClientboundCraftLanternMessage(BlockPos pos) implements ClientboundMessage<ClientboundCraftLanternMessage> {

    @Override
    public ClientMessageListener<ClientboundCraftLanternMessage> getHandler() {
        return new ClientMessageListener<>() {

            @Override
            public void handle(ClientboundCraftLanternMessage message, Minecraft client, ClientPacketListener handler, LocalPlayer player, ClientLevel level) {
                level.playLocalSound(message.pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1, 1, true);
                for (int i = 0; i < 20; i++) {
                    level.addParticle(ParticleTypes.END_ROD, message.pos.getX(), message.pos.getY(), message.pos.getZ(), 0.5d - level.random.nextDouble(), 0.5d - level.random.nextDouble(), 0.5d - level.random.nextDouble());
                }
            }
        };
    }
}
