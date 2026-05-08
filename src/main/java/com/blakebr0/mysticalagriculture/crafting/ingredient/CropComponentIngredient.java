package com.blakebr0.mysticalagriculture.crafting.ingredient;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.init.ModIngredientTypes;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;
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
    private DataComponentPatch components;

    public CropComponentIngredient(Identifier crop, ComponentType type) {
        this.crop = crop;
        this.type = type;
        this.components = DataComponentPatch.EMPTY;
    }

    @Override
    public boolean test(@Nullable ItemStack input) {
        if (input == null)
            return false;

        return this.items().anyMatch(input::is) && this.testComponents(input);
    }

    @Override
    public Stream<Holder<Item>> items() {
        if (values == null) {
            var crop = CropRegistry.getInstance().getCropById(this.crop);
            this.values = switch (this.type) {
                case ESSENCE -> HolderSet.direct(crop.getTier().getEssenceItem().builtInRegistryHolder());
                case SEED -> HolderSet.direct(crop.getType().getCraftingSeedItem().builtInRegistryHolder());
                case MATERIAL -> {
                    var material = crop.getCraftingMaterial(RegistryAccess.EMPTY);
                    if (material != null) {
                        if (crop.getLazyIngredient().isTag()) {
                            MysticalAgriculture.LOGGER.warn("Crop {} has a tag ingredient, which is not supported properly by Crop Component ingredients", crop.getId());
                        }

                        var components = crop.getLazyIngredient().getComponents();
                        if (!components.isEmpty()) {
                            var builder = DataComponentPatch.builder();
                            for (var type : components) {
                                builder.set(type);
                            }
                            this.components = builder.build();
                        }

                        yield HolderSet.direct(material.items().toList());
                    }

                    yield HolderSet.empty();
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

    @Override
    public SlotDisplay display() {
        return new SlotDisplay.Composite(this.items().<SlotDisplay>map(item -> {
                    var template = new ItemStackTemplate(item, 1, components);
                    var display = new SlotDisplay.ItemStackSlotDisplay(template);
                    var remainder = item.value().getCraftingRemainder(template);
                    if (remainder != null) {
                        SlotDisplay remainderDisplay = new SlotDisplay.ItemStackSlotDisplay(remainder);
                        return new SlotDisplay.WithRemainder(display, remainderDisplay);
                    } else {
                        return display;
                    }
                })
                .toList());
    }

    public static Ingredient of(Identifier crop, ComponentType type) {
        return new CropComponentIngredient(crop, type).toVanilla();
    }

    private boolean testComponents(DataComponentGetter getter) {
        for (var entry : components.entrySet()) {
            var type = entry.getKey();
            var value = entry.getValue();
            if (value.isEmpty() && getter.has(type) || value.isPresent() && !value.get().equals(getter.get(type))) {
                return false; // One of the patch entries doesn't match
            }
        }
        return true; // Empty patch always matches
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
