package com.blakebr0.mysticalagriculture.container.slot;

import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import com.blakebr0.cucumber.inventory.slot.CSlot;
import com.blakebr0.mysticalagriculture.api.util.AugmentUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class TinkerableSlot extends CSlot {
    private final AbstractContainerMenu container;

    public TinkerableSlot(AbstractContainerMenu container, CItemStacksHandler inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.container = container;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        var inventory = this.getResourceHandler();
        try (var tx = Transaction.openRoot()) {
            for (int i = 0; i < 2; i++) {
                var resource = inventory.getResource(i + 1);
                if (resource.isEmpty())
                    continue;

                inventory.extract(i + 1, resource, 1, tx);
            }

            tx.commit();
        }
    }

    @Override
    protected void setStackCopy(ItemStack stack) {
        var inventory = this.getResourceHandler();
        try (var tx = Transaction.openRoot()) {
            for (int i = 0; i < 2; i++) {
                var augmentStack = inventory.getResource(i + 1);
                if (!augmentStack.isEmpty()) {
                    inventory.extract(i + 1, augmentStack, augmentStack.getMaxStackSize(), tx);
                }

                var augment = AugmentUtils.getAugment(stack, i);
                if (augment != null) {
                    inventory.insert(i + 1, ItemResource.of(augment.getItem()), stack.count(), tx);
                }
            }

            tx.commit();
        }

        super.setStackCopy(stack);
        this.container.slotsChanged(null);
    }
}
