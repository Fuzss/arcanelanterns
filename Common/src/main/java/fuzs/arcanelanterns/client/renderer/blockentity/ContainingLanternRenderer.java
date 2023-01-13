package fuzs.arcanelanterns.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.arcanelanterns.world.level.block.entity.ContainingLanternBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

public class ContainingLanternRenderer implements BlockEntityRenderer<ContainingLanternBlockEntity> {
    private final ItemStack chainItem = new ItemStack(Items.CHAIN);
    private final ItemRenderer itemRenderer;

    public ContainingLanternRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

//    @Override
//    public void render(ContainingLanternBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
//        int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above(255));
//        for (int i = 30; i > 10; i--) {
//            matrices.pushPose();
//            int x = (i % 2 == 0) ? -i : i;
//            double offsetx = Math.sin((blockEntity.getLevel().getGameTime() + tickDelta) / x) + 0.5;
//            double offsetz = Math.cos((blockEntity.getLevel().getGameTime() + tickDelta) / x) + 0.5;
//            matrices.translate(offsetx, 0.25, offsetz);
//            matrices.mulPose(Vector3f.ZP.rotationDegrees((blockEntity.getLevel().getGameTime() + tickDelta) * x));
//            matrices.mulPose(Vector3f.YP.rotationDegrees((blockEntity.getLevel().getGameTime() + tickDelta) * x));
//            this.itemRenderer.renderStatic(this.chainItem, ItemTransforms.TransformType.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
//            matrices.popPose();
//        }
//    }

    @Override
    public void render(ContainingLanternBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        final int horizontalRange = 5;
        final int verticalRange = 3;
        blockEntity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockEntity.getBlockPos().getX() - horizontalRange, blockEntity.getBlockPos().getY() - verticalRange, blockEntity.getBlockPos().getZ() - horizontalRange, blockEntity.getBlockPos().getX() + horizontalRange, blockEntity.getBlockPos().getY() + verticalRange, blockEntity.getBlockPos().getZ() + horizontalRange)).forEach(entity -> {
            if (entity instanceof Player) return;
            int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos());
            float dx = (float) (entity.getX() - 0.5 - blockEntity.getBlockPos().getX());
            float dy = (float) (entity.getY() + 0.5 - blockEntity.getBlockPos().getY());
            float dz = (float) (entity.getZ() - 0.5 - blockEntity.getBlockPos().getZ());
            float distHor = Mth.sqrt(dx * dx + dz * dz);
            float dist = Mth.sqrt(dx * dx + dz * dz + dy * dy);
            for (float i = 0; i < 1; i += 0.1 + Math.sqrt(dist) / 20) {
                matrices.pushPose();
                matrices.translate(Mth.lerp(i, 0.5, dx + 0.5), Mth.lerp(i, 0, dy + 0.5), Mth.lerp(i, 0.5, dz + 0.5));
                matrices.mulPose(Vector3f.YP.rotationDegrees((float) (-Math.atan2(dz, dx) - 1.576)));
                matrices.mulPose(Vector3f.XP.rotationDegrees((float) (-Math.atan2(distHor, dy))));
                this.itemRenderer.renderStatic(this.chainItem, ItemTransforms.TransformType.NONE, lightAbove, overlay, matrices, vertexConsumers, 0);
                matrices.popPose();
            }
        });
    }
}
