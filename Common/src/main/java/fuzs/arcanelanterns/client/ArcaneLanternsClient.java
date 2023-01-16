package fuzs.arcanelanterns.client;

import fuzs.arcanelanterns.client.renderer.blockentity.LanternMakerRenderer;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.client.renderer.RenderType;

public class ArcaneLanternsClient implements ClientModConstructor {

    @Override
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModRegistry.LANTERN_MAKER_BLOCK_ENTITY.get(), LanternMakerRenderer::new);
    }

    @Override
    public void onRegisterBlockRenderTypes(BlockRenderTypesContext context) {
        context.registerBlock(ModRegistry.LIFE_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.FERAL_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.LOVE_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.WAILING_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.BOREAL_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.BRILLIANT_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.WARDING_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.CONTAINING_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.WITHERING_LANTERN_BLOCK.get(), RenderType.cutout());
        context.registerBlock(ModRegistry.CLOUD_LANTERN_BLOCK.get(), RenderType.cutout());
    }
}
