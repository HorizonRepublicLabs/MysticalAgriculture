package com.blakebr0.mysticalagriculture.item;

import com.blakebr0.cucumber.item.BaseItem;
import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.IAugmentProvider;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class AugmentItem extends BaseItem implements IAugmentProvider {
    private final Augment augment;

    public AugmentItem(Identifier id, Augment augment) {
        super(id);
        this.augment = augment;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.mysticalagriculture.augment", this.augment.getDisplayName());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.augment.hasEffect();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        builder.accept(ModTooltips.getTooltipForTier(this.augment.getTier()));
        builder.accept(this.augment.getDescriptionDisplayText());

        if (this.augment.hasSetBonus()) {
            builder.accept(ModTooltips.SET_BONUS.args(this.augment.getSetBonusDisplayText()).toComponent());
        }

        if (flag.isAdvanced()) {
            builder.accept(ModTooltips.AUGMENT_ID.args(this.augment.getId().toString()).color(ChatFormatting.DARK_GRAY).toComponent());
        }
    }

    @Override
    public Augment getAugment() {
        return this.augment;
    }
}
