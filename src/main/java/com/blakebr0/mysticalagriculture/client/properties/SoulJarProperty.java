package com.blakebr0.mysticalagriculture.client.properties;

import com.blakebr0.mysticalagriculture.api.util.MobSoulUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record SoulJarProperty() implements RangeSelectItemModelProperty {
    public static final MapCodec<SoulJarProperty> MAP_CODEC = MapCodec.unit(SoulJarProperty::new);

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        var type = MobSoulUtils.getType(stack);
        if (type != null) {
            double souls = MobSoulUtils.getSouls(stack);
            if (souls > 0) {
                return (int) ((souls / type.getSoulRequirement()) * 9);
            }
        }

        return 0.0F;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}
