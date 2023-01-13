package fuzs.arcanelanterns.world.level.block.entity;

import fuzs.arcanelanterns.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoveLanternBlockEntity extends BlockEntity {
    private int count;

    public LoveLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.LOVE_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LoveLanternBlockEntity blockEntity) {
        if (++blockEntity.count <= 1200) return;
        final int horizontalRange = 5;
        final int verticalRange = 3;
        List<Animal> nearbyAnimals = level.getEntitiesOfClass(Animal.class, new AABB(pos.getX() - horizontalRange, pos.getY() - verticalRange, pos.getZ() - horizontalRange, pos.getX() + horizontalRange, pos.getY() + verticalRange, pos.getZ() + horizontalRange));
        Collection<List<Animal>> animalsByType = nearbyAnimals.stream().collect(Collectors.groupingBy(Entity::getType)).values();
        for (List<Animal> animals : animalsByType) {
            if (animals.size() <= 12) {
                animals.removeIf(animal -> animal.getAge() != 0 || !animal.canFallInLove());
                if (animals.size() >= 2) {
                    animals.forEach(animal -> animal.setInLove(null));
                }
            }
        }
        blockEntity.count = 0;
    }
}
