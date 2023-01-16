package fuzs.arcanelanterns.client;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

import java.util.Locale;

@Mod.EventBusSubscriber(modid = ArcaneLanterns.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ArcaneLanternsForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientFactories.INSTANCE.clientModConstructor(ArcaneLanterns.MOD_ID).accept(new ArcaneLanternsClient());
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        registerRenderTypes();
    }

    private static void registerRenderTypes() {
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.LIFE_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.FERAL_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.LOVE_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.WAILING_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.BOREAL_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.BRILLIANT_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.WARDING_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.CONTAINING_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.WITHERING_LANTERN_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModRegistry.CLOUD_LANTERN_BLOCK.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void onRegisterRecipeBookCategories(final RegisterRecipeBookCategoriesEvent evt) {
        // just skip this on Fabric, it's not very important (just prints a warning to the log) and Fabric has no api for it
        RecipeBookCategories category = RecipeBookCategories.create(ArcaneLanterns.MOD_NAME.toUpperCase(Locale.ROOT).replace(" ", "_") + "_LANTERN_MAKER");
        evt.registerRecipeCategoryFinder(ModRegistry.LANTERN_MAKING_RECIPE_TYPE.get(), recipe -> category);
    }
}
