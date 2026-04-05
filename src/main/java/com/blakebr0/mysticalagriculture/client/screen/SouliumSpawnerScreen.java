package com.blakebr0.mysticalagriculture.client.screen;

import com.blakebr0.cucumber.client.screen.BaseContainerScreen;
import com.blakebr0.cucumber.client.screen.widget.EnergyBarWidget;
import com.blakebr0.cucumber.util.Formatting;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.container.SouliumSpawnerContainer;
import com.blakebr0.mysticalagriculture.tileentity.SouliumSpawnerTileEntity;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class SouliumSpawnerScreen extends BaseContainerScreen<SouliumSpawnerContainer> {
    private static final Identifier BACKGROUND = MysticalAgriculture.resource("textures/gui/soulium_spawner.png");
    private SouliumSpawnerTileEntity tile;

    public SouliumSpawnerScreen(SouliumSpawnerContainer container, Inventory inv, Component title) {
        super(container, inv, title, BACKGROUND, 176, 194);
    }

    @Override
    protected void init() {
        super.init();

        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        this.tile = this.getTileEntity();

        this.addRenderableWidget(new EnergyBarWidget(x + 7, y + 17, this.menu::getEnergyStored, this.menu::getEnergyCapacity));
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor gfx, int mouseX, int mouseY) {
        gfx.text(this.font, this.title, (this.imageWidth / 2 - this.font.width(this.title) / 2), 6, 4210752, false);
        gfx.text(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 2), 4210752, false);
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor gfx, int mouseX, int mouseY) {
        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        super.extractTooltip(gfx, mouseX, mouseY);

        if (this.menu.getFuelLeft() > 0 && mouseX > x + 30 && mouseX < x + 45 && mouseY > y + 39 && mouseY < y + 53) {
            gfx.setTooltipForNextFrame(this.font, Formatting.energy(this.menu.getFuelLeft()), mouseX, mouseY);
        }

        if (this.tile != null) {
            var displayEntity = this.tile.getDisplayEntity();

            if (displayEntity != null && isHoveringSlot(x + 134, y + 52, mouseX, mouseY)) {
                var entity = displayEntity.entity();
                var chance = displayEntity.chance();

                var text = Component.empty().append(entity.getDisplayName()).append(" (%.2f%%)".formatted(chance));

                gfx.setTooltipForNextFrame(this.font, text, mouseX, mouseY);
            }
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float a) {
        super.extractBackground(gfx, mouseX, mouseY, a);

        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        if (this.menu.getFuelItemValue() > 0) {
            int i = this.getBurnLeftScaled(13);
            gfx.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, x + 31, y + 52 - i, 176, 12 - i, 14, i + 1, 256, 256);
        }

        if (this.menu.getProgress() > 0) {
            int i2 = this.getProgressScaled(24);
            gfx.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, x + 98, y + 51, 176, 14, i2 + 1, 16, 256, 256);
        }

        this.renderEntityPreview(gfx);

        if (isHoveringSlot(x + 134, y + 52, mouseX, mouseY)) {
//            TODO render slot highlight
//            renderSlotHighlight(gfx, x + 134, y + 52, 0);
        }
    }

    private SouliumSpawnerTileEntity getTileEntity() {
        var level = this.getMinecraft().level;

        if (level != null) {
            var tile = level.getBlockEntity(this.getMenu().getBlockPos());

            if (tile instanceof SouliumSpawnerTileEntity spawner) {
                return spawner;
            }
        }

        return null;
    }

    public int getProgressScaled(int pixels) {
        int i = this.menu.getProgress();
        int j = this.menu.getOperationTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    public int getBurnLeftScaled(int pixels) {
        int i = this.menu.getFuelLeft();
        int j = this.menu.getFuelItemValue();
        return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
    }

    private void renderEntityPreview(GuiGraphicsExtractor gfx) {
        if (this.tile == null)
            return;

        var displayEntity = this.tile.getDisplayEntity();
        if (displayEntity == null)
            return;

        var entity = displayEntity.entity();

        float scale = 16.0F;
        float bbMax = Math.max(entity.getBbWidth(), entity.getBbHeight());

        if ((double) bbMax > 1.0D) {
            scale /= bbMax;
        }

        var matrix = gfx.pose();

        matrix.pushMatrix();
// TODO rendering entity in soulium spawner screen
//        matrix.translate(this.leftPos + 142, this.topPos + 68, 32.0F);
//        matrix.mul(Axis.YP.rotationDegrees(135.0F));
//        matrix.mul(Axis.XP.rotationDegrees(180.0F));
//        matrix.scale(scale, scale, scale);
//
//        var buffer = gfx.bufferSource();
//
//        Minecraft.getInstance().getEntityRenderDispatcher().render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1, matrix, buffer, 255);

        matrix.popMatrix();
    }

    private static boolean isHoveringSlot(int x, int y, int mouseX, int mouseY) {
        return mouseX > x - 1 && mouseX < x + 16 && mouseY > y - 1 && mouseY < y + 16;
    }
}
