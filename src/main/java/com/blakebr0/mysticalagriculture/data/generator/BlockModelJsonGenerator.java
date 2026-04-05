package com.blakebr0.mysticalagriculture.data.generator;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.CropBlock;

import java.util.HashMap;

public class BlockModelJsonGenerator extends ModelProvider {
    public BlockModelJsonGenerator(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        var stemModels = new HashMap<Identifier, ModelFile[]>();
//
//        for (var type : CropRegistry.getInstance().getTypes()) {
//            var models = new ModelFile[8];
//            var stemModel = type.getStemModel();
//
//            for (int i = 0; i <= 7; i++) {
//                models[i] = new ModelFile.UncheckedModelFile(Identifier.fromNamespaceAndPath(stemModel.getNamespace(), stemModel.getPath() + "_" + i));
//            }
//
//            stemModels.put(type.getId(), models);
//        }
//
//        for (var crop : CropRegistry.getInstance().getCrops()) {
//            var block = crop.getCropBlock();
//            var models = stemModels.get(crop.getType().getId());
//
//            if (crop.shouldRegisterCropBlock()) {
//                this.getVariantBuilder(block).forAllStates(state -> {
//                    var age = state.getValue(CropBlock.AGE);
//                    if (age == block.getMaxAge()) {
//                        var model = this.models().getBuilder(crop.getNameWithSuffix("crop"))
//                                .parent(models[7])
//                                .texture("flower", crop.getTextures().getFlowerTexture());
//
//                        return ConfiguredModel.builder().modelFile(model).build();
//                    }
//
//                    return ConfiguredModel.builder().modelFile(models[age]).build();
//                });
//            }
//        }
    }

    @Override
    public String getName() {
        return MysticalAgriculture.NAME + " block model generator";
    }
}
