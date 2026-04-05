package com.blakebr0.mysticalagriculture.crafting.recipe;

import com.blakebr0.mysticalagriculture.api.crafting.IAwakeningRecipe;
import com.blakebr0.mysticalagriculture.init.ModBlocks;
import com.blakebr0.mysticalagriculture.init.ModRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
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
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class AwakeningRecipe implements IAwakeningRecipe {
    public static final MapCodec<AwakeningRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Ingredient.CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
                    Ingredient.CODEC
                            .listOf()
                            .fieldOf("ingredients")
                            .flatXmap(
                                    field -> {
                                        var max = 4;
                                        var ingredients = field.toArray(Ingredient[]::new);
                                        if (ingredients.length == 0) {
                                            return DataResult.error(() -> "No ingredients for awakening recipe");
                                        } else {
                                            if (ingredients.length > max) {
                                                return DataResult.error(() -> "Too many ingredients for awakening recipe. The maximum is: %s".formatted(max));
                                            }

                                            return DataResult.success(Arrays.stream(ingredients).toList());
                                        }
                                    },
                                    DataResult::success
                            )
                            .forGetter(recipe -> recipe.inputs),
                    SizedIngredient.NESTED_CODEC
                            .listOf()
                            .fieldOf("essences")
                            .flatXmap(
                                    field -> {
                                        var max = 4;
                                        var ingredients = field.toArray(SizedIngredient[]::new);
                                        if (ingredients.length == 0) {
                                            return DataResult.error(() -> "No essences for awakening recipe");
                                        } else {
                                            if (ingredients.length > max) {
                                                return DataResult.error(() -> "Too many essences for awakening recipe. The maximum is: %s".formatted(max));
                                            }

                                            return DataResult.success(Arrays.stream(ingredients).toList());
                                        }
                                    },
                                    DataResult::success
                            )
                            .forGetter(recipe -> recipe.essences),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                    Codec.BOOL.optionalFieldOf("transfer_components", false).forGetter(recipe -> recipe.transferComponents)
            ).apply(builder, AwakeningRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, AwakeningRecipe> STREAM_CODEC = StreamCodec.of(
            AwakeningRecipe::toNetwork, AwakeningRecipe::fromNetwork
    );
    public static final RecipeSerializer<AwakeningRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Ingredient input;
    private final List<Ingredient> inputs;
    private final List<SizedIngredient> essences;
    private final ItemStackTemplate result;
    private final boolean transferComponents;
    private PlacementInfo placementInfo;
    // for CraftTweaker recipes
    private BiFunction<Integer, ItemStack, ItemStack> transformer;

    // the input is specified separately in JSON but is part of the ingredient list in practice
    public AwakeningRecipe(Ingredient input, List<Ingredient> ingredients, List<SizedIngredient> essences, ItemStackTemplate result, boolean transferComponents) {
        this.input = input;
        this.essences = essences;
        this.result = result;
        this.transferComponents = transferComponents;

        this.inputs = List.of(
                essences.get(0).ingredient(),
                ingredients.get(0),
                essences.get(1).ingredient(),
                ingredients.get(1),
                essences.get(2).ingredient(),
                ingredients.get(2),
                essences.get(3).ingredient(),
                ingredients.get(3)
        );
    }

    @Override
    public boolean matches(CraftingInput inventory, Level level) {
        // -1 ingredient for the input item
        if (this.inputs.size() != inventory.ingredientCount() - 1)
            return false;

        var input = inventory.getItem(0);
        if (!this.input.test(input))
            return false;

        var inputs = NonNullList.<ItemStack>create();

        for (var i = 1; i < inventory.size(); i++) {
            var item = inventory.getItem(i);
            if (!item.isEmpty()) {
                inputs.add(item);
            }
        }

        return RecipeMatcher.findMatches(inputs, this.inputs) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput inventory) {
        var stack = inventory.getItem(0);
        var result = this.result.create();

        if (this.transferComponents) {
            result.applyComponents(stack.getComponentsPatch());
        }

        return result;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            var ingredients = new ArrayList<Ingredient>();
            ingredients.add(this.input);
            ingredients.addAll(this.inputs);
            this.placementInfo = PlacementInfo.create(ingredients);
        }

        return this.placementInfo;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new ShapelessCraftingRecipeDisplay(
                this.placementInfo.ingredients().stream().map(Ingredient::display).toList(),
                new SlotDisplay.ItemStackSlotDisplay(this.result),
                new SlotDisplay.ItemSlotDisplay(ModBlocks.AWAKENING_ALTAR.get().asItem())
        ));
    }

    @Override
    public RecipeSerializer<AwakeningRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<IAwakeningRecipe> getType() {
        return ModRecipeTypes.AWAKENING.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput inventory) {
        var remaining = NonNullList.withSize(inventory.size(), ItemStack.EMPTY);
        // we need to track this separately since the recipe stores vessels and pedestals in alternating order,
        // while the recipe inventory stores them in sequential order
        var vesselIndex = 0;

        for (int i = 0; i < remaining.size(); i++) {
            var stack = inventory.getItem(i);

            // slot indexes 5 -> 8 are the essence vessels
            if (i > 4) {
                var input = this.inputs.get(vesselIndex);

                vesselIndex += 2;

                if (input.isEmpty())
                    continue;

                // the ingredient will have the same ItemStack instance as the essence
                // this *should* be the quickest way to find the exact essence in the recipe
//                TODO handle remaining items in awakening recipes
//                for (var essence : this.essences) {
//                    if (input.getValues() == essence) {
//                        remaining.set(i, StackHelper.shrink(stack, essence.count(), false));
//                        break;
//                    }
//                }
            } else {
                var remainder = stack.getCraftingRemainder();
                if (remainder != null) {
                    remaining.set(i, remainder.create());
                }

                if (this.transformer != null) {
                    var used = new boolean[remaining.size()];
                    var inputs = NonNullList.<Ingredient>create();

                    inputs.add(this.input);
                    inputs.addAll(this.inputs);

                    for (int j = 0; j < inputs.size(); j += 2) {
                        var input = inputs.get(j);

                        if (!used[j] && input.test(stack)) {
                            var index = Math.floorDiv(i, 2);
                            var ingredient = this.transformer.apply(index, stack);

                            used[j] = true;
                            remaining.set(i, ingredient);

                            break;
                        }
                    }
                }
            }
        }

        return remaining;
    }

    @Override
    public List<SizedIngredient> getEssences() {
        return this.essences;
    }

    @Override
    public Map<SizedIngredient, Integer> getMissingEssences(List<ItemStack> items) {
        var remaining = new ArrayList<>(this.essences);
        var missing = new LinkedHashMap<SizedIngredient, Integer>();

        for (var item : items) {
            for (var essence : remaining) {
                if (essence.ingredient().test(item)) {
                    var current = item.getCount();
                    var required = essence.count();

                    if (current < required) {
                        missing.put(essence, required - current);
                    }

                    remaining.remove(essence);

                    break;
                }
            }
        }

        for (var essence : remaining) {
            missing.put(essence, essence.count());
        }

        return missing;
    }

    public void setTransformer(BiFunction<Integer, ItemStack, ItemStack> transformer) {
        this.transformer = transformer;
    }

    private static AwakeningRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        var input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        var inputs = Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buffer);
        var essences = SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buffer);
        var result = ItemStackTemplate.STREAM_CODEC.decode(buffer);
        var transferComponents = buffer.readBoolean();

        return new AwakeningRecipe(input, inputs, essences, result, transferComponents);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, AwakeningRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);

        // only send the non-vessel ingredients
        for (int i = 1; i <= 7; i += 2) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.inputs.get(i));
        }

        SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buffer, recipe.essences);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeBoolean(recipe.transferComponents);
    }
}
