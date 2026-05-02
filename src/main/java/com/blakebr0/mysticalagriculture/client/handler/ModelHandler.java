package com.blakebr0.mysticalagriculture.client.handler;

import com.blakebr0.cucumber.event.RegisterClientItemsEvent;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.api.crop.CropModels;
import com.blakebr0.mysticalagriculture.api.crop.CropType;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import com.google.common.base.Stopwatch;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class ModelHandler {
    private static final Map<CropType, List<StandaloneModelKey<BlockStateModel>>> stemModelKeys = new HashMap<>();
    private static final Map<Identifier, StandaloneModelKey<BlockStateModel>> flowerModelKeys = new HashMap<>();

    @SubscribeEvent
    public void onRegisterAdditionalModels(ModelEvent.RegisterStandalone event) {
        stemModelKeys.put(CropType.RESOURCE, new ArrayList<>());
        stemModelKeys.put(CropType.MOB, new ArrayList<>());

        flowerModelKeys.clear();

        for (int i = 0; i < 8; i++) {
            {
                var id = MysticalAgriculture.resource("block/mystical_resource_crop_" + i);
                var key = new StandaloneModelKey<BlockStateModel>(new Key(id));

                stemModelKeys.get(CropType.RESOURCE).add(key);
                event.register(key, SimpleUnbakedStandaloneModel.blockStateModel(id));
            }

            {
                var id = MysticalAgriculture.resource("block/mystical_mob_crop_" + i);
                var key = new StandaloneModelKey<BlockStateModel>(new Key(id));

                stemModelKeys.get(CropType.MOB).add(key);
                event.register(key, SimpleUnbakedStandaloneModel.blockStateModel(id));
            }
        }

        for (var type : CropRegistry.getInstance().getTypes()) {
            addFlowerModel(event, type, CropModels.FLOWER_INGOT_BLANK);
            addFlowerModel(event, type, CropModels.FLOWER_ROCK_BLANK);
            addFlowerModel(event, type, CropModels.FLOWER_DUST_BLANK);
            addFlowerModel(event, type, CropModels.FLOWER_FACE_BLANK);
        }
    }

    @SubscribeEvent
    public void onModifyBakingResults(ModelEvent.ModifyBakingResult event) {
        var stopwatch = Stopwatch.createStarted();
        var registry = event.getBakingResult();

        for (var crop : CropRegistry.getInstance().getCrops()) {
            var textures = crop.getModels();

            // crop
            {
                for (int i = 0; i < 7; i++) {
                    var blockState = crop.getCropBlock().getStateForAge(i);
                    var bakedModel = registry.getBlockStateModel(blockState);

                    if (bakedModel == registry.missingModels().block()) {
                        var stemModels = stemModelKeys.get(crop.getType());
                        if (stemModels != null) {
                            var model = registry.standaloneModels().get(stemModels.get(i));
                            if (model != null) {
                                registry.blockStateModels().put(blockState, model);
                            }
                        }
                    }
                }

                var blockState = crop.getCropBlock().getStateForAge(7);
                var bakedModel = registry.getBlockStateModel(blockState);

                if (bakedModel == registry.missingModels().block()) {
                    var flower = textures.getFlowerModel();
                    var key = flowerModelKeys.get(flower.withSuffix("_" + crop.getType().getName()));
                    var model = registry.standaloneModels().get(key);

                    if (model != null) {
                        registry.blockStateModels().put(blockState, model);
                    }
                }
            }
        }

        MysticalAgriculture.LOGGER.info("Model replacement took {} ms", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    public static void onRegisterClientItems(RegisterClientItemsEvent event) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            var models = crop.getModels();

            var essence = crop.getEssenceItem();
            var essenceId = BuiltInRegistries.ITEM.getKey(essence);

            if (!event.hasClientItem(essenceId)) {
                var texture = models.getEssenceModel();
                List<ItemTintSource> tints = crop.isEssenceColored() ? List.of(new Constant(crop.getEssenceColor())) : List.of();

                event.register(essenceId, new ClientItem(new CuboidItemModelWrapper.Unbaked(texture, Optional.empty(), tints), ClientItem.Properties.DEFAULT));
            }

            var seeds = crop.getSeedsItem();
            var seedsId = BuiltInRegistries.ITEM.getKey(seeds);

            if (!event.hasClientItem(seedsId)) {
                var texture = models.getSeedModel();
                List<ItemTintSource> tints = crop.isSeedColored() ? List.of(new Constant(crop.getSeedColor())) : List.of();

                event.register(seedsId, new ClientItem(new CuboidItemModelWrapper.Unbaked(texture, Optional.empty(), tints), ClientItem.Properties.DEFAULT));
            }
        }
    }

    private static void addFlowerModel(ModelEvent.RegisterStandalone event, CropType type, Identifier id) {
        id = id.withSuffix("_" + type.getName());
        var key = new StandaloneModelKey<BlockStateModel>(new Key(id));

        flowerModelKeys.put(id, key);
        event.register(key, SimpleUnbakedStandaloneModel.blockStateModel(id));
    }

    private record Key(Identifier id) implements ModelDebugName {
        @Override
        public String debugName() {
            return this.id.toString();
        }
    }
}
