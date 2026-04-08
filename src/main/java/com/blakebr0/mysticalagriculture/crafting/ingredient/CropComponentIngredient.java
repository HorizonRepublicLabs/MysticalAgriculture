package com.blakebr0.mysticalagriculture.crafting.ingredient;

import com.blakebr0.mysticalagriculture.init.ModIngredientTypes;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class CropComponentIngredient implements ICustomIngredient {
    public static final MapCodec<CropComponentIngredient> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Identifier.CODEC.fieldOf("crop").forGetter(ingredient -> ingredient.crop),
                    ComponentType.CODEC.fieldOf("component").forGetter(ingredient -> ingredient.type)
            ).apply(builder, CropComponentIngredient::new)
    );

    private final Identifier crop;
    private final ComponentType type;

    private HolderSet<Item> values;

    public CropComponentIngredient(Identifier crop, ComponentType type) {
        this.crop = crop;
        this.type = type;
    }

    @Override
    public boolean test(@Nullable ItemStack input) {
        if (input == null)
            return false;

        return this.items().anyMatch(s -> input.is(s) && s.components().equals(input.getComponents()));
    }

    @Override
    public Stream<Holder<Item>> items() {
        if (values == null) {
            var crop = CropRegistry.getInstance().getCropById(this.crop);
            this.values = switch (this.type) {
                case ESSENCE -> HolderSet.direct(crop.getTier().getEssenceItem().builtInRegistryHolder());
                case SEED -> HolderSet.direct(crop.getType().getCraftingSeedItem().builtInRegistryHolder());
                case MATERIAL -> {
                    var material = crop.getCraftingMaterial();
                    yield material != null ? HolderSet.direct(material.items().toList()) : HolderSet.empty();
                }
            };
        }

        return this.values.stream();
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return ModIngredientTypes.CROP_COMPONENT.get();
    }

    public static Ingredient of(Identifier crop, ComponentType type) {
        return new CropComponentIngredient(crop, type).toVanilla();
    }

    public enum ComponentType implements StringRepresentable {
        ESSENCE("essence"),
        SEED("seed"),
        MATERIAL("material");

        public static final Codec<ComponentType> CODEC = StringRepresentable.fromEnum(ComponentType::values);

        public final String name;

        ComponentType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
