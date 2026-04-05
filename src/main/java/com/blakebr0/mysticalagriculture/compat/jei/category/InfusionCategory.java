package com.blakebr0.mysticalagriculture.compat.jei.category;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import com.blakebr0.mysticalagriculture.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public class InfusionCategory implements IRecipeCategory<RecipeHolder<IInfusionRecipe>> {
    private static final Identifier TEXTURE = MysticalAgriculture.resource("textures/jei/infusion.png");
    public static final IRecipeHolderType<IInfusionRecipe> RECIPE_TYPE = IRecipeHolderType.create(MysticalAgriculture.resource("infusion"));

    private final IDrawable background;
    private final IDrawable icon;

    public InfusionCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 144, 81);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.INFUSION_ALTAR.get()));
    }

    @Override
    public IRecipeType<RecipeHolder<IInfusionRecipe>> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category.mysticalagriculture.infusion");
    }

    @Override
    public int getWidth() {
        return this.background.getWidth();
    }

    @Override
    public int getHeight() {
        return this.background.getHeight();
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<IInfusionRecipe> recipe, IFocusGroup focuses) {
        var displays = recipe.value().display();
        if (!displays.isEmpty() && displays.getFirst() instanceof ShapelessCraftingRecipeDisplay display) {

            var inputs = display.ingredients();
            var result = display.result();

            builder.addSlot(RecipeIngredientRole.INPUT, 33, 33).add(inputs.get(0));

            var pedestals = inputs.size() - 1;

            switch (pedestals) {
                case 1 -> addSlot(builder, SlotPosition.NORTH, inputs.get(1));
                case 2 -> {
                    addSlot(builder, SlotPosition.NORTH, inputs.get(1));
                    addSlot(builder, SlotPosition.SOUTH, inputs.get(2));
                }
                case 3 -> {
                    addSlot(builder, SlotPosition.WEST, inputs.get(1));
                    addSlot(builder, SlotPosition.NORTH, inputs.get(2));
                    addSlot(builder, SlotPosition.EAST, inputs.get(3));
                }
                case 4 -> {
                    addSlot(builder, SlotPosition.NORTH, inputs.get(1));
                    addSlot(builder, SlotPosition.EAST, inputs.get(2));
                    addSlot(builder, SlotPosition.SOUTH, inputs.get(3));
                    addSlot(builder, SlotPosition.WEST, inputs.get(4));
                }
                case 5 -> {
                    addSlot(builder, SlotPosition.NORTH_WEST, inputs.get(1));
                    addSlot(builder, SlotPosition.NORTH, inputs.get(2));
                    addSlot(builder, SlotPosition.NORTH_EAST, inputs.get(3));
                    addSlot(builder, SlotPosition.SOUTH_EAST, inputs.get(4));
                    addSlot(builder, SlotPosition.SOUTH_WEST, inputs.get(5));
                }
                case 6 -> {
                    addSlot(builder, SlotPosition.NORTH_WEST, inputs.get(1));
                    addSlot(builder, SlotPosition.NORTH, inputs.get(2));
                    addSlot(builder, SlotPosition.NORTH_EAST, inputs.get(3));
                    addSlot(builder, SlotPosition.SOUTH_EAST, inputs.get(4));
                    addSlot(builder, SlotPosition.SOUTH, inputs.get(5));
                    addSlot(builder, SlotPosition.SOUTH_WEST, inputs.get(6));
                }
                case 7 -> {
                    addSlot(builder, SlotPosition.WEST, inputs.get(1));
                    addSlot(builder, SlotPosition.NORTH_WEST, inputs.get(2));
                    addSlot(builder, SlotPosition.NORTH, inputs.get(3));
                    addSlot(builder, SlotPosition.NORTH_EAST, inputs.get(4));
                    addSlot(builder, SlotPosition.EAST, inputs.get(5));
                    addSlot(builder, SlotPosition.SOUTH_EAST, inputs.get(6));
                    addSlot(builder, SlotPosition.SOUTH_WEST, inputs.get(7));
                }
                case 8 -> {
                    addSlot(builder, SlotPosition.NORTH_WEST, inputs.get(1));
                    addSlot(builder, SlotPosition.NORTH, inputs.get(2));
                    addSlot(builder, SlotPosition.NORTH_EAST, inputs.get(3));
                    addSlot(builder, SlotPosition.EAST, inputs.get(4));
                    addSlot(builder, SlotPosition.SOUTH_EAST, inputs.get(5));
                    addSlot(builder, SlotPosition.SOUTH, inputs.get(6));
                    addSlot(builder, SlotPosition.SOUTH_WEST, inputs.get(7));
                    addSlot(builder, SlotPosition.WEST, inputs.get(8));
                }
            }

            builder.addSlot(RecipeIngredientRole.OUTPUT, 123, 33).add(result);
        }
    }

    private static void addSlot(IRecipeLayoutBuilder builder, SlotPosition position, SlotDisplay ingredient) {
        builder.addSlot(RecipeIngredientRole.INPUT, position.x, position.y).add(ingredient);
    }

    private enum SlotPosition {
        NORTH_WEST(7, 7),
        NORTH(33, 1),
        NORTH_EAST(59, 7),
        EAST(65, 33),
        SOUTH_EAST(59, 59),
        SOUTH(33, 64),
        SOUTH_WEST(7, 59),
        WEST(1, 33);

        final int x;
        final int y;

        SlotPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
