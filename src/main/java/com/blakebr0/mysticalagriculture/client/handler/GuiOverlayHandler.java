package com.blakebr0.mysticalagriculture.client.handler;

import com.blakebr0.mysticalagriculture.api.crafting.IAwakeningRecipe;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import com.blakebr0.mysticalagriculture.tileentity.AwakeningAltarTileEntity;
import com.blakebr0.mysticalagriculture.tileentity.EssenceVesselTileEntity;
import com.blakebr0.mysticalagriculture.tileentity.InfusionAltarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public final class GuiOverlayHandler {
    @SubscribeEvent
    public void onPostRenderGui(RenderGuiEvent.Post event) {
        var mc = Minecraft.getInstance();
        var level = mc.level;

        if (level == null)
            return;

        var gfx = event.getGuiGraphics();

        if (mc.hitResult instanceof BlockHitResult result) {
            var pos = result.getBlockPos();
            var tile = level.getBlockEntity(pos);
            var stack = ItemStack.EMPTY;

            if (tile instanceof InfusionAltarTileEntity altar) {
                var recipeId = altar.getActiveRecipeId();
                if (recipeId != null) {
                    var recipe = ClientRecipeHandler.INFUSION_RECIPE_MAP.get(recipeId);

                    if (recipe != null) {
                        stack = recipe.assemble(CraftingInput.EMPTY);
                    }
                }
            }

            if (tile instanceof AwakeningAltarTileEntity altar) {
                var recipeId = altar.getActiveRecipeId();
                if (recipeId != null) {
                    var recipe = ClientRecipeHandler.AWAKENING_RECIPE_MAP.get(recipeId);

                    if (recipe != null) {
                        stack = recipe.assemble(CraftingInput.EMPTY);

                        drawEssenceRequirements(gfx, recipe, altar, level);
                    }
                }
            }

            if (!stack.isEmpty()) {
                int x = mc.getWindow().getGuiScaledWidth() / 2 - 11;
                int y = mc.getWindow().getGuiScaledHeight() / 2 - 8;

                gfx.item(stack, x + 26, y);
                gfx.itemDecorations(mc.font, stack, x + 26, y);
                gfx.text(mc.font, stack.getHoverName(), x + 48, y + 5, 0xFFFFFFFF);
            }
        }

        if (mc.hitResult instanceof BlockHitResult result) {
            var pos = result.getBlockPos();
            var tile = mc.level.getBlockEntity(pos);

            if (tile instanceof EssenceVesselTileEntity vessel) {
                var inventory = vessel.getInventory();
                var resource = inventory.getResource(0);

                if (!resource.isEmpty()) {
                    var amount = inventory.getAmountAsInt(0);
                    int x = mc.getWindow().getGuiScaledWidth() / 2 - 11;
                    int y = mc.getWindow().getGuiScaledHeight() / 2 - 8;
                    var stack = resource.toStack(amount);

                    gfx.item(stack, x + 26, y);
                    gfx.itemDecorations(mc.font, stack, x + 26, y);
                    gfx.text(mc.font, resource.getHoverName(), x + 48, y + 5, 0xFFFFFFFF);
                }
            }
        }
    }

    private static void drawEssenceRequirements(GuiGraphicsExtractor gfx, IAwakeningRecipe recipe, AwakeningAltarTileEntity altar, Level level) {
        var mc = Minecraft.getInstance();

        int x = mc.getWindow().getGuiScaledWidth() / 2 - 11;
        int y = mc.getWindow().getGuiScaledHeight() / 2 - 4;
        var lineHeight = mc.font.lineHeight + 6;

        var hasMissingEssences = false;
        var xOffset = 0;

        var missingEssences = recipe.getMissingEssences(altar.getEssenceItems());

        for (var essence : missingEssences.entrySet()) {
            var stack = essence.getKey().ingredient().display().resolveForFirstStack(SlotDisplayContext.fromLevel(level));

            gfx.item(stack, x + 26 + xOffset, y + 2 * lineHeight);
            gfx.text(mc.font, getEssenceDisplayName(stack, essence.getValue()), x + 48 + xOffset, y + 5 + 2 * lineHeight, 16383998);

            xOffset += 56;
            hasMissingEssences = true;
        }

        if (hasMissingEssences) {
            gfx.text(mc.font, ModTooltips.MISSING_ESSENCES.toComponent(), x + 28, y + 5 + lineHeight, 16383998);
        }
    }

    private static String getEssenceDisplayName(ItemStack stack, int missing) {
        var required = stack.getCount();
        return required - missing + "/" + required;
    }
}
