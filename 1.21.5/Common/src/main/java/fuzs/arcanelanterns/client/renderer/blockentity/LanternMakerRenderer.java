package fuzs.arcanelanterns.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.arcanelanterns.world.level.block.entity.LanternMakerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

/**
 * Mostly copied from <a
 * href="https://github.com/VazkiiMods/Botania/blob/1.19.x/Xplat/src/main/java/vazkii/botania/client/render/block_entity/RunicAltarBlockEntityRenderer.java">Botania's
 * Runic Altar</a> rendering code, thanks!
 */
public class LanternMakerRenderer implements BlockEntityRenderer<LanternMakerBlockEntity> {
    private final ItemRenderer itemRenderer;

    public LanternMakerRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(LanternMakerBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, Vec3 cameraPosition) {
        NonNullList<ItemStack> items = blockEntity.getContainerItems();
        if (!items.isEmpty()) {
            int posData = (int) blockEntity.getBlockPos().asLong();
            float totalTicks = blockEntity.getLevel().getGameTime() + tickDelta;
            long filledSlots = items.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
            float itemRenderAngle = 360.0F / filledSlots;
            for (int i = 0; i < items.size(); ++i) {
                if (!items.get(i).isEmpty()) {
                    matrices.pushPose();
                    matrices.translate(0.5F, 1.15F, 0.5F);
                    matrices.mulPose(Axis.YP.rotationDegrees(i * itemRenderAngle + totalTicks));
                    matrices.translate(0.75F, 0.0F, 0.25F);
                    matrices.mulPose(Axis.YP.rotationDegrees(totalTicks % 360.0F));
                    matrices.translate(0.0, 0.075 * Math.sin((totalTicks + i * 10.0) / 5.0), 0.0F);
                    this.itemRenderer.renderStatic(items.get(i),
                            ItemDisplayContext.GROUND,
                            light,
                            overlay,
                            matrices,
                            vertexConsumers,
                            blockEntity.getLevel(),
                            posData + i);
                    matrices.popPose();
                }
            }
        }
    }
}
