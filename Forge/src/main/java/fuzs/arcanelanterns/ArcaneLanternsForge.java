package fuzs.arcanelanterns;

import fuzs.arcanelanterns.handler.BreedingHeartsHandler;
import fuzs.puzzleslib.core.CommonFactories;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(ArcaneLanterns.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArcaneLanternsForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CommonFactories.INSTANCE.modConstructor(ArcaneLanterns.MOD_ID).accept(new ArcaneLanterns());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final LivingEvent.LivingTickEvent evt) -> {
            BreedingHeartsHandler.onLivingUpdate(evt.getEntity()).ifPresent(unit -> evt.setCanceled(true));
        });
    }
}
