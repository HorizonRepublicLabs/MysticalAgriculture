package com.blakebr0.mysticalagriculture.item;

import com.blakebr0.cucumber.item.BaseItem;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.crop.ICropProvider;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class MysticalEssenceItem extends BaseItem implements ICropProvider {
    private final Crop crop;

    public MysticalEssenceItem(Identifier id, Crop crop) {
        super(id);
        this.crop = crop;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.mysticalagriculture.mystical_essence", this.crop.getDisplayName());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.crop.hasEffect(stack) || super.isFoil(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        if (!this.crop.getModId().equals(MysticalAgriculture.MOD_ID)) {
            builder.accept(ModTooltips.getAddedByTooltip(this.crop.getModId()));
        }
    }

    @Override
    public Crop getCrop() {
        return this.crop;
    }
}
