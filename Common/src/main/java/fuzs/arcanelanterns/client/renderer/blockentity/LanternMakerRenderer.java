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

import java.util.function.Predicate;

/**
 * mostly copied from Botania's runic altar rendering code, thanks!
 * <p><a href="https://github.com/VazkiiMods/Botania/blob/1.19.x/Xplat/src/main/java/vazkii/botania/client/render/block_entity/RunicAltarBlockEntityRenderer.java">RunicAltarBlockEntityRenderer.java</a>
 */
public class LanternMakerRenderer implements BlockEntityRenderer<LanternMakerBlockEntity> {
    private final ItemRenderer itemRenderer;

    public LanternMakerRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(LanternMakerBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        NonNullList<ItemStack> items = blockEntity.items();
        if (!items.isEmpty()) {
            int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
            int posData = (int) blockEntity.getBlockPos().asLong();
            float age = blockEntity.getLevel().getGameTime() + tickDelta;
            long filledSlots = items.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
            float itemRenderAngle = 360.0F / filledSlots;
            for (int i = 0; i < items.size(); ++i) {
                if (!items.get(i).isEmpty()) {
                    matrices.pushPose();
                    matrices.translate(0.5F, 1.15F, 0.5F);
                    matrices.mulPose(Vector3f.YP.rotationDegrees(i * itemRenderAngle + age));
                    matrices.translate(0.75F, 0.0F, 0.25F);
                    matrices.mulPose(Vector3f.YP.rotationDegrees(age % 360.0F));
                    matrices.translate(0.0, 0.075 * Math.sin((age + i * 10.0) / 5.0), 0.0F);
                    this.itemRenderer.renderStatic(items.get(i), ItemTransforms.TransformType.GROUND, lightAbove, overlay, matrices, vertexConsumers, posData + i);
                    matrices.popPose();
                }
            }
        }
    }
}
