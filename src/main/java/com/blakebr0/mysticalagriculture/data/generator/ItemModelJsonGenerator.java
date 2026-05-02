package com.blakebr0.mysticalagriculture.data.generator;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.client.tints.AugmentTintSource;
import com.blakebr0.mysticalagriculture.registry.AugmentRegistry;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class ItemModelJsonGenerator extends ModelProvider {
    public ItemModelJsonGenerator(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.shouldRegisterEssenceItem()) {
                var item = crop.getEssenceItem();

                itemModels.itemModelOutput.register(
                        item,
                        new ClientItem(
                                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item)),
                                ClientItem.Properties.DEFAULT
                        )
                );

                ModelTemplates.FLAT_ITEM.create(
                        item,
                        TextureMapping.singleSlot(TextureSlot.LAYER0, new Material(crop.getModels().getEssenceModel())),
                        itemModels.modelOutput
                );
            }

            if (crop.shouldRegisterSeedsItem()) {
                var item = crop.getSeedsItem();

                itemModels.itemModelOutput.register(
                        item,
                        new ClientItem(
                                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item)),
                                ClientItem.Properties.DEFAULT
                        )
                );

                ModelTemplates.FLAT_ITEM.create(
                        item,
                        TextureMapping.singleSlot(TextureSlot.LAYER0, new Material(crop.getModels().getSeedModel())),
                        itemModels.modelOutput
                );
            }
        }

        {
            var template = ModelTemplates.createItem(MysticalAgriculture.resource("augment").toString());

            for (var augment : AugmentRegistry.getInstance().getAugments()) {
                var item = augment.getItem();
                var location = ModelLocationUtils.getModelLocation(item);

                itemModels.itemModelOutput.register(
                        item,
                        new ClientItem(
                                ItemModelUtils.tintedModel(location, new AugmentTintSource(augment.getId(), 0), new AugmentTintSource(augment.getId(), 1)),
                                ClientItem.Properties.DEFAULT
                        )
                );

                itemModels.modelOutput.accept(location, () -> template.createBaseTemplate(
                        MysticalAgriculture.resource(augment.getNameWithSuffix("augment")),
                        Map.of(
                                TextureSlot.LAYER1, new Material(MysticalAgriculture.resource("item/augment_%s".formatted(augment.getTier())))
                        )
                ));
            }
        }
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.empty();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.empty();
    }

    @Override
    public String getName() {
        return MysticalAgriculture.NAME + " item model generator";
    }
}
