package com.blakebr0.mysticalagriculture.data.generator;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.registry.AugmentRegistry;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
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
                ModelTemplates.FLAT_ITEM.create(
                        crop.getEssenceItem(),
                        TextureMapping.singleSlot(TextureSlot.LAYER0, new Material(crop.getTextures().getEssenceTexture())),
                        itemModels.modelOutput
                );
            }

            if (crop.shouldRegisterSeedsItem()) {
                ModelTemplates.FLAT_ITEM.create(
                        crop.getSeedsItem(),
                        TextureMapping.singleSlot(TextureSlot.LAYER0, new Material(crop.getTextures().getSeedTexture())),
                        itemModels.modelOutput
                );
            }
        }

        {
            var template = ModelTemplates.createItem(MysticalAgriculture.resource("augment").toString());

            for (var augment : AugmentRegistry.getInstance().getAugments()) {
                itemModels.modelOutput.accept(ModelLocationUtils.getModelLocation(augment.getItem()), () -> template.createBaseTemplate(
                        MysticalAgriculture.resource(augment.getNameWithSuffix("augment")),
                        Map.of()
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
