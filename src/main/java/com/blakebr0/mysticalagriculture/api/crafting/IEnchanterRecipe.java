package com.blakebr0.mysticalagriculture.api.crafting;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Used to represent an Enchanter recipe for the recipe type
 */
public interface IEnchanterRecipe extends Recipe<CraftingInput> {
    Holder<Enchantment> getEnchantment();

    /**
     * Get the count for the ingredient at the requested index
     *
     * @param index the ingredient index
     * @return either the count or -1 if invalid
     */
    int getCount(int index);

    /**
     * Get the maximum enchantment level for the provided recipe input
     *
     * @param input the recipe input
     * @return the resulting enchantment level
     */
    int getMaxResultEnchantmentLevel(RecipeInput input);

    /**
     * Special case version of {@link Recipe#assemble(RecipeInput, HolderLookup.Provider)} that will only return the
     * newly enchanted item with the provided level or empty
     *
     * @param input the recipe input
     * @param provider the lookup provider
     * @param level the required enchantment level
     * @return the enchanted item or empty
     */
    ItemStack assemble(CraftingInput input, HolderLookup.Provider provider, int level);

    /**
     * Special case version of {@link Recipe#getRemainingItems(RecipeInput)} that takes in the enchantment level instead
     * of using the max value provided by {@link IEnchanterRecipe#getMaxResultEnchantmentLevel(RecipeInput)}
     *
     * @param input the recipe input
     * @param level the required enchantment level
     * @return the remaining items for a craft with the specified enchantment level
     */
    NonNullList<ItemStack> getRemainingItems(CraftingInput input, int level);
}
