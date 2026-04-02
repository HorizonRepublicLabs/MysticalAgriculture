package com.blakebr0.mysticalagriculture.item;

import com.blakebr0.cucumber.item.BaseWateringCanItem;
import com.blakebr0.mysticalagriculture.config.ModConfigs;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public class WateringCanItem extends BaseWateringCanItem {
    public WateringCanItem(Identifier id, int range, double chance) {
        super(id, range, chance);
    }

    public WateringCanItem(Identifier id, int range, double chance, Function<Properties, Properties> properties) {
        super(id, range, chance, properties);
    }

    @Override
    protected boolean allowFakePlayerWatering() {
        return ModConfigs.FAKE_PLAYER_WATERING.get();
    }
}
