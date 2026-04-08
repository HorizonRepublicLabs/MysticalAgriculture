package com.blakebr0.mysticalagriculture.api.crop;

import com.blakebr0.mysticalagriculture.api.MysticalAgricultureAPI;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CropType {
    public static final CropType RESOURCE = new CropType(MysticalAgricultureAPI.resource("resource"), MysticalAgricultureAPI.resource("block/mystical_resource_crop"));
    public static final CropType MOB = new CropType(MysticalAgricultureAPI.resource("mob"), MysticalAgricultureAPI.resource("block/mystical_mob_crop"));

    private final Identifier id;
    private final Identifier stemModel;
    private Supplier<? extends Item> craftingSeed;

    /**
     * Represents a type of crop, such as resource or mob
     * @param id the name of this type
     * @param stemModel the stem model for all crops of this type
     */
    public CropType(Identifier id, Identifier stemModel) {
        this.id = id;
        this.stemModel = stemModel;
    }

    /**
     * @return the id of this crop tier
     */
    public Identifier getId() {
        return this.id;
    }

    /**
     * @return the name of this tier
     */
    public String getName() {
        return this.id.getPath();
    }

    /**
     * @return the mod id of the mod that created this tier
     */
    public String getModId() {
        return this.id.getNamespace();
    }

    /**
     * The base model location used for the stem models of this tier, excluding the _# for age
     * @return the resource location of the stem model for this type
     */
    public Identifier getStemModel() {
        return this.stemModel;
    }

    /**
     * The crafting seed used in recipes for all crops of this type
     * @return the crafting seed of this type
     */
    public Item getCraftingSeedItem() {
        return this.craftingSeed == null ? null : this.craftingSeed.get();
    }

    /**
     * Used to set the crafting seed item instance for this crop
     * @param item the crafting seed item
     * @return this crop type
     */
    public CropType setCraftingSeedItem(Supplier<? extends Item> item) {
        this.craftingSeed = item;
        return this;
    }
}
