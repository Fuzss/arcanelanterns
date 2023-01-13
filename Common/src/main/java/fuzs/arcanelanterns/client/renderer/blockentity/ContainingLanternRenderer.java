package fuzs.arcanelanterns.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.arcanelanterns.world.level.block.entity.ContainingLanternBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ContainingLanternRenderer implements BlockEntityRenderer<ContainingLanternBlockEntity> {
    private static final ItemStack CHAIN_ITEM = new ItemStack(Items.CHAIN);

    public ContainingLanternRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(ContainingLanternBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        int lightAbove = LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above(255));
        for (int i = 30; i > 10; i--) {
            matrices.pushPose();
            int x = (i % 2 == 0) ? -i : i;
            double offsetx = Math.sin((entity.getLevel().getGameTime() + tickDelta) / x) + 0.5;
            double offsetz = Math.cos((entity.getLevel().getGameTime() + tickDelta) / x) + 0.5;
            matrices.translate(offsetx, 0.25, offsetz);
            matrices.mulPose(Vector3f.ZP.rotationDegrees((entity.getLevel().getGameTime() + tickDelta) * x));
            matrices.mulPose(Vector3f.YP.rotationDegrees((entity.getLevel().getGameTime() + tickDelta) * x));
            Minecraft.getInstance().getItemRenderer().renderStatic(CHAIN_ITEM, ItemTransforms.TransformType.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
            matrices.popPose();
        }
    }
}
