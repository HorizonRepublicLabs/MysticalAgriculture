package com.blakebr0.mysticalagriculture.crafting.recipe;

import com.blakebr0.mysticalagriculture.api.crafting.IReprocessorRecipe;
import com.blakebr0.mysticalagriculture.init.ModBlocks;
import com.blakebr0.mysticalagriculture.init.ModRecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public class ReprocessorRecipe implements IReprocessorRecipe {
    public static final MapCodec<ReprocessorRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Ingredient.CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
            ).apply(builder, ReprocessorRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ReprocessorRecipe> STREAM_CODEC = StreamCodec.of(
            ReprocessorRecipe::toNetwork, ReprocessorRecipe::fromNetwork
    );
    public static final RecipeSerializer<ReprocessorRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Ingredient input;
    private final ItemStackTemplate result;

    private PlacementInfo placementInfo;

    public ReprocessorRecipe(Ingredient input, ItemStackTemplate result) {
        this.input = input;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput inventory, Level level) {
        return this.input.test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(CraftingInput inventory) {
        return this.result.create();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.input);
        }

        return this.placementInfo;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new ShapelessCraftingRecipeDisplay(
                List.of(this.input.display()),
                new SlotDisplay.ItemStackSlotDisplay(this.result),
                new SlotDisplay.ItemSlotDisplay(ModBlocks.REPROCESSOR.get().asItem())
        ));
    }

    @Override
    public RecipeSerializer<ReprocessorRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<IReprocessorRecipe> getType() {
        return ModRecipeTypes.REPROCESSOR.get();
    }

    private static ReprocessorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        var input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        var result = ItemStackTemplate.STREAM_CODEC.decode(buffer);

        return new ReprocessorRecipe(input, result);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, ReprocessorRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.result);
    }
}
