package com.blakebr0.mysticalagriculture.data.generator;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class ItemModelJsonGenerator extends ModelProvider {
    public ItemModelJsonGenerator(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        var generatedModel = new ModelFile.UncheckedModelFile("item/generated");
//
//        for (var crop : CropRegistry.getInstance().getCrops()) {
//            if (crop.shouldRegisterEssenceItem()) {
//                this.getBuilder(crop.getNameWithSuffix("essence"))
//                        .parent(generatedModel)
//                        .texture("layer0", crop.getTextures().getEssenceTexture());
//            }
//
//            if (crop.shouldRegisterSeedsItem()) {
//                this.getBuilder(crop.getNameWithSuffix("seeds"))
//                        .parent(generatedModel)
//                        .texture("layer0", crop.getTextures().getSeedTexture());
//            }
//        }
//
//        var augmentModel = new ModelFile.UncheckedModelFile(MysticalAgriculture.resource("item/augment"));
//
//        for (var augment : AugmentRegistry.getInstance().getAugments()) {
//            this.getBuilder(augment.getNameWithSuffix("augment"))
//                    .parent(augmentModel);
//        }
    }

    @Override
    public String getName() {
        return MysticalAgriculture.NAME + " item model generator";
    }
}
