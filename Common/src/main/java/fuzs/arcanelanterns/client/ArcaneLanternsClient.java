package fuzs.arcanelanterns.client;

import fuzs.arcanelanterns.client.renderer.blockentity.LanternMakerRenderer;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientModConstructor;

public class ArcaneLanternsClient implements ClientModConstructor {

    @Override
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModRegistry.LANTERN_MAKER_BLOCK_ENTITY.get(), LanternMakerRenderer::new);
    }
}
