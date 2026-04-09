package com.blakebr0.mysticalagriculture.api;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class MysticalAgricultureTags {
    public interface Blocks {
        TagKey<Block> CROPS = BlockTags.create(MysticalAgricultureAPI.resource("crops"));

        TagKey<Block> ALWAYS_EFFECTIVE_FARMLAND = BlockTags.create(MysticalAgricultureAPI.resource("always_effective_farmland"));

        TagKey<Block> INCORRECT_FOR_INFERIUM_TOOL = BlockTags.create(MysticalAgricultureAPI.resource("incorrect_for_inferium_tool"));
        TagKey<Block> INCORRECT_FOR_PRUDENTIUM_TOOL = BlockTags.create(MysticalAgricultureAPI.resource("incorrect_for_prudentium_tool"));
        TagKey<Block> INCORRECT_FOR_TERTIUM_TOOL = BlockTags.create(MysticalAgricultureAPI.resource("incorrect_for_tertium_tool"));
        TagKey<Block> INCORRECT_FOR_IMPERIUM_TOOL = BlockTags.create(MysticalAgricultureAPI.resource("incorrect_for_imperium_tool"));
        TagKey<Block> INCORRECT_FOR_SUPREMIUM_TOOL = BlockTags.create(MysticalAgricultureAPI.resource("incorrect_for_supremium_tool"));
        TagKey<Block> INCORRECT_FOR_AWAKENED_SUPREMIUM_TOOL = BlockTags.create(MysticalAgricultureAPI.resource("incorrect_for_awakened_supremium_tool"));
        TagKey<Block> INCORRECT_FOR_SOULIUM_TOOL = BlockTags.create(MysticalAgricultureAPI.resource("incorrect_for_soulium_tool"));
    }

    public interface Items {
        TagKey<Item> ESSENCES = ItemTags.create(MysticalAgricultureAPI.resource("essences"));
        TagKey<Item> SEEDS = ItemTags.create(MysticalAgricultureAPI.resource("seeds"));

        TagKey<Item> REPAIRS_INFERIUM_ARMOR = ItemTags.create(MysticalAgricultureAPI.resource("repairs_inferium_armor"));
        TagKey<Item> REPAIRS_PRUDENTIUM_ARMOR = ItemTags.create(MysticalAgricultureAPI.resource("repairs_prudentium_armor"));
        TagKey<Item> REPAIRS_TERTIUM_ARMOR = ItemTags.create(MysticalAgricultureAPI.resource("repairs_tertium_armor"));
        TagKey<Item> REPAIRS_IMPERIUM_ARMOR = ItemTags.create(MysticalAgricultureAPI.resource("repairs_imperium_armor"));
        TagKey<Item> REPAIRS_SUPREMIUM_ARMOR = ItemTags.create(MysticalAgricultureAPI.resource("repairs_supremium_armor"));
        TagKey<Item> REPAIRS_AWAKENED_SUPREMIUM_ARMOR = ItemTags.create(MysticalAgricultureAPI.resource("repairs_awakened_supremium_armor"));

        TagKey<Item> INFERIUM_TOOL_MATERIALS = ItemTags.create(MysticalAgricultureAPI.resource("inferium_tool_materials"));
        TagKey<Item> PRUDENTIUM_TOOL_MATERIALS = ItemTags.create(MysticalAgricultureAPI.resource("prudentium_tool_materials"));
        TagKey<Item> TERTIUM_TOOL_MATERIALS = ItemTags.create(MysticalAgricultureAPI.resource("tertium_tool_materials"));
        TagKey<Item> IMPERIUM_TOOL_MATERIALS = ItemTags.create(MysticalAgricultureAPI.resource("imperium_tool_materials"));
        TagKey<Item> SUPREMIUM_TOOL_MATERIALS = ItemTags.create(MysticalAgricultureAPI.resource("supremium_tool_materials"));
        TagKey<Item> AWAKENED_SUPREMIUM_TOOL_MATERIALS = ItemTags.create(MysticalAgricultureAPI.resource("awakened_supremium_tool_materials"));
        TagKey<Item> SOULIUM_TOOL_MATERIALS = ItemTags.create(MysticalAgricultureAPI.resource("soulium_tool_materials"));

        TagKey<Item> MYSTICAL_ENLIGHTENMENT_ENCHANTABLE = ItemTags.create(MysticalAgricultureAPI.resource("enchantable/mystical_enlightenment"));
        TagKey<Item> SOUL_SIPHONER_ENCHANTABLE = ItemTags.create(MysticalAgricultureAPI.resource("enchantable/soul_siphoner"));
    }
}
