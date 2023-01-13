package fuzs.arcanelanterns.handler;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;

import java.util.Optional;

public class BreedingHeartsHandler {

    public static Optional<Unit> onLivingUpdate(LivingEntity entity) {
        if (!entity.level.isClientSide &&  entity instanceof Animal animal) {
            if (animal.getInLoveTime() > 0 && animal.getInLoveTime() % 10 == 0) {
                double posX = animal.getRandom().nextGaussian() * 0.02;
                double posY = animal.getRandom().nextGaussian() * 0.02;
                double posZ = animal.getRandom().nextGaussian() * 0.02;
                ((ServerLevel) animal.level).sendParticles(ParticleTypes.HEART, animal.getRandomX(1.0), animal.getRandomY() + 0.5, animal.getRandomZ(1.0), 1, posX, posY, posZ, 0.0);
            }
        }
        return Optional.empty();
    }
}
