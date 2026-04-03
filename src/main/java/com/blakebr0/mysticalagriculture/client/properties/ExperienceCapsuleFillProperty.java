package com.blakebr0.mysticalagriculture.client.properties;

import com.blakebr0.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record ExperienceCapsuleFillProperty() implements RangeSelectItemModelProperty {
    public static final MapCodec<ExperienceCapsuleFillProperty> MAP_CODEC = MapCodec.unit(new ExperienceCapsuleFillProperty());

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        int experience = ExperienceCapsuleUtils.getExperience(stack);
        if (experience > 0) {
            double xpLevel = (double) experience / ExperienceCapsuleUtils.MAX_XP_POINTS;
            return (int) (xpLevel * 10);
        }

        return 0.0F;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return null;
    }
}
