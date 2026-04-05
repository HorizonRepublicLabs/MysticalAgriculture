package com.blakebr0.mysticalagriculture.client.tesr.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class EnchanterRenderState extends BlockEntityRenderState {
    public Direction facing;
    public ItemResource itemResource;
    public ItemStackRenderState itemRenderState = new ItemStackRenderState();
}
