package com.blakebr0.mysticalagriculture.init;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.world.modifiers.InferiumOreModifier;
import com.blakebr0.mysticalagriculture.world.modifiers.ProsperityOreModifier;
import com.blakebr0.mysticalagriculture.world.modifiers.SoulstoneModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModBiomeModifiers {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, MysticalAgriculture.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<InferiumOreModifier>> INFERIUM_ORE = REGISTRY.register("inferium_ore", () -> InferiumOreModifier.MAP_CODEC);
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ProsperityOreModifier>> PROSPERITY_ORE = REGISTRY.register("prosperity_ore", () -> ProsperityOreModifier.MAP_CODEC);
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<SoulstoneModifier>> SOULSTONE = REGISTRY.register("soulstone", () -> SoulstoneModifier.MAP_CODEC);
}
