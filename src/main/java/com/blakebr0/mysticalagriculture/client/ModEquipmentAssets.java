package com.blakebr0.mysticalagriculture.client;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public final class ModEquipmentAssets {
    public static final ResourceKey<EquipmentAsset> INFERIUM = ResourceKey.create(EquipmentAssets.ROOT_ID, MysticalAgriculture.resource("inferium"));
    public static final ResourceKey<EquipmentAsset> PRUDENTIUM = ResourceKey.create(EquipmentAssets.ROOT_ID, MysticalAgriculture.resource("prudentium"));
    public static final ResourceKey<EquipmentAsset> TERTIUM = ResourceKey.create(EquipmentAssets.ROOT_ID, MysticalAgriculture.resource("tertium"));
    public static final ResourceKey<EquipmentAsset> IMPERIUM = ResourceKey.create(EquipmentAssets.ROOT_ID, MysticalAgriculture.resource("imperium"));
    public static final ResourceKey<EquipmentAsset> SUPREMIUM = ResourceKey.create(EquipmentAssets.ROOT_ID, MysticalAgriculture.resource("supremium"));
    public static final ResourceKey<EquipmentAsset> AWAKENED_SUPREMIUM = ResourceKey.create(EquipmentAssets.ROOT_ID, MysticalAgriculture.resource("awakened_supremium"));
}
