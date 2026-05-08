package com.blakebr0.mysticalagriculture.crafting.recipe;

import com.blakebr0.mysticalagriculture.api.crafting.IEnchanterRecipe;
import com.blakebr0.mysticalagriculture.init.ModRecipeTypes;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import java.util.Arrays;
import java.util.List;

public class EnchanterRecipe implements IEnchanterRecipe {
    public static final MapCodec<EnchanterRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    SizedIngredient.NESTED_CODEC
                            .listOf()
                            .fieldOf("ingredients")
                            .flatXmap(
                                    field -> {
                                        var max = 2;
                                        var ingredients = field.toArray(SizedIngredient[]::new);
                                        if (ingredients.length == 0) {
                                            return DataResult.error(() -> "No ingredients for enchanter recipe");
                                        } else {
                                            return ingredients.length > max
                                                    ? DataResult.error(() -> "Too many ingredients for enchanter recipe. The maximum is: %s".formatted(max))
                                                    : DataResult.success(Arrays.stream(ingredients).toList());
                                        }
                                    },
                                    DataResult::success
                            )
                            .forGetter(recipe -> recipe.inputs),
                    Enchantment.CODEC.fieldOf("enchantment").forGetter(recipe -> recipe.enchantment)
            ).apply(builder, EnchanterRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, EnchanterRecipe> STREAM_CODEC = StreamCodec.of(
            EnchanterRecipe::toNetwork, EnchanterRecipe::fromNetwork
    );
    public static final RecipeSerializer<EnchanterRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final List<SizedIngredient> inputs;
    private final Holder<Enchantment> enchantment;

    public EnchanterRecipe(List<SizedIngredient> inputs, Holder<Enchantment> enchantment) {
        this.inputs = inputs;
        this.enchantment = enchantment;
    }

    @Override
    public boolean matches(CraftingInput inventory, Level level) {
        if (this.inputs.size() != inventory.ingredientCount() - 1)
            return false;

        var inputs = NonNullList.<ItemStack>create();

        for (var i = 0; i < inventory.size() - 1; i++) {
            var item = inventory.getItem(i);
            if (!item.isEmpty()) {
                inputs.add(item);
            }
        }

        return RecipeMatcher.findMatches(inputs, this.inputs.stream().map(SizedIngredient::ingredient).toList()) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput inventory) {
        return this.assemble(inventory, this.getMaxResultEnchantmentLevel(inventory));
    }

    @Override
    public ItemStack assemble(CraftingInput inventory, int level) {
        var stack = inventory.getItem(2);

        if (this.enchantment.value().canEnchant(stack)) {
            var enchantments = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(stack));

            for (var enchantment : enchantments.keySet()) {
                if (enchantment == this.enchantment && enchantments.getLevel(enchantment) >= level)
                    return ItemStack.EMPTY;

                if (enchantment != this.enchantment && Enchantment.areCompatible(enchantment, this.enchantment))
                    return ItemStack.EMPTY;
            }

            enchantments.set(enchantment, level);

            var result = stack.copyWithCount(1);

            EnchantmentHelper.setEnchantments(result, enchantments.toImmutable());

            return result;
        }

        if (stack.is(Items.BOOK)) {
            return EnchantmentHelper.createBook(new EnchantmentInstance(this.enchantment, level));
        }

        return ItemStack.EMPTY;
    }

    @Override
    public List<SizedIngredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public Holder<Enchantment> getEnchantment() {
        return this.enchantment;
    }

    @Override
    public RecipeSerializer<EnchanterRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<IEnchanterRecipe> getType() {
        return ModRecipeTypes.ENCHANTER.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput inventory, int level) {
        var remaining = NonNullList.withSize(inventory.size(), ItemStack.EMPTY);

        for (int i = 0; i < 2; i++) {
            var stack = inventory.getItem(i);
            var count = this.inputs.get(i).count() * level;

            remaining.set(i, stack.copyWithCount(stack.getCount() - count));
        }

        var stack = inventory.getItem(2);
        if (stack.getCount() > 1) {
            remaining.set(2, stack.copyWithCount(stack.getCount() - 1));
        }

        return remaining;
    }

    @Override
    public int getMaxResultEnchantmentLevel(RecipeInput inventory) {
        var level = 0;

        for (var i = 0; i < this.inputs.size(); i++) {
            var stack = inventory.getItem(i);
            var count = this.inputs.get(i).count();

            var newLevel = stack.getCount() / count;

            if (level == 0 || newLevel < level) {
                level = Math.min(newLevel, this.enchantment.value().getMaxLevel());
            }
        }

        return level;
    }

    private static EnchanterRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        var inputs = SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buffer);
        var enchantment = Enchantment.STREAM_CODEC.decode(buffer);

        return new EnchanterRecipe(inputs, enchantment);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, EnchanterRecipe recipe) {
        SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buffer, recipe.inputs);
        Enchantment.STREAM_CODEC.encode(buffer, recipe.enchantment);
    }
}
