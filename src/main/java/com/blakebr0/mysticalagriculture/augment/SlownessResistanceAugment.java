package com.blakebr0.mysticalagriculture.augment;

import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;

public class SlownessResistanceAugment extends Augment {
    public SlownessResistanceAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.LEGGINGS), 0x333333, 0x111111);
    }

    @Override
    public void onArmorTick(ItemStack stack, ServerLevel level, Player player) {
        if (player.hasEffect(MobEffects.SLOWNESS)) {
            player.removeEffect(MobEffects.SLOWNESS);
        }
    }
}
