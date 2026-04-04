package com.blakebr0.mysticalagriculture.api.crafting;

import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;

/**
 * Used to represent a Reprocessor recipe for the recipe type
 */
public interface IReprocessorRecipe extends Recipe<CraftingInput> {
    @Override
    default String group() {
        return "mysticalagriculture:reprocessor";
    }

    @Override
    default boolean showNotification() {
        return false;
    }

    @Override
    default RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
