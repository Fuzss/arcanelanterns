package fuzs.arcanelanterns;

import fuzs.arcanelanterns.data.ModBlockTagsProvider;
import fuzs.arcanelanterns.data.ModLootTableProvider;
import fuzs.arcanelanterns.data.ModRecipeProvider;
import fuzs.arcanelanterns.handler.BreedingHeartsHandler;
import fuzs.puzzleslib.core.CommonFactories;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
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

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper fileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModLootTableProvider(generator, ArcaneLanterns.MOD_ID));
        generator.addProvider(true, new ModRecipeProvider(generator, ArcaneLanterns.MOD_ID));
        generator.addProvider(true, new ModBlockTagsProvider(generator, ArcaneLanterns.MOD_ID, fileHelper));
    }
}
