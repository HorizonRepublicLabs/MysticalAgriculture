package com.blakebr0.mysticalagriculture.container.slot;

import com.blakebr0.cucumber.iface.IToggleableSlot;
import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import com.blakebr0.cucumber.inventory.slot.CSlot;
import com.blakebr0.mysticalagriculture.api.tinkering.IAugmentProvider;
import com.blakebr0.mysticalagriculture.api.tinkering.ITinkerable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class AugmentSlot extends CSlot implements IToggleableSlot {
    private final AbstractContainerMenu container;
    private final int augmentSlot;

    public AugmentSlot(AbstractContainerMenu container, CItemStacksHandler inventory, int index, int xPosition, int yPosition, int augmentSlot) {
        super(inventory, index, xPosition, yPosition);
        this.container = container;
        this.augmentSlot = augmentSlot;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        super.onTake(player, stack);
        this.container.slotsChanged(null);
    }

    @Override
    protected void setStackCopy(ItemStack stack) {
        super.setStackCopy(stack);
        this.container.slotsChanged(null);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (!super.mayPlace(stack) || !this.isActive())
            return false;

        var stackInSlot = this.getResourceHandler().getResource(0);
        var tinkerableItem = stackInSlot.getItem();
        var augmentItem = stack.getItem();

        if (tinkerableItem instanceof ITinkerable tinkerable && augmentItem instanceof IAugmentProvider augmentProvider) {
            var augment = augmentProvider.getAugment();

            return tinkerable.canApplyAugment(augment);
        }

        return false;
    }

    @Override
    public boolean isActive() {
        var stack = this.getResourceHandler().getResource(0);
        var item = stack.getItem();

        if (item instanceof ITinkerable tinkerable) {
            return this.augmentSlot < tinkerable.getAugmentSlots();
        }

        return false;
    }
}
