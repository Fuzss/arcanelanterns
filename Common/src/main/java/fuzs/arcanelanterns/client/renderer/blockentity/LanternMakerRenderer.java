package fuzs.arcanelanterns.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.arcanelanterns.world.level.block.entity.LanternMakerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public class LanternMakerRenderer implements BlockEntityRenderer<LanternMakerBlockEntity> {

    public LanternMakerRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(LanternMakerBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        NonNullList<ItemStack> items = entity.items();
        int i = 10;
        int lightAbove = LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above(255));
        for (ItemStack item : items) {
            matrices.pushPose();
            int x = (i % 2 == 0) ? -i : i;
            double offsety = Math.sin((entity.getLevel().getGameTime() + tickDelta) / x);
            double offsetx = Math.sin((entity.getLevel().getGameTime() + tickDelta) / i) + 0.5;
            double offsetz = Math.cos((entity.getLevel().getGameTime() + tickDelta) / i) + 0.5;
            matrices.translate(offsetx, 1.25 + offsety, offsetz);
            matrices.mulPose(Vector3f.YP.rotationDegrees((entity.getLevel().getGameTime() + tickDelta) * i));
            matrices.mulPose(Vector3f.XP.rotationDegrees((entity.getLevel().getGameTime() + tickDelta) * i));
            matrices.mulPose(Vector3f.ZP.rotationDegrees((entity.getLevel().getGameTime() + tickDelta) * i));
            Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
            matrices.popPose();
            i--;
        }
    }
}
