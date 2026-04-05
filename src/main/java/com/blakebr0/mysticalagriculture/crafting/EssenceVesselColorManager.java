package com.blakebr0.mysticalagriculture.crafting;

import com.blakebr0.cucumber.helper.ParsingHelper;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.network.payloads.SyncEssenceVesselColorsPayload;
import com.google.common.base.Stopwatch;
import com.google.gson.JsonParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoader;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class EssenceVesselColorManager implements PreparableReloadListener {
    public static final EssenceVesselColorManager INSTANCE = new EssenceVesselColorManager();

    private final Map<String, Integer> colors = new HashMap<>();

    @SubscribeEvent
    public void onAddReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(MysticalAgriculture.resource("essence_vessel_color_manager"), this);
    }

    @SubscribeEvent
    public void onDataPackSync(OnDatapackSyncEvent event) {
        var payload = new SyncEssenceVesselColorsPayload(this.colors);
        var player = event.getPlayer();

        // send the new caches to the client
        if (player != null) {
            PacketDistributor.sendToPlayer(player, payload);
        } else {
            PacketDistributor.sendToAllPlayers(payload);
        }
    }

    @Override
    public CompletableFuture<Void> reload(SharedState currentReload, Executor taskExecutor, PreparationBarrier barrier, Executor reloadExecutor) {
        return CompletableFuture.runAsync(() -> {
            if (!ModLoader.hasErrors()) {
                this.load(currentReload.resourceManager());
            }
        }, reloadExecutor).thenCompose(barrier::wait);
    }

    public int getColor(ItemStack stack) {
        var id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return this.colors.getOrDefault(id.toString(), 0xFFFFFF);
    }

    public int getColor(ItemResource resource) {
        return getColor(resource.toStack());
    }

    public void addColor(ItemStack stack, int color) {
        var id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        this.colors.put(id.toString(), color);
    }

    public void setColors(Map<String, Integer> colors) {
        this.colors.clear();
        this.colors.putAll(colors);
    }

    private void load(ResourceManager manager) {
        var stopwatch = Stopwatch.createStarted();
        var resources = manager.listResources("mysticalagriculture/essence_vessel_colors.json", s -> s.getPath().endsWith(".json"));

        this.colors.clear();

        for (var resource : resources.entrySet()) {
            try (var reader = resource.getValue().openAsReader()) {
                var json = JsonParser.parseReader(reader).getAsJsonObject();

                for (var entry : json.entrySet()) {
                    var item = entry.getKey();
                    var color = ParsingHelper.parseHex(entry.getValue().getAsString(), item);

                    this.colors.put(item, color);
                }
            } catch (IOException e) {
                MysticalAgriculture.LOGGER.error("Failed to load {}", resource.getKey(), e);
            }
        }

        MysticalAgriculture.LOGGER.info("Loaded {} essence vessel colors in {} ms", this.colors.size(), stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }
}
