package com.blakebr0.mysticalagriculture.tileentity;

import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import com.blakebr0.cucumber.tileentity.BaseInventoryTileEntity;
import com.blakebr0.mysticalagriculture.init.ModTileEntities;
import com.blakebr0.mysticalagriculture.util.RecipeIngredientCache;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class EssenceVesselTileEntity extends BaseInventoryTileEntity {
    private static final int MAX_STACK_SIZE = 40;
    private final CItemStacksHandler inventory;

    public EssenceVesselTileEntity(BlockPos pos, BlockState state) {
        super(ModTileEntities.ESSENCE_VESSEL.get(), pos, state);
        this.inventory = CItemStacksHandler.create(1, (slot) -> this.setChangedAndDispatch(), handler -> {
            handler.setDefaultSlotLimit(MAX_STACK_SIZE);
            handler.setCanInsert((slot, stack) -> canInsertStack(stack));
        });
    }

    @Override
    public CItemStacksHandler getInventory() {
        return this.inventory;
    }

    private static boolean canInsertStack(ItemStack stack) {
        return RecipeIngredientCache.INSTANCE.isValidVesselItem(stack);
    }
}
