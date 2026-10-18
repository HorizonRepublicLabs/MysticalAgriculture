package com.blakebr0.mysticalagriculture.network.payloads;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.crafting.EssenceVesselColorManager;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

public record SyncEssenceVesselColorsPayload(Map<String, Integer> colors) implements CustomPacketPayload {
    public static final Type<SyncEssenceVesselColorsPayload> TYPE = new Type<>(MysticalAgriculture.resource("sync_essence_vessel_colors"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncEssenceVesselColorsPayload> STREAM_CODEC = StreamCodec.of(
            SyncEssenceVesselColorsPayload::toNetwork, SyncEssenceVesselColorsPayload::fromNetwork
    );

    // 26.3's FriendlyByteBuf readUtf/writeUtf no longer fit readMap/writeMap's functional types.
    private static final StreamCodec<RegistryFriendlyByteBuf, Map<String, Integer>> COLORS_CODEC =
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.VAR_INT);

    @Override
    public Type<SyncEssenceVesselColorsPayload> type() {
        return TYPE;
    }

    private static SyncEssenceVesselColorsPayload fromNetwork(RegistryFriendlyByteBuf buffer) {
        return new SyncEssenceVesselColorsPayload(COLORS_CODEC.decode(buffer));
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, SyncEssenceVesselColorsPayload payload) {
        COLORS_CODEC.encode(buffer, payload.colors);
    }

    public static void handleClient(SyncEssenceVesselColorsPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            EssenceVesselColorManager.INSTANCE.setColors(payload.colors);
        });
    }
}
