package com.blakebr0.mysticalagriculture.data;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.data.generator.BlockModelJsonGenerator;
import com.blakebr0.mysticalagriculture.data.generator.BlockTagsJsonGenerator;
import com.blakebr0.mysticalagriculture.data.generator.ItemModelJsonGenerator;
import com.blakebr0.mysticalagriculture.data.generator.ItemTagsJsonGenerator;
import com.blakebr0.mysticalagriculture.data.generator.RecipeJsonGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ModDataGenerators {
    @SubscribeEvent
    public void onGatherData(GatherDataEvent.Client event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        event.addProvider(new BlockModelJsonGenerator(packOutput, MysticalAgriculture.MOD_ID));
        event.addProvider(new ItemModelJsonGenerator(packOutput, MysticalAgriculture.MOD_ID));

        event.addProvider(new RecipeJsonGenerator.Runner(packOutput, lookupProvider));
        event.addProvider(new BlockTagsJsonGenerator(packOutput, lookupProvider, MysticalAgriculture.MOD_ID));
        event.addProvider(new ItemTagsJsonGenerator(packOutput, lookupProvider, MysticalAgriculture.MOD_ID));
    }
}
