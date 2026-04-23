package com.blakebr0.mysticalagriculture.client.handler;

import com.blakebr0.mysticalagriculture.api.crafting.IAwakeningRecipe;
import com.blakebr0.mysticalagriculture.api.crafting.IEnchanterRecipe;
import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import com.blakebr0.mysticalagriculture.api.crafting.IOreInfusionRecipe;
import com.blakebr0.mysticalagriculture.api.crafting.IReprocessorRecipe;
import com.blakebr0.mysticalagriculture.api.crafting.ISoulExtractionRecipe;
import com.blakebr0.mysticalagriculture.api.crafting.ISouliumSpawnerRecipe;
import com.blakebr0.mysticalagriculture.init.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public final class ClientRecipeHandler {
    public static final List<RecipeHolder<IAwakeningRecipe>> AWAKENING_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<IEnchanterRecipe>> ENCHANTER_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<IInfusionRecipe>> INFUSION_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<IReprocessorRecipe>> REPROCESSOR_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<ISoulExtractionRecipe>> SOUL_EXTRACTION_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<ISouliumSpawnerRecipe>> SOULIUM_SPAWNER_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<IOreInfusionRecipe>> ORE_INFUSION_RECIPES = new ArrayList<>();

    @SubscribeEvent
    public void onRecipesReceived(RecipesReceivedEvent event) {
        var recipes = event.getRecipeMap();

        AWAKENING_RECIPES.addAll(recipes.byType(ModRecipeTypes.AWAKENING.get()));
        ENCHANTER_RECIPES.addAll(recipes.byType(ModRecipeTypes.ENCHANTER.get()));
        INFUSION_RECIPES.addAll(recipes.byType(ModRecipeTypes.INFUSION.get()));
        REPROCESSOR_RECIPES.addAll(recipes.byType(ModRecipeTypes.REPROCESSOR.get()));
        SOUL_EXTRACTION_RECIPES.addAll(recipes.byType(ModRecipeTypes.SOUL_EXTRACTION.get()));
        SOULIUM_SPAWNER_RECIPES.addAll(recipes.byType(ModRecipeTypes.SOULIUM_SPAWNER.get()));
        ORE_INFUSION_RECIPES.addAll(recipes.byType(ModRecipeTypes.ORE_INFUSION.get()));
    }

    @SubscribeEvent
    public void onClientPlayerLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        AWAKENING_RECIPES.clear();
        ENCHANTER_RECIPES.clear();
        INFUSION_RECIPES.clear();
        REPROCESSOR_RECIPES.clear();
        SOUL_EXTRACTION_RECIPES.clear();
        SOULIUM_SPAWNER_RECIPES.clear();
        ORE_INFUSION_RECIPES.clear();
    }
}
