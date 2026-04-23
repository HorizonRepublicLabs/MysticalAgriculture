package com.blakebr0.mysticalagriculture.init;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.container.EnchanterContainer;
import com.blakebr0.mysticalagriculture.container.EssenceFurnaceContainer;
import com.blakebr0.mysticalagriculture.container.HarvesterContainer;
import com.blakebr0.mysticalagriculture.container.OreInfuserContainer;
import com.blakebr0.mysticalagriculture.container.ReprocessorContainer;
import com.blakebr0.mysticalagriculture.container.SoulExtractorContainer;
import com.blakebr0.mysticalagriculture.container.SouliumSpawnerContainer;
import com.blakebr0.mysticalagriculture.container.TinkeringTableContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, MysticalAgriculture.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<TinkeringTableContainer>> TINKERING_TABLE = REGISTRY.register("tinkering_table", () -> new MenuType<>((IContainerFactory<TinkeringTableContainer>) TinkeringTableContainer::new, FeatureFlagSet.of()));
    public static final DeferredHolder<MenuType<?>, MenuType<EnchanterContainer>> ENCHANTER = REGISTRY.register("enchanter", () -> new MenuType<>((IContainerFactory<EnchanterContainer>) EnchanterContainer::new, FeatureFlagSet.of()));
    public static final DeferredHolder<MenuType<?>, MenuType<EssenceFurnaceContainer>> FURNACE = REGISTRY.register("furnace", () -> new MenuType<>((IContainerFactory<EssenceFurnaceContainer>) EssenceFurnaceContainer::new, FeatureFlagSet.of()));
    public static final DeferredHolder<MenuType<?>, MenuType<ReprocessorContainer>> REPROCESSOR = REGISTRY.register("reprocessor", () -> new MenuType<>((IContainerFactory<ReprocessorContainer>) ReprocessorContainer::new, FeatureFlagSet.of()));
    public static final DeferredHolder<MenuType<?>, MenuType<SoulExtractorContainer>> SOUL_EXTRACTOR = REGISTRY.register("soul_extractor", () -> new MenuType<>((IContainerFactory<SoulExtractorContainer>) SoulExtractorContainer::new, FeatureFlagSet.of()));
    public static final DeferredHolder<MenuType<?>, MenuType<HarvesterContainer>> HARVESTER = REGISTRY.register("harvester", () -> new MenuType<>((IContainerFactory<HarvesterContainer>) HarvesterContainer::new, FeatureFlagSet.of()));
    public static final DeferredHolder<MenuType<?>, MenuType<SouliumSpawnerContainer>> SOULIUM_SPAWNER = REGISTRY.register("soulium_spawner", () -> new MenuType<>((IContainerFactory<SouliumSpawnerContainer>) SouliumSpawnerContainer::new, FeatureFlagSet.of()));
    public static final DeferredHolder<MenuType<?>, MenuType<OreInfuserContainer>> ORE_INFUSER = REGISTRY.register("ore_infuser", () -> new MenuType<>((IContainerFactory<OreInfuserContainer>) OreInfuserContainer::new, FeatureFlagSet.of()));
}
