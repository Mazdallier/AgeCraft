package org.agecraft.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

import org.agecraft.core.AgeCraftCoreClient;
import org.lwjgl.opengl.GL11;

public class GuiAnvilRepair extends GuiContainer {

	public ContainerAnvil container;
	
	public GuiAnvilRepair(ContainerAnvil container) {
		super(container);
		this.container = container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(AgeCraftCoreClient.guiAnvilRepair);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
