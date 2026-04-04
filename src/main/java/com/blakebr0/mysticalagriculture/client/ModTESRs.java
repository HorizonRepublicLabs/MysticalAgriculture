package com.blakebr0.mysticalagriculture.client;

import com.blakebr0.mysticalagriculture.client.tesr.renderer.AwakeningAltarRenderer;
import com.blakebr0.mysticalagriculture.client.tesr.renderer.AwakeningPedestalRenderer;
import com.blakebr0.mysticalagriculture.client.tesr.renderer.EnchanterRenderer;
import com.blakebr0.mysticalagriculture.client.tesr.renderer.EssenceVesselRenderer;
import com.blakebr0.mysticalagriculture.client.tesr.renderer.InfusionAltarRenderer;
import com.blakebr0.mysticalagriculture.client.tesr.renderer.InfusionPedestalRenderer;
import com.blakebr0.mysticalagriculture.client.tesr.renderer.SouliumSpawnerRenderer;
import com.blakebr0.mysticalagriculture.client.tesr.renderer.TinkeringTableRenderer;
import com.blakebr0.mysticalagriculture.init.ModTileEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public final class ModTESRs {
    @SubscribeEvent
    public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTileEntities.INFUSION_PEDESTAL.get(), InfusionPedestalRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.INFUSION_ALTAR.get(), InfusionAltarRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.TINKERING_TABLE.get(), TinkeringTableRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.ENCHANTER.get(), EnchanterRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.AWAKENING_PEDESTAL.get(), AwakeningPedestalRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.AWAKENING_ALTAR.get(), AwakeningAltarRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.ESSENCE_VESSEL.get(), EssenceVesselRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.SOULIUM_SPAWNER.get(), SouliumSpawnerRenderer::new);
    }
}
