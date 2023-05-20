package fuzs.arcanelanterns.handler;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.config.ServerConfig;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;

public class BreedingHeartsHandler {

    public static EventResult onLivingUpdate(LivingEntity entity) {
        if (!ArcaneLanterns.CONFIG.get(ServerConfig.class).permanentBreedingHearts) return EventResult.PASS;
        if (!entity.level.isClientSide &&  entity instanceof Animal animal) {
            if (animal.getInLoveTime() > 0 && animal.getInLoveTime() % 10 == 0) {
                double posX = animal.getRandom().nextGaussian() * 0.02;
                double posY = animal.getRandom().nextGaussian() * 0.02;
                double posZ = animal.getRandom().nextGaussian() * 0.02;
                ((ServerLevel) animal.level).sendParticles(ParticleTypes.HEART, animal.getRandomX(1.0), animal.getRandomY() + 0.5, animal.getRandomZ(1.0), 1, posX, posY, posZ, 0.0);
            }
        }
        return EventResult.PASS;
    }
}
