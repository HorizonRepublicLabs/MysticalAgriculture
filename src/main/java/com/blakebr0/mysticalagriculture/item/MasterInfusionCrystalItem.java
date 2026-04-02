package com.blakebr0.mysticalagriculture.item;

import com.blakebr0.cucumber.item.BaseReusableItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class MasterInfusionCrystalItem extends BaseReusableItem {
    public MasterInfusionCrystalItem(Identifier id) {
        super(id, 0);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
