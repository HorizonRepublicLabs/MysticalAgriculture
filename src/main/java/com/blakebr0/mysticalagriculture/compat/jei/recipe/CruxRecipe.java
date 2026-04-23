package com.blakebr0.mysticalagriculture.compat.jei.recipe;

import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import com.google.common.collect.Lists;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.stream.Collectors;

public record CruxRecipe(ItemStack seed, ItemStack crux, ItemStack essence) {
    private static List<Block> farmlands;

    public List<Ingredient> getIngredients() {
        return Lists.newArrayList(
                Ingredient.of(this.seed.getItem()),
                Ingredient.of(farmlands.toArray(new Block[0])),
                Ingredient.of(this.crux.getItem())
        );
    }

    public static List<CruxRecipe> createAll() {
        farmlands = CropRegistry.getInstance().getCrops()
                .stream()
                .map(c -> c.getTier().getFarmlandBlock())
                .distinct()
                .collect(Collectors.toList());

        return CropRegistry.getInstance().getCrops()
                .stream()
                .filter(c -> c.getCruxBlock() != null)
                .map(c -> new CruxRecipe(
                        new ItemStack(c.getSeedsItem()),
                        new ItemStack(c.getCruxBlock()),
                        new ItemStack(c.getEssenceItem())
                ))
                .collect(Collectors.toList());
    }
}
