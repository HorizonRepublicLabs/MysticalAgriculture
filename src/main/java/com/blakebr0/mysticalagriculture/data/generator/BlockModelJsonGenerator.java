package com.blakebr0.mysticalagriculture.data.generator;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;

import java.util.HashMap;
import java.util.stream.Stream;

public class BlockModelJsonGenerator extends ModelProvider {
    public BlockModelJsonGenerator(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        var stemModels = new HashMap<Identifier, Variant[]>();
        var fullyGrownModels = new HashMap<Identifier, ModelTemplate>();

        for (var type : CropRegistry.getInstance().getTypes()) {
            var models = new Variant[7];
            var stemModel = type.getStemModel();

            for (int i = 0; i < 7; i++) {
                models[i] = new Variant(stemModel.withSuffix("_" + i));
            }

            stemModels.put(type.getId(), models);
            fullyGrownModels.put(type.getId(), ModelTemplates.create(stemModel.withSuffix("_7").toString().replace("block/", "")));
        }

        for (var crop : CropRegistry.getInstance().getCrops()) {
            var block = crop.getCropBlock();
            var models = stemModels.get(crop.getType().getId());

            if (crop.shouldRegisterCropBlock()) {
                var fullyGrownModel = fullyGrownModels.get(crop.getType().getId())
                        .create(
                                crop.getCropBlock(),
                                new TextureMapping().putForced(TextureSlot.create("flower"), new Material(crop.getModels().getFlowerModel())),
                                blockModels.modelOutput
                        );

                blockModels.blockStateOutput.accept(
                        MultiVariantGenerator.dispatch(block).with(
                                PropertyDispatch.initial(CropBlock.AGE).generate(stage -> {
                                    if (stage == block.getMaxAge()) {
                                        return BlockModelGenerators.plainVariant(fullyGrownModel);
                                    }

                                    return BlockModelGenerators.variant(models[stage]);
                                })
                        )
                );
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
        return MysticalAgriculture.NAME + " block model generator";
    }
}
