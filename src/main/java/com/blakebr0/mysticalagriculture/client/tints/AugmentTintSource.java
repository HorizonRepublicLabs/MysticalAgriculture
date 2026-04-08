package com.blakebr0.mysticalagriculture.client.tints;

import com.blakebr0.mysticalagriculture.registry.AugmentRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record AugmentTintSource(Identifier id, int index) implements ItemTintSource {
    public static final MapCodec<AugmentTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Identifier.CODEC.fieldOf("id").forGetter(AugmentTintSource::id),
                    Codec.INT.fieldOf("index").forGetter(AugmentTintSource::index)
            ).apply(builder, AugmentTintSource::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        var augment = AugmentRegistry.getInstance().getAugmentById(this.id);

        return switch (this.index) {
            case 0 -> augment.getPrimaryColor();
            case 1 -> augment.getSecondaryColor();
            default -> -1;
        };
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
