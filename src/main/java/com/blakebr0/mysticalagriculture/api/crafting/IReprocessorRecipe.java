package com.blakebr0.mysticalagriculture.api.crafting;

import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;

/**
 * Used to represent a Reprocessor recipe for the recipe type
 */
public interface IReprocessorRecipe extends Recipe<CraftingInput> {
    Ingredient getIngredient();

    @Override
    default boolean isSpecial() {
        return true;
    }

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

    @Override
    default PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }
}
