package com.blakebr0.mysticalagriculture.api.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;

import java.util.List;

/**
 * Used to represent an Infusion recipe for the recipe type
 */
public interface IInfusionRecipe extends Recipe<CraftingInput> {
    Ingredient getAltarIngredient();
    List<Ingredient> getPedestalIngredients();

    /**
     * Returns the remaining items after a successful crafting operation
     *
     * @param input the crafting input
     * @return the remaining items
     */
    NonNullList<ItemStack> getRemainingItems(CraftingInput input);

    @Override
    default boolean isSpecial() {
        return true;
    }

    @Override
    default String group() {
        return "mysticalagriculture:infusion";
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
