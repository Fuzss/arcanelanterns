package fuzs.arcanelanterns;

import fuzs.arcanelanterns.api.event.entity.living.LivingEvents;
import fuzs.arcanelanterns.handler.BreedingHeartsHandler;
import fuzs.puzzleslib.core.CommonFactories;
import net.fabricmc.api.ModInitializer;

public class ArcaneLanternsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonFactories.INSTANCE.modConstructor(ArcaneLanterns.MOD_ID).accept(new ArcaneLanterns());
        registerHandlers();
    }

    private static void registerHandlers() {
        LivingEvents.TICK.register(BreedingHeartsHandler::onLivingUpdate);
    }
}
