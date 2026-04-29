//package com.blakebr0.mysticalagriculture.compat;
//
//import mcjty.theoneprobe.api.ITheOneProbe;
//import net.neoforged.fml.InterModComms;
//
//import java.util.function.Function;
//
//public class TOPCompat implements Function<ITheOneProbe, Void> {
//    @Override
//    public Void apply(ITheOneProbe probe) {
//        probe.registerProvider(new IProbeInfoProvider() {
//            @Override
//            public Identifier getID() {
//                return MysticalAgriculture.resource("crops");
//            }
//
//            @Override
//            public void addProbeInfo(ProbeMode mode, IProbeInfo info, Player player, Level level, BlockState state, IProbeHitData data) {
//                var block = state.getBlock();
//                var pos = data.getPos();
//
//                if (block instanceof ICropProvider provider) {
//                    var crop = provider.getCrop();
//                    var belowBlock = level.getBlockState(pos.below()).getBlock();
//
//                    info.text(ModTooltips.TIER.args(crop.getTier().getDisplayName()).toComponent());
//
//                    if (ModConfigs.REQUIRES_EFFECTIVE_FARMLAND.get() && crop != ModCrops.INFERIUM) {
//                        var farmland = crop.getTier().getFarmland();
//                        if (farmland != null) {
//                            info.text(ModTooltips.REQUIRES_EFFECTIVE_FARMLAND.args(farmland.getName()).build());
//                        }
//                    }
//
//                    if (ModConfigs.SECONDARY_SEED_DROPS.get()) {
//                        double secondaryChance = crop.getSecondaryChance(belowBlock);
//                        if (secondaryChance > 0) {
//                            var chanceText = Component.literal(String.valueOf((int) (secondaryChance * 100)))
//                                    .append("%")
//                                    .withStyle(crop.getTier().getTextColor());
//
//                            info.text(ModTooltips.SECONDARY_CHANCE.args(chanceText).build());
//                        }
//                    }
//
//                    var crux = crop.getCruxBlock();
//                    if (crux != null) {
//                        var stack = new ItemStack(crux);
//
//                        info.text(ModTooltips.REQUIRES_CRUX.args(stack.getHoverName()).build());
//                    }
//
//                    var biomes = crop.getRequiredBiomes();
//                    if (!biomes.isEmpty()) {
//                        var biome = level.getBiome(pos).getKey();
//
//                        if (biome != null && !biomes.contains(biome.location())) {
//                            info.text(ModTooltips.INVALID_BIOME.color(ChatFormatting.RED).build());
//                        }
//                    }
//
//                    if (block instanceof InferiumCropBlock) {
//                        int output = 100;
//                        if (belowBlock instanceof IEssenceFarmland farmland) {
//                            int tier = farmland.getTier().getValue();
//                            output = (tier * 50) + 50;
//                        }
//
//                        var inferiumOutputText = Component.literal(String.valueOf(output))
//                                .append("%")
//                                .withStyle(crop.getTier().getTextColor());
//
//                        info.text(ModTooltips.INFERIUM_OUTPUT.args(inferiumOutputText).build());
//                    }
//                }
//
//                if (block instanceof IEssenceFarmland farmland) {
//                    info.text(ModTooltips.TIER.args(farmland.getTier().getDisplayName()).build());
//                }
//
//                var tile = level.getBlockEntity(pos);
//
//                if (tile instanceof EssenceVesselTileEntity vessel) {
//                    var stack = vessel.getInventory().getStackInSlot(0);
//
//                    if (!stack.isEmpty()) {
//                        info.text(String.format("%sx %s", stack.getCount(), stack.getHoverName().getString()));
//                    }
//                }
//            }
//        });
//
//        return null;
//    }
//
//    public static void onInterModEnqueue() {
//        InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPCompat::new);
//    }
//}
