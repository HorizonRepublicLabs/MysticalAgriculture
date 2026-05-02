package com.blakebr0.mysticalagriculture.api.crop;

import com.blakebr0.mysticalagriculture.api.MysticalAgricultureAPI;
import net.minecraft.resources.Identifier;

/**
 * Helper class used to specify crop model locations
 */
public class CropModels {
    public static final Identifier FLOWER_INGOT_BLANK = MysticalAgricultureAPI.resource("block/flower_ingot");
    public static final Identifier FLOWER_ROCK_BLANK = MysticalAgricultureAPI.resource("block/flower_rock");
    public static final Identifier FLOWER_DUST_BLANK = MysticalAgricultureAPI.resource("block/flower_dust");
    public static final Identifier FLOWER_FACE_BLANK = MysticalAgricultureAPI.resource("block/flower_face");

    public static final Identifier ESSENCE_INGOT_BLANK = MysticalAgricultureAPI.resource("item/essence_ingot");
    public static final Identifier ESSENCE_ROCK_BLANK = MysticalAgricultureAPI.resource("item/essence_rock");
    public static final Identifier ESSENCE_DUST_BLANK = MysticalAgricultureAPI.resource("item/essence_dust");
    public static final Identifier ESSENCE_GEM_BLANK = MysticalAgricultureAPI.resource("item/essence_gem");
    public static final Identifier ESSENCE_TALL_GEM_BLANK = MysticalAgricultureAPI.resource("item/essence_tall_gem");
    public static final Identifier ESSENCE_DIAMOND_BLANK = MysticalAgricultureAPI.resource("item/essence_diamond");
    public static final Identifier ESSENCE_QUARTZ_BLANK = MysticalAgricultureAPI.resource("item/essence_quartz");
    public static final Identifier ESSENCE_FLAME_BLANK = MysticalAgricultureAPI.resource("item/essence_flame");
    public static final Identifier ESSENCE_ROD_BLANK = MysticalAgricultureAPI.resource("item/essence_rod");

    public static final Identifier SEED_BLANK = MysticalAgricultureAPI.resource("item/mystical_seeds");

    private Identifier flowerModel;
    private Identifier essenceModel;
    private Identifier seedModel;

    /**
     * Set up all crop-related textures using its specific textures
     */
    public CropModels() {
        this(null, null, null);
    }

    /**
     * Set up all crop-related textures using the provided resource locations, with seeds defaulting to blank
     * @param flowerModel flower texture location
     * @param essenceModel essence texture location
     */
    public CropModels(Identifier flowerModel, Identifier essenceModel) {
        this(flowerModel, essenceModel, SEED_BLANK);
    }

    /**
     * Set up all crop-related textures using their resource locations, pass null to default to the crop-specific texture
     * @param flowerModel flower texture location
     * @param essenceModel essence texture location
     * @param seedModel seed texture location
     */
    public CropModels(Identifier flowerModel, Identifier essenceModel, Identifier seedModel) {
        this.flowerModel = flowerModel;
        this.essenceModel = essenceModel;
        this.seedModel = seedModel;
    }

    public Identifier getFlowerModel() {
        return this.flowerModel;
    }

    public CropModels setFlowerModel(Identifier location) {
        this.flowerModel = location;
        return this;
    }

    public Identifier getEssenceModel() {
        return this.essenceModel;
    }

    public CropModels setEssenceModel(Identifier location) {
        this.essenceModel = location;
        return this;
    }

    public Identifier getSeedModel() {
        return this.seedModel;
    }

    public CropModels setSeedModel(Identifier location) {
        this.seedModel = location;
        return this;
    }

    /**
     * Used to add fallback locations (the crop-specific locations) if any of the locations are null
     * @param id the id of the crop to generate texture locations for
     * @return this crop textures instance with non-null locations
     */
    public CropModels init(Identifier id) {
        var modid = id.getNamespace();
        var name = id.getPath();

        if (this.flowerModel == null)
            this.flowerModel = Identifier.fromNamespaceAndPath(modid, "block/flower/" + name + "_flower");
        if (this.essenceModel == null)
            this.essenceModel = Identifier.fromNamespaceAndPath(modid, "item/essence/" + name + "_essence");
        if (this.seedModel == null)
            this.seedModel = Identifier.fromNamespaceAndPath(modid, "item/seeds/" + name + "_seeds");

        return this;
    }
}
