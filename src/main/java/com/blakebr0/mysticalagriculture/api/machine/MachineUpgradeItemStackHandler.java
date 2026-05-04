package com.blakebr0.mysticalagriculture.api.machine;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStackResourceHandler;
import org.jetbrains.annotations.Nullable;

/**
 * An implementation of {@link ItemStackResourceHandler} for {@link IMachineUpgrade}s.
 */
public class MachineUpgradeItemStackHandler extends ItemStackResourceHandler {
    private ItemStack stack = ItemStack.EMPTY;

    /**
     * Set the resource in this handler
     * @param index unused
     * @param resource the resource
     * @param amount the amount of the resource
     */
    public void set(int index, ItemResource resource, int amount) {
        this.stack = resource.toStack(amount);
    }

    /**
     * Gets the {@link MachineUpgradeTier} for the upgrade in this inventory, or null if empty
     * @return the machine upgrade tier
     */
    @Nullable
    public MachineUpgradeTier getUpgradeTier() {
        var item = this.stack.getItem();
        if (item instanceof IMachineUpgrade upgrade)
            return upgrade.getTier();

        return null;
    }

    public ItemStack getStackCopy() {
        return this.stack.copy();
    }

    public void clear() {
        this.stack = ItemStack.EMPTY;
    }

    @Override
    protected int getCapacity(ItemResource resource) {
        return 1;
    }

    @Override
    protected boolean isValid(ItemResource resource) {
        return resource.getItem() instanceof IMachineUpgrade;
    }

    @Override
    protected ItemStack getStack() {
        return this.stack;
    }

    @Override
    protected void setStack(ItemStack stack) {
        this.stack = stack;
    }
}
