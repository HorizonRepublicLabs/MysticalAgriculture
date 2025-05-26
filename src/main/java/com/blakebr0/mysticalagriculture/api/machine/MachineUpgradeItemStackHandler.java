package com.blakebr0.mysticalagriculture.api.machine;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

/**
 * An implementation of {@link ItemStackHandler} for {@link IMachineUpgrade}s.
 */
public class MachineUpgradeItemStackHandler extends ItemStackHandler {
    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        var item = stack.getItem();
        return item instanceof IMachineUpgrade;
    }

    /**
     * Gets the {@link MachineUpgradeTier} for the upgrade in this inventory, or null if empty
     * @return the machine upgrade tier
     */
    @Nullable
    public MachineUpgradeTier getUpgradeTier() {
        var item = this.getStackInSlot(0).getItem();
        if (item instanceof IMachineUpgrade upgrade)
            return upgrade.getTier();

        return null;
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }
}
