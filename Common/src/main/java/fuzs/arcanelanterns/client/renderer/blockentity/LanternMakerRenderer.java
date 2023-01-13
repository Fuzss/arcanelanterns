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

public class LanternMakerRenderer implements BlockEntityRenderer<LanternMakerBlockEntity> {
    private final ItemRenderer itemRenderer;

    public LanternMakerRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(LanternMakerBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        NonNullList<ItemStack> items = entity.items();
        int lightAbove = LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above(255));
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);
            if (item.isEmpty()) continue;
            matrices.pushPose();
            int x = (i % 2 == 0) ? -i : i;
            double offsetY = Math.sin((entity.getLevel().getGameTime() + tickDelta) / x);
            double offsetX = Math.sin((entity.getLevel().getGameTime() + tickDelta) / i) + 0.5;
            double offsetZ = Math.cos((entity.getLevel().getGameTime() + tickDelta) / i) + 0.5;
            matrices.translate(offsetX, 1.25 + offsetY, offsetZ);
            matrices.mulPose(Vector3f.YP.rotationDegrees((entity.getLevel().getGameTime() + tickDelta) * i));
            matrices.mulPose(Vector3f.XP.rotationDegrees((entity.getLevel().getGameTime() + tickDelta) * i));
            matrices.mulPose(Vector3f.ZP.rotationDegrees((entity.getLevel().getGameTime() + tickDelta) * i));
            this.itemRenderer.renderStatic(item, ItemTransforms.TransformType.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
            matrices.popPose();
        }
    }
}
