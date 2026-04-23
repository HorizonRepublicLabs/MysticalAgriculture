package com.blakebr0.mysticalagriculture.init;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.crafting.recipe.AwakeningRecipe;
import com.blakebr0.mysticalagriculture.crafting.recipe.EnchanterRecipe;
import com.blakebr0.mysticalagriculture.crafting.recipe.FarmlandTillRecipe;
import com.blakebr0.mysticalagriculture.crafting.recipe.InfusionRecipe;
import com.blakebr0.mysticalagriculture.crafting.recipe.OreInfusionRecipe;
import com.blakebr0.mysticalagriculture.crafting.recipe.ReprocessorRecipe;
import com.blakebr0.mysticalagriculture.crafting.recipe.SoulExtractionRecipe;
import com.blakebr0.mysticalagriculture.crafting.recipe.SoulJarEmptyRecipe;
import com.blakebr0.mysticalagriculture.crafting.recipe.SouliumSpawnerRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MysticalAgriculture.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> CRAFTING_FARMLAND_TILL = REGISTRY.register("farmland_till", () -> FarmlandTillRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> INFUSION = REGISTRY.register("infusion", () -> InfusionRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> AWAKENING = REGISTRY.register("awakening", () -> AwakeningRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> ENCHANTER = REGISTRY.register("enchanter", () -> EnchanterRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> REPROCESSOR = REGISTRY.register("reprocessor", () -> ReprocessorRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> SOUL_EXTRACTION = REGISTRY.register("soul_extraction", () -> SoulExtractionRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> SOULIUM_SPAWNER = REGISTRY.register("soulium_spawner", () -> SouliumSpawnerRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> ORE_INFUSION = REGISTRY.register("ore_infusion", () -> OreInfusionRecipe.SERIALIZER);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> CRAFTING_SOUL_JAR_EMPTY = REGISTRY.register("soul_jar_empty", () -> SoulJarEmptyRecipe.SERIALIZER);
}
