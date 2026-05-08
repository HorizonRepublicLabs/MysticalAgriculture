package com.blakebr0.mysticalagriculture.data.generator;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.data.recipe.CraftingRecipeBuilder;
import com.blakebr0.mysticalagriculture.data.recipe.InfusionRecipeBuilder;
import com.blakebr0.mysticalagriculture.data.recipe.ReprocessorRecipeBuilder;
import com.blakebr0.mysticalagriculture.lib.ModCrops;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class RecipeJsonGenerator extends RecipeProvider {
    public RecipeJsonGenerator(HolderLookup.Provider lookup, RecipeOutput output) {
        super(lookup, output);
    }

    @Override
    protected void buildRecipes() {
        for (var crop : CropRegistry.getInstance().getCrops()) {
//            if (crop != ModCrops.INFERIUM) {
//                {
//                    var id = MysticalAgriculture.resource("seed/crafting/" + crop.getName());
//                    CraftingRecipeBuilder.seed(id, crop).save(this.output);
//                }
//
//                {
//                    var id = MysticalAgriculture.resource("seed/infusion/" + crop.getName());
//                    InfusionRecipeBuilder.seed(id, crop).save(this.output);
//                }
//            }

            {
                var id = MysticalAgriculture.resource("seed/reprocessor/" + crop.getName());
                ReprocessorRecipeBuilder.seed(id, crop).save(this.output);
            }
        }
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new RecipeJsonGenerator(provider, output);
        }

        @Override
        public String getName() {
            return MysticalAgriculture.NAME + " recipe generator";
        }
    }
}
