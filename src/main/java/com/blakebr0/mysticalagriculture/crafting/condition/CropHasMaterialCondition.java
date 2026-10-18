package com.blakebr0.mysticalagriculture.crafting.condition;

import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.common.conditions.ICondition;

public class CropHasMaterialCondition implements ICondition {
    public static final MapCodec<CropHasMaterialCondition> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Identifier.CODEC.fieldOf("crop").forGetter(condition -> condition.crop)
            ).apply(builder, CropHasMaterialCondition::new)
    );

    private final Identifier crop;

    public CropHasMaterialCondition(Identifier crop) {
        this.crop = crop;
    }

    @Override
    public boolean test(IContext context) {
        var crop = CropRegistry.getInstance().getCropById(this.crop);
        if (crop == null)
            return false;

        // Recipes load before tags are bound in 26.3, so a tag material is looked up through
        // the context, which sees the tags loaded so far, rather than through the registries.
        var lazy = crop.getLazyIngredient();
        if (lazy.isTag()) {
            var tag = TagKey.create(Registries.ITEM, Identifier.parse(lazy.getId()));
            return !context.getTag(tag).isEmpty();
        }

        var material = crop.getCraftingMaterial(context.registries());

        return material != null && !material.isEmpty();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
