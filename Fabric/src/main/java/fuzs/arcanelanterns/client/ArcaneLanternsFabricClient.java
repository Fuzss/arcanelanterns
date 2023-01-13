package fuzs.arcanelanterns.client;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class ArcaneLanternsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientFactories.INSTANCE.clientModConstructor(ArcaneLanterns.MOD_ID).accept(new ArcaneLanternsClient());
        registerRenderTypes();
    }

    private static void registerRenderTypes() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.LIFE_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.FERAL_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.LOVE_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.WAILING_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BOREAL_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.BRILIANT_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.WARDING_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.CONTAINING_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.WITHERING_LANTERN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.CLOUD_LANTERN_BLOCK.get(), RenderType.cutout());
    }
}
