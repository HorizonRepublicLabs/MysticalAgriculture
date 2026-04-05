package com.blakebr0.mysticalagriculture.api.crafting;

import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;

import java.util.Optional;

public interface ISouliumSpawnerRecipe extends Recipe<CraftingInput> {
    WeightedList<EntityType<?>> getEntityTypes();
    EntityType<?> getFirstEntityType();
    Optional<EntityType<?>> getRandomEntityType(RandomSource random);

    /**
     * Get the count for the ingredient at the requested index
     */
    int getCount();

    @Override
    default String group() {
        return "mysticalagriculture:soulium_spawner";
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
