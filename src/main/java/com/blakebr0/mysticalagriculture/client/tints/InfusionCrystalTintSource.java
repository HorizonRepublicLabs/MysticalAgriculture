package com.blakebr0.mysticalagriculture.client.tints;

import com.blakebr0.cucumber.helper.ColorHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record InfusionCrystalTintSource() implements ItemTintSource {
    public static final MapCodec<InfusionCrystalTintSource> MAP_CODEC = MapCodec.unit(InfusionCrystalTintSource::new);

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        float damage = (float) (stack.getMaxDamage() - stack.getDamageValue()) / stack.getMaxDamage();
        return ColorHelper.saturate(0x00D9D9, damage);
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
