package com.blakebr0.mysticalagriculture.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

/**
 * Fuel, as Minecraft 26.3 defines it: a {@code cooking_fuel} component whose burn time is a
 * data-driven provider, resolved against the block doing the burning (blast furnaces burn
 * faster). Replaces the {@code FuelValues} 26.2 kept on the level.
 */
public final class FuelHelper {
    private FuelHelper() {
    }

    public static boolean isFuel(ItemStack stack) {
        return stack.has(DataComponents.COOKING_FUEL);
    }

    /** Ticks the stack burns for in the machine at {@code pos}; zero for a non-fuel or on a client. */
    public static int burnTime(Level level, BlockPos pos, ItemStack stack) {
        var fuel = stack.get(DataComponents.COOKING_FUEL);
        if (fuel == null || !(level instanceof ServerLevel serverLevel)) {
            return 0;
        }

        // The providers read the block state; this is the smallest parameter set that has it.
        var params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.BLOCK_STATE, level.getBlockState(pos))
                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos))
                .create(LootContextParamSets.COMMAND_COMPUTE_POSITION);
        return fuel.burnTime().get(new LootContext.Builder(params).create(java.util.Optional.empty()), 0);
    }
}
