package com.blakebr0.mysticalagriculture.client.screen;

import com.blakebr0.cucumber.client.screen.BaseContainerScreen;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.container.EnchanterContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class EnchanterScreen extends BaseContainerScreen<EnchanterContainer> {
    private static final Identifier BACKGROUND = MysticalAgriculture.resource("textures/gui/enchanter.png");

    public EnchanterScreen(EnchanterContainer container, Inventory inv, Component title) {
        super(container, inv, title, BACKGROUND, 176, 177);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor gfx, int mouseX, int mouseY) {
        gfx.text(this.font, this.title, (this.imageWidth / 2 - this.font.width(this.title) / 2), 6, -12566464, false);
        gfx.text(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 2), -12566464, false);
    }
}
