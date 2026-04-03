package com.blakebr0.mysticalagriculture.lib;

import com.blakebr0.mysticalagriculture.api.MysticalAgricultureTags;
import com.blakebr0.mysticalagriculture.client.ModEquipmentAssets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

public final class ModArmorMaterials {
    public static final ArmorMaterial INFERIUM = new ArmorMaterial(
            40, Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 4);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 8);
                map.put(ArmorType.HELMET, 3);
            }),
            12, SoundEvents.ARMOR_EQUIP_GOLD,
            2.0F, 0.0F,
            MysticalAgricultureTags.Items.REPAIRS_INFERIUM_ARMOR,
            ModEquipmentAssets.INFERIUM
    );
    public static final ArmorMaterial PRUDENTIUM = new ArmorMaterial(
            60, Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 4);
                map.put(ArmorType.LEGGINGS, 7);
                map.put(ArmorType.CHESTPLATE, 8);
                map.put(ArmorType.HELMET, 4);
            }),
            14, SoundEvents.ARMOR_EQUIP_GOLD,
            2.25F, 0.0F,
            MysticalAgricultureTags.Items.REPAIRS_PRUDENTIUM_ARMOR,
            ModEquipmentAssets.PRUDENTIUM
    );
    public static final ArmorMaterial TERTIUM = new ArmorMaterial(
            80, Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 5);
                map.put(ArmorType.LEGGINGS, 8);
                map.put(ArmorType.CHESTPLATE, 9);
                map.put(ArmorType.HELMET, 4);
            }),
            16, SoundEvents.ARMOR_EQUIP_GOLD,
            2.5F, 0.0F,
            MysticalAgricultureTags.Items.REPAIRS_TERTIUM_ARMOR,
            ModEquipmentAssets.TERTIUM
    );
    public static final ArmorMaterial IMPERIUM = new ArmorMaterial(
            140, Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 5);
                map.put(ArmorType.LEGGINGS, 8);
                map.put(ArmorType.CHESTPLATE, 9);
                map.put(ArmorType.HELMET, 5);
            }),
            18, SoundEvents.ARMOR_EQUIP_GOLD,
            2.75F, 0.0F,
            MysticalAgricultureTags.Items.REPAIRS_IMPERIUM_ARMOR,
            ModEquipmentAssets.IMPERIUM
    );
    public static final ArmorMaterial SUPREMIUM = new ArmorMaterial(
            280, Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 6);
                map.put(ArmorType.LEGGINGS, 8);
                map.put(ArmorType.CHESTPLATE, 10);
                map.put(ArmorType.HELMET, 5);
            }),
            20, SoundEvents.ARMOR_EQUIP_GOLD,
            3.0F, 0.0F,
            MysticalAgricultureTags.Items.REPAIRS_SUPREMIUM_ARMOR,
            ModEquipmentAssets.SUPREMIUM
    );
    public static final ArmorMaterial AWAKENED_SUPREMIUM = new ArmorMaterial(
            320, Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 8);
                map.put(ArmorType.LEGGINGS, 10);
                map.put(ArmorType.CHESTPLATE, 12);
                map.put(ArmorType.HELMET, 6);
            }),
            22, SoundEvents.ARMOR_EQUIP_GOLD,
            3.5F, 0.0F,
            MysticalAgricultureTags.Items.REPAIRS_AWAKENED_SUPREMIUM_ARMOR,
            ModEquipmentAssets.AWAKENED_SUPREMIUM
    );
}
