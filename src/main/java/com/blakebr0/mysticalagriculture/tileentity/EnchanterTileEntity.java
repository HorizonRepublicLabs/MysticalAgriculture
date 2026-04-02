package com.blakebr0.mysticalagriculture.tileentity;

import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import com.blakebr0.cucumber.inventory.OnContentsChangedFunction;
import com.blakebr0.cucumber.tileentity.BaseInventoryTileEntity;
import com.blakebr0.mysticalagriculture.container.EnchanterContainer;
import com.blakebr0.mysticalagriculture.init.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class EnchanterTileEntity extends BaseInventoryTileEntity implements MenuProvider {
    private final CItemStacksHandler inventory;

    public EnchanterTileEntity(BlockPos pos, BlockState state) {
        super(ModTileEntities.ENCHANTER.get(), pos, state);
        this.inventory = createInventoryHandler((slot) -> this.setChangedAndDispatch());
    }

    @Override
    public CItemStacksHandler getInventory() {
        return this.inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.mysticalagriculture.enchanter");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return EnchanterContainer.create(id, playerInventory, this.inventory, this.getBlockPos());
    }

    public static CItemStacksHandler createInventoryHandler() {
        return createInventoryHandler(null);
    }

    public static CItemStacksHandler createInventoryHandler(OnContentsChangedFunction onContentsChanged) {
        return CItemStacksHandler.create(3, onContentsChanged, handler -> {
            handler.addSlotLimit(0, 512);
            handler.addSlotLimit(1, 512);
        });
    }
}
