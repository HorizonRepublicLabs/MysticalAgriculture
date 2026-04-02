package com.blakebr0.mysticalagriculture.augment;

import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;

public class NauseaResistanceAugment extends Augment {
    public NauseaResistanceAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.HELMET), 0x66BF00, 0x1F8057);
    }

    @Override
    public void onArmorTick(ItemStack stack, ServerLevel level, Player player) {
        if (player.hasEffect(MobEffects.NAUSEA)) {
            player.removeEffect(MobEffects.NAUSEA);
        }
    }
}
