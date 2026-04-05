package com.blakebr0.mysticalagriculture.api.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;

/**
 * Used to represent an Infusion recipe for the recipe type
 */
public interface IInfusionRecipe extends Recipe<CraftingInput> {
    /**
     * Returns the remaining items after a successful crafting operation
     *
     * @param input the crafting input
     * @return the remaining items
     */
    NonNullList<ItemStack> getRemainingItems(CraftingInput input);

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
}
