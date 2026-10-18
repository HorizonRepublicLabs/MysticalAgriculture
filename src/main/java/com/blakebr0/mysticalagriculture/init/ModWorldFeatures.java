package com.blakebr0.mysticalagriculture.init;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.world.feature.SoulstoneFeature;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// 26.3 registers a feature's codec as its type; configured instances are datapack entries
// under worldgen/feature.
public final class ModWorldFeatures {
    public static final DeferredRegister<MapCodec<? extends Feature>> REGISTRY = DeferredRegister.create(Registries.FEATURE_TYPE, MysticalAgriculture.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<SoulstoneFeature>> SOULSTONE = REGISTRY.register("soulstone", () -> SoulstoneFeature.CODEC);
}
