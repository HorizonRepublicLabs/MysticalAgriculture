package com.blakebr0.mysticalagriculture.augment;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.api.lib.AbilityCache;
import com.blakebr0.mysticalagriculture.api.tinkering.Augment;
import com.blakebr0.mysticalagriculture.api.tinkering.AugmentType;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.EnumSet;

public class FlightAugment extends Augment {
    private static final Identifier ATTRIBUTE_ID = MysticalAgriculture.resource("flight_augment");

    public FlightAugment(Identifier id, int tier) {
        super(id, tier, EnumSet.of(AugmentType.CHESTPLATE), 0xCBD6D6, 0x556B6B);
    }

    @Override
    public void onPlayerTick(Level level, Player player, AbilityCache cache) {
        var abilities = player.getAbilities();

        if (!cache.isCached(this, player)) {
            var flight = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (flight == null)
                return;

            flight.addOrReplacePermanentModifier(new AttributeModifier(ATTRIBUTE_ID, 1, AttributeModifier.Operation.ADD_VALUE));

            cache.add(this, player, () -> {
                if (!abilities.instabuild && !player.isSpectator()) {
                    abilities.flying = false;

                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.onUpdateAbilities();
                    }
                }

                flight.removeModifier(ATTRIBUTE_ID);
            });
        }
    }
}
