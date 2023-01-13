package fuzs.arcanelanterns.client;

import fuzs.arcanelanterns.init.MagicLanternBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.magic.lanterns.block.*;
import net.minecraft.client.renderer.RenderType;

public class ClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.LIFE_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.FERAL_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.LOVE_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WAILING_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.BOREAL_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.BRILIANT_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WARDING_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.CONTAINING_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WITHERING_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.CLOUD_LANTERN, RenderType.cutout());
    }
}
