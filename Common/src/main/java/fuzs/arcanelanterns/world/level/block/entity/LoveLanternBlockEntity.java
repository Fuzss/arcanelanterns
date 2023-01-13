package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.networking.ClientboundLoveHeartsMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class LoveLanternBlockEntity extends BlockEntity {
    private int count;

    public LoveLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.LOVE_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LoveLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 1000) return;
        level.getEntities(null, new AABB(pos.getX() - 5, pos.getY() - 5, pos.getZ() - 5, pos.getX() + 5, pos.getY() + 5, pos.getZ() + 5)).forEach((entity) -> {
            if (entity instanceof Animal animal) {
                animal.setInLoveTime(200);
                BlockPos entityPos = entity.blockPosition();
                ArcaneLanterns.NETWORK.sendToAllNear(new ClientboundLoveHeartsMessage(entityPos), entityPos, level);
            }
        });
        blockEntity.count = 0;
    }
}
