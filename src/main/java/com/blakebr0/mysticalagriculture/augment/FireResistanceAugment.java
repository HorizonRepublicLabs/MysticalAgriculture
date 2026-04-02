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

public class FireResistanceAugment extends Augment {
    public FireResistanceAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.ARMOR), 0xFFAA3F, 0x623E4E);
    }

    @Override
    public void onArmorTick(ItemStack stack, ServerLevel level, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 5, 0, true, false));

        if (player.isOnFire()) {
            player.clearFire();
        }
    }
}
