package com.blakebr0.mysticalagriculture.augment;

import com.blakebr0.cucumber.helper.ColorHelper;
import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;

public class JumpBoostAugment extends Augment {
    private final int amplifier;

    public JumpBoostAugment(Identifier id, int tier, int amplifier) {
        super(id, tier, EnumSet.of(AugmentType.BOOTS), getColor(0xFAFAFA, tier), getColor(0x458FAB, tier));
        this.amplifier = amplifier;
    }

    @Override
    public void onArmorTick(ItemStack stack, ServerLevel level, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 5, this.amplifier, true, false));
    }

    private static int getColor(int color, int tier) {
        return ColorHelper.saturate(color, Math.min((float) tier / 4, 1));
    }
}
