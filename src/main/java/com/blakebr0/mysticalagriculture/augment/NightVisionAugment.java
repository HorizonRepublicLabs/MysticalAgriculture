package com.blakebr0.mysticalagriculture.augment;

import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;

public class NightVisionAugment extends Augment {
    public NightVisionAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.HELMET), 0xEEE050, 0x2B1E74);
    }

    @Override
    public void onArmorTick(ItemStack stack, ServerLevel level, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0, true, false));
    }
}
