package com.blakebr0.mysticalagriculture.client.tesr.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class InfusionPedestalRenderState extends BlockEntityRenderState {
    public ItemResource itemResource;
    public ItemStackRenderState itemRenderState = new ItemStackRenderState();
}
