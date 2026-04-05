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
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

public class RecipeJsonGenerator extends RecipeProvider {
    public RecipeJsonGenerator(HolderLookup.Provider lookup, RecipeOutput output) {
        super(lookup, output);
    }

    @Override
    protected void buildRecipes() {
//        for (var crop : CropRegistry.getInstance().getCrops()) {
//            if (crop != ModCrops.INFERIUM) {
//                var craftingId = "seed/crafting/" + crop.getName();
//                CraftingRecipeBuilder.newSeedRecipe(crop).build(consumer, Identifier.fromNamespaceAndPath(crop.getModId(), craftingId));
//
//                var infusionId = "seed/infusion/" + crop.getName();
//                InfusionRecipeBuilder.newSeedRecipe(crop).build(consumer, Identifier.fromNamespaceAndPath(crop.getModId(), infusionId));
//            }
//
//            var reprocessorId = "seed/reprocessor/" + crop.getName();
//            ReprocessorRecipeBuilder.newSeedReprocessingRecipe(crop).build(consumer, Identifier.fromNamespaceAndPath(crop.getModId(), reprocessorId));
//        }
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
