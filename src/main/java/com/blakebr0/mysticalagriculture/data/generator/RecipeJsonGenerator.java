package com.blakebr0.mysticalagriculture.data.generator;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.data.recipe.CraftingRecipeBuilder;
import com.blakebr0.mysticalagriculture.data.recipe.InfusionRecipeBuilder;
import com.blakebr0.mysticalagriculture.data.recipe.ReprocessorRecipeBuilder;
import com.blakebr0.mysticalagriculture.lib.ModCrops;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.data.recipes.RecipeProvider;


public class RecipeJsonGenerator extends RecipeProvider {
    // 26.3 made recipes a datapack registry: a provider writes into the registry's bootstrap
    // context and is wired up through GatherDataRegistryEntriesEvent (see ModDataGenerators).
    public RecipeJsonGenerator(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
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
}
