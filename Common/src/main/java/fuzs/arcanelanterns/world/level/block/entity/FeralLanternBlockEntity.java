package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.config.ServerConfig;
import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FeralLanternBlockEntity extends LanternBlockEntity {
    private static final String TAG_FLARES = "PlacedFlares";

    private int placedFlares;
    private boolean placeAttempt;

    public FeralLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.FERAL_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FeralLanternBlockEntity blockEntity) {
        ServerConfig.FeralLanternConfig config = ArcaneLanterns.CONFIG.get(ServerConfig.class).feralLantern;
        if (++blockEntity.count > config.delay && !blockEntity.placeAttempt) {
            final int horizontalRange = config.horizontalRange;
            final int verticalRange = config.verticalRange;
            if (blockEntity.placedFlares >= config.maxPlacedFlares) {
                level.destroyBlock(pos, false);
                return;
            }
            BlockPos check = pos.subtract(new Vec3i(horizontalRange, verticalRange, horizontalRange));
            check = check.offset(level.random.nextInt(horizontalRange * 2), -level.random.nextInt(verticalRange * 2), level.random.nextInt(horizontalRange * 2));
            while (check.closerThan(pos, 200)) {
                blockEntity.placeAttempt = true;
                if (level.getBlockState(check).isAir() && !(level.getBlockState(check.below()).isAir()) && level.getMaxLocalRawBrightness(check) < config.maxLightLevel) {
                    level.setBlockAndUpdate(check, ModRegistry.SPARK_BLOCK.get().defaultBlockState());
                    blockEntity.placeAttempt = false;
                    blockEntity.placedFlares++;
                }
                check = check.subtract(new Vec3i(0, 1, 0));
            }
            if (!check.closerThan(pos, 100)) {
                blockEntity.placeAttempt = false;
            }
            blockEntity.count = 0;
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(TAG_FLARES, this.placedFlares);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.placedFlares = tag.getInt(TAG_FLARES);
    }
}
