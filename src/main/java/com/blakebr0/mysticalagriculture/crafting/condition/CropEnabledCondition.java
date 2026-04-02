package com.blakebr0.mysticalagriculture.crafting.condition;

import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.conditions.ICondition;

public class CropEnabledCondition implements ICondition {
    public static final MapCodec<CropEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Identifier.CODEC.fieldOf("crop").forGetter(condition -> condition.crop)
            ).apply(builder, CropEnabledCondition::new)
    );

    private final Identifier crop;

    public CropEnabledCondition(Identifier crop) {
        this.crop = crop;
    }

    @Override
    public boolean test(IContext context) {
        var crop = CropRegistry.getInstance().getCropById(this.crop);
        return crop != null && crop.isEnabled();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
