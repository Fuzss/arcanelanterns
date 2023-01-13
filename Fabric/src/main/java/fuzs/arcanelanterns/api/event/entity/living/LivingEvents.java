package fuzs.arcanelanterns.api.event.entity.living;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public final class LivingEvents {
    public static final Event<Tick> TICK = EventFactory.createArrayBacked(Tick.class, listeners -> (LivingEntity entity) -> {
        for (Tick event : listeners) {
            if (event.onLivingTick(entity).isPresent()) {
                return Optional.of(Unit.INSTANCE);
            }
        }
        return Optional.empty();
    });

    private LivingEvents() {

    }

    @FunctionalInterface
    public interface Tick {

        /**
         * Called at the beginning of {@link LivingEntity#tick()}, allows to prevent the tick method from running.
         *
         * @param entity the entity that's being ticked
         * @return if present the tick method will be cancelled
         */
        Optional<Unit> onLivingTick(LivingEntity entity);
    }
}
