package com.blakebr0.mysticalagriculture.init;

import com.blakebr0.cucumber.item.BaseItem;
import com.blakebr0.cucumber.item.tool.BaseScytheItem;
import com.blakebr0.cucumber.item.tool.BaseSickleItem;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.api.crop.CropTier;
import com.blakebr0.mysticalagriculture.api.machine.MachineUpgradeTier;
import com.blakebr0.mysticalagriculture.item.EssenceItem;
import com.blakebr0.mysticalagriculture.item.EssenceWateringCanItem;
import com.blakebr0.mysticalagriculture.item.ExperienceCapsuleItem;
import com.blakebr0.mysticalagriculture.item.ExperienceDropletItem;
import com.blakebr0.mysticalagriculture.item.FertilizedEssenceItem;
import com.blakebr0.mysticalagriculture.item.InfusionCrystalItem;
import com.blakebr0.mysticalagriculture.item.MachineUpgradeItem;
import com.blakebr0.mysticalagriculture.item.MasterInfusionCrystalItem;
import com.blakebr0.mysticalagriculture.item.MysticalFertilizerItem;
import com.blakebr0.mysticalagriculture.item.SoulJarItem;
import com.blakebr0.mysticalagriculture.item.SouliumDaggerItem;
import com.blakebr0.mysticalagriculture.item.WandItem;
import com.blakebr0.mysticalagriculture.item.WateringCanItem;
import com.blakebr0.mysticalagriculture.item.armor.EssenceBootsItem;
import com.blakebr0.mysticalagriculture.item.armor.EssenceChestplateItem;
import com.blakebr0.mysticalagriculture.item.armor.EssenceHelmetItem;
import com.blakebr0.mysticalagriculture.item.armor.EssenceLeggingsItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceAxeItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceBowItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceCrossbowItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceFishingRodItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceHoeItem;
import com.blakebr0.mysticalagriculture.item.tool.EssencePickaxeItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceScytheItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceShearsItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceShovelItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceSickleItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceSpearItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceStaffItem;
import com.blakebr0.mysticalagriculture.item.tool.EssenceSwordItem;
import com.blakebr0.mysticalagriculture.lib.ModArmorMaterials;
import com.blakebr0.mysticalagriculture.lib.ModToolMaterials;
import com.blakebr0.mysticalagriculture.registry.AugmentRegistry;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public final class ModItems {
    public static final Map<Identifier, Function<Identifier, BlockItem>> BLOCK_ENTRIES = new LinkedHashMap<>();
    public static final Map<DeferredHolder<Item, Item>, Function<Identifier, Item>> ENTRIES = new LinkedHashMap<>();
    public static final Map<DeferredHolder<Item, Item>, Function<Identifier, Item>> GEAR_ENTRIES = new LinkedHashMap<>();

    public static final DeferredHolder<Item, Item> PROSPERITY_SHARD = register("prosperity_shard");
    public static final DeferredHolder<Item, Item> INFERIUM_ESSENCE = register("inferium_essence", id -> new EssenceItem(id, CropTier.ONE));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_ESSENCE = register("prudentium_essence", id -> new EssenceItem(id, CropTier.TWO));
    public static final DeferredHolder<Item, Item> TERTIUM_ESSENCE = register("tertium_essence", id -> new EssenceItem(id, CropTier.THREE));
    public static final DeferredHolder<Item, Item> IMPERIUM_ESSENCE = register("imperium_essence", id -> new EssenceItem(id, CropTier.FOUR));
    public static final DeferredHolder<Item, Item> SUPREMIUM_ESSENCE = register("supremium_essence", id -> new EssenceItem(id, CropTier.FIVE));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_ESSENCE = register("awakened_supremium_essence");
    public static final DeferredHolder<Item, Item> PROSPERITY_INGOT = register("prosperity_ingot");
    public static final DeferredHolder<Item, Item> INFERIUM_INGOT = register("inferium_ingot");
    public static final DeferredHolder<Item, Item> PRUDENTIUM_INGOT = register("prudentium_ingot");
    public static final DeferredHolder<Item, Item> TERTIUM_INGOT = register("tertium_ingot");
    public static final DeferredHolder<Item, Item> IMPERIUM_INGOT = register("imperium_ingot");
    public static final DeferredHolder<Item, Item> SUPREMIUM_INGOT = register("supremium_ingot");
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_INGOT = register("awakened_supremium_ingot");
    public static final DeferredHolder<Item, Item> SOULIUM_INGOT = register("soulium_ingot");
    public static final DeferredHolder<Item, Item> PROSPERITY_NUGGET = register("prosperity_nugget");
    public static final DeferredHolder<Item, Item> INFERIUM_NUGGET = register("inferium_nugget");
    public static final DeferredHolder<Item, Item> PRUDENTIUM_NUGGET = register("prudentium_nugget");
    public static final DeferredHolder<Item, Item> TERTIUM_NUGGET = register("tertium_nugget");
    public static final DeferredHolder<Item, Item> IMPERIUM_NUGGET = register("imperium_nugget");
    public static final DeferredHolder<Item, Item> SUPREMIUM_NUGGET = register("supremium_nugget");
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_NUGGET = register("awakened_supremium_nugget");
    public static final DeferredHolder<Item, Item> SOULIUM_NUGGET = register("soulium_nugget");
    public static final DeferredHolder<Item, Item> PROSPERITY_GEMSTONE = register("prosperity_gemstone");
    public static final DeferredHolder<Item, Item> INFERIUM_GEMSTONE = register("inferium_gemstone");
    public static final DeferredHolder<Item, Item> PRUDENTIUM_GEMSTONE = register("prudentium_gemstone");
    public static final DeferredHolder<Item, Item> TERTIUM_GEMSTONE = register("tertium_gemstone");
    public static final DeferredHolder<Item, Item> IMPERIUM_GEMSTONE = register("imperium_gemstone");
    public static final DeferredHolder<Item, Item> SUPREMIUM_GEMSTONE = register("supremium_gemstone");
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_GEMSTONE = register("awakened_supremium_gemstone");
    public static final DeferredHolder<Item, Item> SOULIUM_GEMSTONE = register("soulium_gemstone");
    public static final DeferredHolder<Item, Item> PROSPERITY_SEED_BASE = register("prosperity_seed_base");
    public static final DeferredHolder<Item, Item> SOULIUM_SEED_BASE = register("soulium_seed_base");
    public static final DeferredHolder<Item, Item> SOUL_DUST = register("soul_dust");
    public static final DeferredHolder<Item, Item> SOULIUM_DUST = register("soulium_dust");
    public static final DeferredHolder<Item, Item> COGNIZANT_DUST = register("cognizant_dust");
    public static final DeferredHolder<Item, Item> SOULIUM_DAGGER = register("soulium_dagger", id -> new SouliumDaggerItem(id, ModToolMaterials.SOULIUM, SouliumDaggerItem.DaggerType.BASIC));
    public static final DeferredHolder<Item, Item> PASSIVE_SOULIUM_DAGGER = register("passive_soulium_dagger", id -> new SouliumDaggerItem(id, ModToolMaterials.SOULIUM, SouliumDaggerItem.DaggerType.PASSIVE));
    public static final DeferredHolder<Item, Item> HOSTILE_SOULIUM_DAGGER = register("hostile_soulium_dagger", id -> new SouliumDaggerItem(id, ModToolMaterials.SOULIUM, SouliumDaggerItem.DaggerType.HOSTILE));
    public static final DeferredHolder<Item, Item> CREATIVE_SOULIUM_DAGGER = register("creative_soulium_dagger", id -> new SouliumDaggerItem(id, ModToolMaterials.SOULIUM, SouliumDaggerItem.DaggerType.CREATIVE));
    public static final DeferredHolder<Item, Item> INFUSION_CRYSTAL = register("infusion_crystal", InfusionCrystalItem::new);
    public static final DeferredHolder<Item, Item> MASTER_INFUSION_CRYSTAL = register("master_infusion_crystal", MasterInfusionCrystalItem::new);
    public static final DeferredHolder<Item, Item> FERTILIZED_ESSENCE = register("fertilized_essence", FertilizedEssenceItem::new);
    public static final DeferredHolder<Item, Item> MYSTICAL_FERTILIZER = register("mystical_fertilizer", MysticalFertilizerItem::new);
    public static final DeferredHolder<Item, Item> AIR_AGGLOMERATIO = register("air_agglomeratio");
    public static final DeferredHolder<Item, Item> EARTH_AGGLOMERATIO = register("earth_agglomeratio");
    public static final DeferredHolder<Item, Item> WATER_AGGLOMERATIO = register("water_agglomeratio");
    public static final DeferredHolder<Item, Item> FIRE_AGGLOMERATIO = register("fire_agglomeratio");
    public static final DeferredHolder<Item, Item> NATURE_AGGLOMERATIO = register("nature_agglomeratio");
    public static final DeferredHolder<Item, Item> DYE_AGGLOMERATIO = register("dye_agglomeratio");
    public static final DeferredHolder<Item, Item> NETHER_AGGLOMERATIO = register("nether_agglomeratio");
    public static final DeferredHolder<Item, Item> CORAL_AGGLOMERATIO = register("coral_agglomeratio");
    public static final DeferredHolder<Item, Item> HONEY_AGGLOMERATIO = register("honey_agglomeratio");
    public static final DeferredHolder<Item, Item> PRISMARINE_AGGLOMERATIO = register("prismarine_agglomeratio");
    public static final DeferredHolder<Item, Item> END_AGGLOMERATIO = register("end_agglomeratio");
    public static final DeferredHolder<Item, Item> MYSTICAL_FLOWER_AGGLOMERATIO = register("mystical_flower_agglomeratio");
    public static final DeferredHolder<Item, Item> EXPERIENCE_DROPLET = register("experience_droplet", ExperienceDropletItem::new);
    public static final DeferredHolder<Item, Item> WAND = register("wand", WandItem::new);
    public static final DeferredHolder<Item, Item> BLANK_SKULL = register("blank_skull");
    public static final DeferredHolder<Item, Item> BLANK_RECORD = register("blank_record");
    public static final DeferredHolder<Item, Item> BLANK_AUGMENT = register("blank_augment");
    public static final DeferredHolder<Item, Item> SOUL_JAR = register("soul_jar", SoulJarItem::new);
    public static final DeferredHolder<Item, Item> EXPERIENCE_CAPSULE = register("experience_capsule", ExperienceCapsuleItem::new);
    public static final DeferredHolder<Item, Item> WATERING_CAN = register("watering_can", id -> new WateringCanItem(id, 3, 0.25));
    public static final DeferredHolder<Item, Item> UPGRADE_BASE = register("upgrade_base");
    public static final DeferredHolder<Item, Item> INFERIUM_UPGRADE = register("inferium_upgrade", id -> new MachineUpgradeItem(id, MachineUpgradeTier.INFERIUM));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_UPGRADE = register("prudentium_upgrade", id -> new MachineUpgradeItem(id, MachineUpgradeTier.PRUDENTIUM));
    public static final DeferredHolder<Item, Item> TERTIUM_UPGRADE = register("tertium_upgrade", id -> new MachineUpgradeItem(id, MachineUpgradeTier.TERTIUM));
    public static final DeferredHolder<Item, Item> IMPERIUM_UPGRADE = register("imperium_upgrade", id -> new MachineUpgradeItem(id, MachineUpgradeTier.IMPERIUM));
    public static final DeferredHolder<Item, Item> SUPREMIUM_UPGRADE = register("supremium_upgrade", id -> new MachineUpgradeItem(id, MachineUpgradeTier.SUPREMIUM));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_UPGRADE = register("awakened_supremium_upgrade", id -> new MachineUpgradeItem(id, MachineUpgradeTier.AWAKENED_SUPREMIUM));

    public static final DeferredHolder<Item, Item> WOODEN_SICKLE = register("wooden_sickle", id -> new BaseSickleItem(id, ToolMaterial.WOOD, 1));
    public static final DeferredHolder<Item, Item> STONE_SICKLE = register("stone_sickle", id -> new BaseSickleItem(id, ToolMaterial.STONE, 1));
    public static final DeferredHolder<Item, Item> COPPER_SICKLE = register("copper_sickle", id -> new BaseSickleItem(id, ToolMaterial.COPPER, 1));
    public static final DeferredHolder<Item, Item> IRON_SICKLE = register("iron_sickle", id -> new BaseSickleItem(id, ToolMaterial.IRON, 2));
    public static final DeferredHolder<Item, Item> GOLDEN_SICKLE = register("golden_sickle", id -> new BaseSickleItem(id, ToolMaterial.GOLD, 2));
    public static final DeferredHolder<Item, Item> DIAMOND_SICKLE = register("diamond_sickle", id -> new BaseSickleItem(id, ToolMaterial.DIAMOND, 3));
    public static final DeferredHolder<Item, Item> NETHERITE_SICKLE = register("netherite_sickle", id -> new BaseSickleItem(id, ToolMaterial.NETHERITE, 3));

    public static final DeferredHolder<Item, Item> WOODEN_SCYTHE = register("wooden_scythe", id -> new BaseScytheItem(id, ToolMaterial.WOOD, 1));
    public static final DeferredHolder<Item, Item> STONE_SCYTHE = register("stone_scythe", id -> new BaseScytheItem(id, ToolMaterial.STONE, 1));
    public static final DeferredHolder<Item, Item> COPPER_SCYTHE = register("copper_scythe", id -> new BaseScytheItem(id, ToolMaterial.COPPER, 1));
    public static final DeferredHolder<Item, Item> IRON_SCYTHE = register("iron_scythe", id -> new BaseScytheItem(id, ToolMaterial.IRON, 2));
    public static final DeferredHolder<Item, Item> GOLDEN_SCYTHE = register("golden_scythe", id -> new BaseScytheItem(id, ToolMaterial.GOLD, 2));
    public static final DeferredHolder<Item, Item> DIAMOND_SCYTHE = register("diamond_scythe", id -> new BaseScytheItem(id, ToolMaterial.DIAMOND, 3));
    public static final DeferredHolder<Item, Item> NETHERITE_SCYTHE = register("netherite_scythe", id -> new BaseScytheItem(id, ToolMaterial.NETHERITE, 3));

    public static final DeferredHolder<Item, Item> INFERIUM_SWORD = registerGear("inferium_sword", id -> new EssenceSwordItem(id, ModToolMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_PICKAXE = registerGear("inferium_pickaxe", id -> new EssencePickaxeItem(id, ModToolMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_SHOVEL = registerGear("inferium_shovel", id -> new EssenceShovelItem(id, ModToolMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_AXE = registerGear("inferium_axe", id -> new EssenceAxeItem(id, ModToolMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_HOE = registerGear("inferium_hoe", id -> new EssenceHoeItem(id, ModToolMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_STAFF = registerGear("inferium_staff", id -> new EssenceStaffItem(id, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_WATERING_CAN = registerGear("inferium_watering_can", id -> new EssenceWateringCanItem(id, 3, 0.25, CropTier.ONE.getTextColor()));
    public static final DeferredHolder<Item, Item> INFERIUM_BOW = registerGear("inferium_bow", id -> new EssenceBowItem(id, ModToolMaterials.INFERIUM, 1, 1, 1.1F, 1.0F));
    public static final DeferredHolder<Item, Item> INFERIUM_CROSSBOW = registerGear("inferium_crossbow", id -> new EssenceCrossbowItem(id, ModToolMaterials.INFERIUM, 1, 1, 1.1F, 1.0F));
    public static final DeferredHolder<Item, Item> INFERIUM_SPEAR = registerGear("inferium_spear", id -> new EssenceSpearItem(id, ModToolMaterials.INFERIUM, 1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_SHEARS = registerGear("inferium_shears", id -> new EssenceShearsItem(id, ModToolMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_FISHING_ROD = registerGear("inferium_fishing_rod", id -> new EssenceFishingRodItem(id, ModToolMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_SICKLE = registerGear("inferium_sickle", id -> new EssenceSickleItem(id, ModToolMaterials.INFERIUM, 3, CropTier.ONE.getTextColor(), 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_SCYTHE = registerGear("inferium_scythe", id -> new EssenceScytheItem(id, ModToolMaterials.INFERIUM, 3, CropTier.ONE.getTextColor(), 1, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_SWORD = registerGear("prudentium_sword", id -> new EssenceSwordItem(id, ModToolMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_PICKAXE = registerGear("prudentium_pickaxe", id -> new EssencePickaxeItem(id, ModToolMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_SHOVEL = registerGear("prudentium_shovel", id -> new EssenceShovelItem(id, ModToolMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_AXE = registerGear("prudentium_axe", id -> new EssenceAxeItem(id, ModToolMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_HOE = registerGear("prudentium_hoe", id -> new EssenceHoeItem(id, ModToolMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_STAFF = registerGear("prudentium_staff", id -> new EssenceStaffItem(id, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_WATERING_CAN = registerGear("prudentium_watering_can", id -> new EssenceWateringCanItem(id, 5, 0.30, CropTier.TWO.getTextColor()));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_BOW = registerGear("prudentium_bow", id -> new EssenceBowItem(id, ModToolMaterials.PRUDENTIUM, 2, 1, 1.2F, 1.5F));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_CROSSBOW = registerGear("prudentium_crossbow", id -> new EssenceCrossbowItem(id, ModToolMaterials.PRUDENTIUM, 2, 1, 1.2F, 1.5F));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_SPEAR = registerGear("prudentium_spear", id -> new EssenceSpearItem(id, ModToolMaterials.PRUDENTIUM, 1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_SHEARS = registerGear("prudentium_shears", id -> new EssenceShearsItem(id, ModToolMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_FISHING_ROD = registerGear("prudentium_fishing_rod", id -> new EssenceFishingRodItem(id, ModToolMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_SICKLE = registerGear("prudentium_sickle", id -> new EssenceSickleItem(id, ModToolMaterials.PRUDENTIUM, 4, CropTier.TWO.getTextColor(), 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_SCYTHE = registerGear("prudentium_scythe", id -> new EssenceScytheItem(id, ModToolMaterials.PRUDENTIUM, 4, CropTier.TWO.getTextColor(), 2, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_SWORD = registerGear("tertium_sword", id -> new EssenceSwordItem(id, ModToolMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_PICKAXE = registerGear("tertium_pickaxe", id -> new EssencePickaxeItem(id, ModToolMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_SHOVEL = registerGear("tertium_shovel", id -> new EssenceShovelItem(id, ModToolMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_AXE = registerGear("tertium_axe", id -> new EssenceAxeItem(id, ModToolMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_HOE = registerGear("tertium_hoe", id -> new EssenceHoeItem(id, ModToolMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_STAFF = registerGear("tertium_staff", id -> new EssenceStaffItem(id, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_WATERING_CAN = registerGear("tertium_watering_can", id -> new EssenceWateringCanItem(id, 7, 0.35, CropTier.THREE.getTextColor()));
    public static final DeferredHolder<Item, Item> TERTIUM_BOW = registerGear("tertium_bow", id -> new EssenceBowItem(id, ModToolMaterials.TERTIUM, 3, 1, 1.35F, 2.0F));
    public static final DeferredHolder<Item, Item> TERTIUM_CROSSBOW = registerGear("tertium_crossbow", id -> new EssenceCrossbowItem(id, ModToolMaterials.TERTIUM, 3, 1, 1.35F, 2.0F));
    public static final DeferredHolder<Item, Item> TERTIUM_SPEAR = registerGear("tertium_spear", id -> new EssenceSpearItem(id, ModToolMaterials.TERTIUM, 1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_SHEARS = registerGear("tertium_shears", id -> new EssenceShearsItem(id, ModToolMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_FISHING_ROD = registerGear("tertium_fishing_rod", id -> new EssenceFishingRodItem(id, ModToolMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_SICKLE = registerGear("tertium_sickle", id -> new EssenceSickleItem(id, ModToolMaterials.TERTIUM, 5, CropTier.THREE.getTextColor(), 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_SCYTHE = registerGear("tertium_scythe", id -> new EssenceScytheItem(id, ModToolMaterials.TERTIUM, 5, CropTier.THREE.getTextColor(), 3, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_SWORD = registerGear("imperium_sword", id -> new EssenceSwordItem(id, ModToolMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_PICKAXE = registerGear("imperium_pickaxe", id -> new EssencePickaxeItem(id, ModToolMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_SHOVEL = registerGear("imperium_shovel", id -> new EssenceShovelItem(id, ModToolMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_AXE = registerGear("imperium_axe", id -> new EssenceAxeItem(id, ModToolMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_HOE = registerGear("imperium_hoe", id -> new EssenceHoeItem(id, ModToolMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_STAFF = registerGear("imperium_staff", id -> new EssenceStaffItem(id, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_WATERING_CAN = registerGear("imperium_watering_can", id -> new EssenceWateringCanItem(id, 9, 0.40, CropTier.FOUR.getTextColor()));
    public static final DeferredHolder<Item, Item> IMPERIUM_BOW = registerGear("imperium_bow", id -> new EssenceBowItem(id, ModToolMaterials.IMPERIUM, 4, 1, 1.55F, 2.5F));
    public static final DeferredHolder<Item, Item> IMPERIUM_CROSSBOW = registerGear("imperium_crossbow", id -> new EssenceCrossbowItem(id, ModToolMaterials.IMPERIUM, 4, 1, 1.55F, 2.5F));
    public static final DeferredHolder<Item, Item> IMPERIUM_SPEAR = registerGear("imperium_spear", id -> new EssenceSpearItem(id, ModToolMaterials.IMPERIUM, 1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_SHEARS = registerGear("imperium_shears", id -> new EssenceShearsItem(id, ModToolMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_FISHING_ROD = registerGear("imperium_fishing_rod", id -> new EssenceFishingRodItem(id, ModToolMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_SICKLE = registerGear("imperium_sickle", id -> new EssenceSickleItem(id, ModToolMaterials.IMPERIUM, 6, CropTier.FOUR.getTextColor(), 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_SCYTHE = registerGear("imperium_scythe", id -> new EssenceScytheItem(id, ModToolMaterials.IMPERIUM, 6, CropTier.FOUR.getTextColor(), 4, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_SWORD = registerGear("supremium_sword", id -> new EssenceSwordItem(id, ModToolMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_PICKAXE = registerGear("supremium_pickaxe", id -> new EssencePickaxeItem(id, ModToolMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_SHOVEL = registerGear("supremium_shovel", id -> new EssenceShovelItem(id, ModToolMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_AXE = registerGear("supremium_axe", id -> new EssenceAxeItem(id, ModToolMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_HOE = registerGear("supremium_hoe", id -> new EssenceHoeItem(id, ModToolMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_STAFF = registerGear("supremium_staff", id -> new EssenceStaffItem(id, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_WATERING_CAN = registerGear("supremium_watering_can", id -> new EssenceWateringCanItem(id, 11, 0.45, CropTier.FIVE.getTextColor()));
    public static final DeferredHolder<Item, Item> SUPREMIUM_BOW = registerGear("supremium_bow", id -> new EssenceBowItem(id, ModToolMaterials.SUPREMIUM, 5, 1, 1.80F, 3.0F));
    public static final DeferredHolder<Item, Item> SUPREMIUM_CROSSBOW = registerGear("supremium_crossbow", id -> new EssenceCrossbowItem(id, ModToolMaterials.SUPREMIUM, 5, 1, 1.80F, 3.0F));
    public static final DeferredHolder<Item, Item> SUPREMIUM_SPEAR = registerGear("supremium_spear", id -> new EssenceSpearItem(id, ModToolMaterials.SUPREMIUM, 1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_SHEARS = registerGear("supremium_shears", id -> new EssenceShearsItem(id, ModToolMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_FISHING_ROD = registerGear("supremium_fishing_rod", id -> new EssenceFishingRodItem(id, ModToolMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_SICKLE = registerGear("supremium_sickle", id -> new EssenceSickleItem(id, ModToolMaterials.SUPREMIUM, 7, CropTier.FIVE.getTextColor(), 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_SCYTHE = registerGear("supremium_scythe", id -> new EssenceScytheItem(id, ModToolMaterials.SUPREMIUM, 7, CropTier.FIVE.getTextColor(), 5, 1));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_SWORD = registerGear("awakened_supremium_sword", id -> new EssenceSwordItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_PICKAXE = registerGear("awakened_supremium_pickaxe", id -> new EssencePickaxeItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_SHOVEL = registerGear("awakened_supremium_shovel", id -> new EssenceShovelItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_AXE = registerGear("awakened_supremium_axe", id -> new EssenceAxeItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_HOE = registerGear("awakened_supremium_hoe", id -> new EssenceHoeItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_STAFF = registerGear("awakened_supremium_staff", id -> new EssenceStaffItem(id, 5, 1));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_WATERING_CAN = registerGear("awakened_supremium_watering_can", id -> new EssenceWateringCanItem(id, 13, 0.50, CropTier.FIVE.getTextColor()));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_BOW = registerGear("awakened_supremium_bow", id -> new EssenceBowItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2, 2.10F, 4.0F));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_CROSSBOW = registerGear("awakened_supremium_crossbow", id -> new EssenceCrossbowItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2, 2.10F, 4.0F));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_SPEAR = registerGear("awakened_supremium_spear", id -> new EssenceSpearItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_SHEARS = registerGear("awakened_supremium_shears", id -> new EssenceShearsItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_FISHING_ROD = registerGear("awakened_supremium_fishing_rod", id -> new EssenceFishingRodItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_SICKLE = registerGear("awakened_supremium_sickle", id -> new EssenceSickleItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 8, CropTier.FIVE.getTextColor(), 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_SCYTHE = registerGear("awakened_supremium_scythe", id -> new EssenceScytheItem(id, ModToolMaterials.AWAKENED_SUPREMIUM, 8, CropTier.FIVE.getTextColor(), 5, 2));
    public static final DeferredHolder<Item, Item> INFERIUM_HELMET = registerGear("inferium_helmet", id -> new EssenceHelmetItem(id, ModArmorMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_CHESTPLATE = registerGear("inferium_chestplate", id -> new EssenceChestplateItem(id, ModArmorMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_LEGGINGS = registerGear("inferium_leggings", id -> new EssenceLeggingsItem(id, ModArmorMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> INFERIUM_BOOTS = registerGear("inferium_boots", id -> new EssenceBootsItem(id, ModArmorMaterials.INFERIUM, 1, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_HELMET = registerGear("prudentium_helmet", id -> new EssenceHelmetItem(id, ModArmorMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_CHESTPLATE = registerGear("prudentium_chestplate", id -> new EssenceChestplateItem(id, ModArmorMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_LEGGINGS = registerGear("prudentium_leggings", id -> new EssenceLeggingsItem(id, ModArmorMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> PRUDENTIUM_BOOTS = registerGear("prudentium_boots", id -> new EssenceBootsItem(id, ModArmorMaterials.PRUDENTIUM, 2, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_HELMET = registerGear("tertium_helmet", id -> new EssenceHelmetItem(id, ModArmorMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_CHESTPLATE = registerGear("tertium_chestplate", id -> new EssenceChestplateItem(id, ModArmorMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_LEGGINGS = registerGear("tertium_leggings", id -> new EssenceLeggingsItem(id, ModArmorMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> TERTIUM_BOOTS = registerGear("tertium_boots", id -> new EssenceBootsItem(id, ModArmorMaterials.TERTIUM, 3, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_HELMET = registerGear("imperium_helmet", id -> new EssenceHelmetItem(id, ModArmorMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_CHESTPLATE = registerGear("imperium_chestplate", id -> new EssenceChestplateItem(id, ModArmorMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_LEGGINGS = registerGear("imperium_leggings", id -> new EssenceLeggingsItem(id, ModArmorMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> IMPERIUM_BOOTS = registerGear("imperium_boots", id -> new EssenceBootsItem(id, ModArmorMaterials.IMPERIUM, 4, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_HELMET = registerGear("supremium_helmet", id -> new EssenceHelmetItem(id, ModArmorMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_CHESTPLATE = registerGear("supremium_chestplate", id -> new EssenceChestplateItem(id, ModArmorMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_LEGGINGS = registerGear("supremium_leggings", id -> new EssenceLeggingsItem(id, ModArmorMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> SUPREMIUM_BOOTS = registerGear("supremium_boots", id -> new EssenceBootsItem(id, ModArmorMaterials.SUPREMIUM, 5, 1));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_HELMET = registerGear("awakened_supremium_helmet", id -> new EssenceHelmetItem(id, ModArmorMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_CHESTPLATE = registerGear("awakened_supremium_chestplate", id -> new EssenceChestplateItem(id, ModArmorMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_LEGGINGS = registerGear("awakened_supremium_leggings", id -> new EssenceLeggingsItem(id, ModArmorMaterials.AWAKENED_SUPREMIUM, 5, 2));
    public static final DeferredHolder<Item, Item> AWAKENED_SUPREMIUM_BOOTS = registerGear("awakened_supremium_boots", id -> new EssenceBootsItem(id, ModArmorMaterials.AWAKENED_SUPREMIUM, 5, 2));

    @SubscribeEvent
    public void onRegisterItems(RegisterEvent event) {
        event.register(Registries.ITEM, registry -> {
            for (var entry : BLOCK_ENTRIES.entrySet()) {
                registry.register(entry.getKey(), entry.getValue().apply(entry.getKey()));
            }

            for (var entry : ENTRIES.entrySet()) {
                registry.register(entry.getKey().getId(), entry.getValue().apply(entry.getKey().getId()));
            }

            CropRegistry.getInstance().onRegisterItems(registry);

            for (var entry : GEAR_ENTRIES.entrySet()) {
                registry.register(entry.getKey().getId(), entry.getValue().apply(entry.getKey().getId()));
            }

            AugmentRegistry.getInstance().onRegisterItems(registry);
        });
    }

    private static DeferredHolder<Item, Item> register(String name) {
        return register(name, BaseItem::new);
    }

    private static DeferredHolder<Item, Item> register(String name, Function<Identifier, Item> item) {
        var id = MysticalAgriculture.resource(name);
        var holder = DeferredHolder.create(Registries.ITEM, id);
        ENTRIES.put(holder, item);
        return holder;
    }

    private static DeferredHolder<Item, Item> registerGear(String name, Function<Identifier, Item> item) {
        var id = MysticalAgriculture.resource(name);
        var holder = DeferredHolder.create(Registries.ITEM, id);
        GEAR_ENTRIES.put(holder, item);
        return holder;
    }
}