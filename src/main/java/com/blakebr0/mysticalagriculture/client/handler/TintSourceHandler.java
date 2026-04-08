package com.blakebr0.mysticalagriculture.client.handler;

import com.blakebr0.cucumber.iface.IColored;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.block.InfusedFarmlandBlock;
import com.blakebr0.mysticalagriculture.client.tints.AugmentTintSource;
import com.blakebr0.mysticalagriculture.client.tints.CropTintSource;
import com.blakebr0.mysticalagriculture.client.tints.InfusionCrystalTintSource;
import com.blakebr0.mysticalagriculture.client.tints.SoulJarTintSource;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

public final class TintSourceHandler {
    @SubscribeEvent
    public void onRegisterBlockTintSources(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(new IColored.BlockColors()), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));

        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.isFlowerColored() && crop.getCropBlock() != null)
                event.register(List.of(_ -> crop.getFlowerColor()), crop.getCropBlock());
        }
    }

    @SubscribeEvent
    public void onRegisterItemTintSources(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(MysticalAgriculture.resource("augment"), AugmentTintSource.MAP_CODEC);
        event.register(MysticalAgriculture.resource("crop"), CropTintSource.MAP_CODEC);
        event.register(MysticalAgriculture.resource("infusion_crystal"), InfusionCrystalTintSource.MAP_CODEC);
        event.register(MysticalAgriculture.resource("soul_jar"), SoulJarTintSource.MAP_CODEC);
    }
}
