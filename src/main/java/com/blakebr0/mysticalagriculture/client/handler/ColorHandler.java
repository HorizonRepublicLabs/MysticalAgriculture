package com.blakebr0.mysticalagriculture.client.handler;

import com.blakebr0.cucumber.iface.IColored;
import com.blakebr0.mysticalagriculture.block.InfusedFarmlandBlock;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

public final class ColorHandler {
    @SubscribeEvent
    public void onBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(new IColored.BlockColors()), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));

        for (var crop : CropRegistry.getInstance().getCrops()) {
            if (crop.isFlowerColored() && crop.getCropBlock() != null)
                event.register(List.of(_ -> crop.getFlowerColor()), crop.getCropBlock());
        }
    }

//    TODO item colors
//    @SubscribeEvent
//    public void onItemColors(RegisterColorHandlersEvent.Item event) {
//        event.register(new IColored.ItemBlockColors(), InfusedFarmlandBlock.FARMLANDS.toArray(new InfusedFarmlandBlock[0]));
//        event.register((stack, tint) -> {
//            float damage = (float) (stack.getMaxDamage() - stack.getDamageValue()) / stack.getMaxDamage();
//            return ColorHelper.saturate(0x00D9D9, damage);
//        }, ModItems.INFUSION_CRYSTAL.get());
//
//        event.register((stack, tint) -> {
//            var type = MobSoulUtils.getType(stack);
//            return tint == 1 && type != null ? type.getColor() : -1;
//        }, ModItems.SOUL_JAR.get());
//
//        for (var crop : CropRegistry.getInstance().getCrops()) {
//            if (crop.isEssenceColored() && crop.getEssenceItem() != null)
//                event.register((stack, tint) -> crop.getEssenceColor(), crop.getEssenceItem());
//            if (crop.isSeedColored() && crop.getSeedsItem() != null)
//                event.register((stack, tint) -> crop.getSeedColor(), crop.getSeedsItem());
//        }
//
//        for (var augment : AugmentRegistry.getInstance().getAugments()) {
//            if (augment.getItem() != null)
//                event.register((stack, tint) -> tint == 0 ? augment.getSecondaryColor() : tint == 1 ? augment.getPrimaryColor() : -1, augment.getItem());
//        }
//    }
}
