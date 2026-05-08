package com.blakebr0.mysticalagriculture.handler;

import com.blakebr0.mysticalagriculture.init.ModTileEntities;
import com.blakebr0.mysticalagriculture.tileentity.EssenceFurnaceTileEntity;
import com.blakebr0.mysticalagriculture.tileentity.OreInfuserTileEntity;
import com.blakebr0.mysticalagriculture.tileentity.ReprocessorTileEntity;
import com.blakebr0.mysticalagriculture.tileentity.SoulExtractorTileEntity;
import com.blakebr0.mysticalagriculture.tileentity.SouliumSpawnerTileEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public final class RegisterCapabilityHandler {
    @SubscribeEvent
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.AWAKENING_ALTAR.get(), (block, _) -> block.getInventory());
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.AWAKENING_PEDESTAL.get(), (block, _) -> block.getInventory());
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.ESSENCE_VESSEL.get(), (block, _) -> block.getInventory());
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.FURNACE.get(), EssenceFurnaceTileEntity::getSidedInventory);
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.HARVESTER.get(), (block, _) -> block.getInventory());
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.INFUSION_ALTAR.get(), (block, _) -> block.getInventory());
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.INFUSION_PEDESTAL.get(), (block, _) -> block.getInventory());
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.REPROCESSOR.get(), ReprocessorTileEntity::getSidedInventory);
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.SOUL_EXTRACTOR.get(), SoulExtractorTileEntity::getSidedInventory);
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.SOULIUM_SPAWNER.get(), SouliumSpawnerTileEntity::getSidedInventory);
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.ORE_INFUSER.get(), OreInfuserTileEntity::getSidedInventory);

        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModTileEntities.FURNACE.get(), (block, _) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModTileEntities.HARVESTER.get(), (block, _) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModTileEntities.REPROCESSOR.get(), (block, _) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModTileEntities.SOUL_EXTRACTOR.get(), (block, _) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModTileEntities.SOULIUM_SPAWNER.get(), (block, _) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModTileEntities.ORE_INFUSER.get(), (block, _) -> block.getEnergy());
    }
}
