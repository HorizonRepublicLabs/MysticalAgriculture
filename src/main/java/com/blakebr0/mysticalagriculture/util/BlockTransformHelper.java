package com.blakebr0.mysticalagriculture.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * What a hoe, shovel or axe turns a block into. 26.3 moved tilling, flattening and stripping
 * from item abilities into data-driven {@link BlockTransformer}s; this answers the question
 * {@code getToolModifiedState} used to, for any position, without performing the change.
 */
public final class BlockTransformHelper {
    private BlockTransformHelper() {
    }

    public static @Nullable BlockState transformedState(Level level, BlockPos pos, Direction face, ResourceKey<BlockTransformer> key) {
        var transformer = level.registryAccess().lookupOrThrow(Registries.BLOCK_TRANSFORMER).getOrThrow(key);
        for (var transform : transformer.value().transforms()) {
            if (transform.disallowedFaces().contains(face)) {
                continue;
            }

            var state = transform.blockStateProvider().value().getOptionalState(level, level.getRandom(), pos);
            if (state != null) {
                return state;
            }
        }

        return null;
    }
}
