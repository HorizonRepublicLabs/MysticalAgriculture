package com.blakebr0.mysticalagriculture.item;

import com.blakebr0.cucumber.item.BaseBlockItem;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.crop.ICropProvider;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class MysticalSeedsItem extends BaseBlockItem implements ICropProvider {
    private final Crop crop;

    public MysticalSeedsItem(Identifier id, Crop crop) {
        super(id, crop.getCropBlock());
        this.crop = crop;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.mysticalagriculture.mystical_seeds", this.crop.getDisplayName());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.crop.hasEffect(stack) || super.isFoil(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext level, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        var tier = this.crop.getTier().getDisplayName();

        builder.accept(ModTooltips.TIER.args(tier).toComponent());

        if (!this.crop.getModId().equals(MysticalAgriculture.MOD_ID)) {
            builder.accept(ModTooltips.getAddedByTooltip(this.crop.getModId()));
        }

        var biomes = this.crop.getRequiredBiomes();

        if (!biomes.isEmpty()) {
            builder.accept(ModTooltips.REQUIRED_BIOMES.toComponent());

            biomes.stream()
                    .map(Identifier::toString)
                    .map(s -> " - " + s)
                    .map(Component::literal)
                    .forEach(builder);
        }

        if (flag.isAdvanced()) {
            builder.accept(ModTooltips.CROP_ID.args(this.crop.getId().toString()).color(ChatFormatting.DARK_GRAY).toComponent());
        }
    }

    @Override
    public Crop getCrop() {
        return this.crop;
    }
}
