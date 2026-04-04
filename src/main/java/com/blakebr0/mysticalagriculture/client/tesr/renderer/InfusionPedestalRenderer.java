package com.blakebr0.mysticalagriculture.client.tesr.renderer;

import com.blakebr0.mysticalagriculture.client.tesr.state.InfusionPedestalRenderState;
import com.blakebr0.mysticalagriculture.tileentity.InfusionPedestalTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;

public class InfusionPedestalRenderer implements BlockEntityRenderer<InfusionPedestalTileEntity, InfusionPedestalRenderState> {
    public InfusionPedestalRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public InfusionPedestalRenderState createRenderState() {
        return new InfusionPedestalRenderState();
    }

    @Override
    public void submit(InfusionPedestalRenderState state, PoseStack matrix, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        var stack = tile.getInventory().getStackInSlot(0);
        var minecraft = Minecraft.getInstance();

        if (!stack.isEmpty()) {
            matrix.pushPose();
            matrix.translate(0.5D, 1.2D, 0.5D);
            float scale = stack.getItem() instanceof BlockItem ? 0.95F : 0.75F;
            matrix.scale(scale, scale, scale);
            double tick = System.currentTimeMillis() / 800.0D;
            matrix.translate(0.0D, Math.sin(tick % (2 * Math.PI)) * 0.065D, 0.0D);
            matrix.mulPose(Axis.YP.rotationDegrees((float) ((tick * 40.0D) % 360)));
            minecraft.getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, i, i1, matrix, buffer, minecraft.level, 0);
            matrix.popPose();
        }
    }
}

