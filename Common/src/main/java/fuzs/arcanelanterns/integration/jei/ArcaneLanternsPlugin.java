package fuzs.arcanelanterns.integration.jei;

import fuzs.arcanelanterns.ArcaneLanterns;
import fuzs.arcanelanterns.init.ModRegistry;
import fuzs.arcanelanterns.world.item.crafting.LanternMakingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;
import java.util.stream.Collectors;

@JeiPlugin
public class ArcaneLanternsPlugin implements IModPlugin {
    static final RecipeType<LanternMakingRecipe> LANTERN_MAKING_RECIPE_TYPE = RecipeType.create(ArcaneLanterns.MOD_ID, "lantern_making", LanternMakingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ArcaneLanterns.id("main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new LanternMakingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModRegistry.LANTERN_MAKER_BLOCK.get()), LANTERN_MAKING_RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(LANTERN_MAKING_RECIPE_TYPE, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRegistry.LANTERN_MAKING_RECIPE_TYPE.get()).stream().sorted(Comparator.comparing(Recipe::getId)).collect(Collectors.toList()));
        addLanternStackInfo(registration, ModRegistry.LANTERN_MAKER_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.LIFE_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.FERAL_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.LOVE_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.WAILING_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.BOREAL_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.BRILLIANT_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.WARDING_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.CONTAINING_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.WITHERING_LANTERN_BLOCK.get());
        addLanternStackInfo(registration, ModRegistry.CLOUD_LANTERN_BLOCK.get());
    }

    private static void addLanternStackInfo(IRecipeRegistration registration, Block block) {
        registration.addItemStackInfo(new ItemStack(block), Component.translatable(block.getDescriptionId() + ".info"));
    }
}
