package com.blakebr0.mysticalagriculture.client.handler;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.client.properties.ExperienceCapsuleProperty;
import com.blakebr0.mysticalagriculture.client.properties.SoulJarProperty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;

public final class ItemModelPropertyHandler {
    @SubscribeEvent
    public void onRegisterSelectItemModelProperty(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(MysticalAgriculture.resource("experience_capsule"), ExperienceCapsuleProperty.MAP_CODEC);
        event.register(MysticalAgriculture.resource("soul_jar"), SoulJarProperty.MAP_CODEC);
    }
}
