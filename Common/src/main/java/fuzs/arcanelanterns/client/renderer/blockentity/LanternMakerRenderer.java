package fuzs.arcanelanterns.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.arcanelanterns.world.level.block.entity.LanternMakerBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LanternMakerRenderer implements BlockEntityRenderer<LanternMakerBlockEntity> {
    private final ItemRenderer itemRenderer;

    public LanternMakerRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(LanternMakerBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        NonNullList<ItemStack> items = blockEntity.items();
        if (items.isEmpty()) return;
        int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
        int posData = (int) blockEntity.getBlockPos().asLong();
        this.renderHoveringItemList(items, blockEntity.getLevel().getGameTime() + tickDelta, matrices, vertexConsumers, lightAbove, overlay, true, posData);
    }

    private void renderHoveringItemList(List<ItemStack> inventoryItems, float age, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, boolean rotateItems, int posData) {
        // mostly copied from Botania's runic altar rendering code, thanks!
        float itemRenderAngle = 360.0F / inventoryItems.size();
        for (int i = 0; i < inventoryItems.size(); ++i) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5F, 1.0F, 0.5F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(i * itemRenderAngle + age));
            matrixStackIn.translate(0.75F, 0.0F, 0.25F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(rotateItems ? age % 360.0F : 90.0F));
            matrixStackIn.translate(0.0, 0.075 * Math.sin((age + i * 10.0) / 5.0), 0.0F);
            this.itemRenderer.renderStatic(inventoryItems.get(i), ItemTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, posData + i);
            matrixStackIn.popPose();
        }
    }
}
