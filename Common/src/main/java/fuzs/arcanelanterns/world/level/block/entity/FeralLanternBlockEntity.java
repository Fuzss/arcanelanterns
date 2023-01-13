package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FeralLanternBlockEntity extends BlockEntity {
    private final RandomSource random = RandomSource.create();
    private int count;
    private int totalFlares;
    private boolean placeAttempt;

    public FeralLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.FERAL_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FeralLanternBlockEntity blockEntity) {
        if (++blockEntity.count > 20 && !blockEntity.placeAttempt) {
            if (blockEntity.totalFlares > 100) {
                level.destroyBlock(pos, false);
            }
            BlockPos check = pos.subtract(new Vec3i(40, 5, 40));
            check = check.offset(blockEntity.random.nextInt(80), -blockEntity.random.nextInt(20), blockEntity.random.nextInt(80));
            while (check.closerThan(pos, 200)) {
                blockEntity.placeAttempt = true;
                if (level.getBlockState(check).isAir() && !(level.getBlockState(check.below()).isAir()) && level.getMaxLocalRawBrightness(check) < 7) {
                    level.setBlockAndUpdate(check, ModRegistry.SPARK_BLOCK.get().defaultBlockState());
                    blockEntity.placeAttempt = false;
                    blockEntity.totalFlares++;
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
        tag.putInt("TotalFlares", this.totalFlares);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.totalFlares = tag.getInt("TotalFlares");
    }
}
