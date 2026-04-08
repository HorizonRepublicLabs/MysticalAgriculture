package com.blakebr0.mysticalagriculture.item;

import com.blakebr0.cucumber.item.BaseItem;
import com.blakebr0.mysticalagriculture.api.util.MobSoulUtils;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Consumer;

public class SoulJarItem extends BaseItem {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    static {
        DECIMAL_FORMAT.setRoundingMode(RoundingMode.DOWN);
    }

    public SoulJarItem(Identifier id) {
        super(id, p -> p.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        var type = MobSoulUtils.getType(stack);
        if (type != null) {
            var entityName = type.getEntityDisplayName();
            var souls = DECIMAL_FORMAT.format(MobSoulUtils.getSouls(stack));
            var requirement = DECIMAL_FORMAT.format(type.getSoulRequirement());

            builder.accept(Component.literal("%s (%s/%s)".formatted(entityName.getString(), souls, requirement)).withStyle(ChatFormatting.GRAY));

            if (flag.isAdvanced()) {
                builder.accept(ModTooltips.MST_ID.args(type.getId().toString()).color(ChatFormatting.DARK_GRAY).toComponent());
            }
        }
    }
}
