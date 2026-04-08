package com.blakebr0.mysticalagriculture.client.tints;

import com.blakebr0.mysticalagriculture.api.util.MobSoulUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record SoulJarTintSource() implements ItemTintSource {
    public static final MapCodec<SoulJarTintSource> MAP_CODEC = MapCodec.unit(SoulJarTintSource::new);

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        var type = MobSoulUtils.getType(stack);
        return type != null ? type.getColor() : 0;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
