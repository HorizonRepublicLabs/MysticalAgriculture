package com.blakebr0.mysticalagriculture.compat;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.api.crop.ICropProvider;
import com.blakebr0.mysticalagriculture.api.farmland.IEssenceFarmland;
import com.blakebr0.mysticalagriculture.block.InferiumCropBlock;
import com.blakebr0.mysticalagriculture.block.InfusedFarmlandBlock;
import com.blakebr0.mysticalagriculture.block.MysticalCropBlock;
import com.blakebr0.mysticalagriculture.config.ModConfigs;
import com.blakebr0.mysticalagriculture.lib.ModCrops;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {
    private static final Identifier CROP_PROVIDER = MysticalAgriculture.resource("crop");
    private static final Identifier INFERIUM_CROP_PROVIDER = MysticalAgriculture.resource("inferium_crop");
    private static final Identifier INFUSED_FARMLAND_PROVIDER = MysticalAgriculture.resource("infused_farmland");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                var block = accessor.getBlock();
                var crop = ((ICropProvider) block).getCrop();

                tooltip.add(ModTooltips.TIER.args(crop.getTier().getDisplayName()).toComponent());

                var pos = accessor.getPosition();
                var downPos = pos.below();
                var level = accessor.getLevel();
                var belowBlock = level.getBlockState(downPos).getBlock();

                if (ModConfigs.REQUIRES_EFFECTIVE_FARMLAND.get() && crop != ModCrops.INFERIUM) {
                    var farmland = crop.getTier().getFarmlandBlock();
                    if (farmland != null) {
                        tooltip.add(ModTooltips.REQUIRES_EFFECTIVE_FARMLAND.args(farmland.getName().withStyle(crop.getTier().getTextColor())).toComponent());
                    }
                }

                if (ModConfigs.SECONDARY_SEED_DROPS.get()) {
                    var secondaryChance = crop.getSecondaryChance(belowBlock);
                    if (secondaryChance > 0) {
                        var chanceText = Component.literal(String.valueOf((int) (secondaryChance * 100)))
                                .append("%")
                                .withStyle(crop.getTier().getTextColor());

                        tooltip.add(ModTooltips.SECONDARY_CHANCE.args(chanceText).toComponent());
                    }
                }

                var crux = crop.getCruxBlock();
                if (crux != null) {
                    var stack = new ItemStack(crux);
                    tooltip.add(ModTooltips.REQUIRES_CRUX.args(stack.getHoverName()).toComponent());
                }

                var biomes = crop.getRequiredBiomes();
                if (!biomes.isEmpty()) {
                    var biome = level.getBiome(pos).getKey();
                    if (biome != null && !biomes.contains(biome.identifier())) {
                        tooltip.add(ModTooltips.INVALID_BIOME.color(ChatFormatting.RED).toComponent());
                    }
                }
            }

            @Override
            public Identifier getUid() {
                return CROP_PROVIDER;
            }
        }, MysticalCropBlock.class);

        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                var block = accessor.getBlock();
                var crop = ((ICropProvider) block).getCrop();
                var downPos = accessor.getPosition().below();
                var belowBlock = accessor.getLevel().getBlockState(downPos).getBlock();

                int output = 100;
                if (belowBlock instanceof IEssenceFarmland farmland) {
                    int tier = farmland.getTier().getValue();
                    output = (tier * 50) + 50;
                }

                var inferiumOutputText = Component.literal(String.valueOf(output)).append("%").withStyle(crop.getTier().getTextColor());

                tooltip.add(ModTooltips.INFERIUM_OUTPUT.args(inferiumOutputText).toComponent());
            }

            @Override
            public Identifier getUid() {
                return INFERIUM_CROP_PROVIDER;
            }
        }, InferiumCropBlock.class);

        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                var block = accessor.getBlock();
                var farmland = (IEssenceFarmland) block;

                tooltip.add(ModTooltips.TIER.args(farmland.getTier().getDisplayName()).toComponent());
            }

            @Override
            public Identifier getUid() {
                return INFUSED_FARMLAND_PROVIDER;
            }
        }, InfusedFarmlandBlock.class);
    }
}
