package elcon.mods.agecraft.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

import org.lwjgl.opengl.GL11;

import elcon.mods.agecraft.assets.resources.ResourcesCore;

public class GuiTrade extends GuiContainer {

	public GuiTrade(ContainerTrade container) {
		super(container);
		xSize = 176;
		ySize = 168;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString("ElConquisador", -59, 13, 0x404040);
		
		fontRenderer.drawString("ElConquisador", -59, 55, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ResourcesCore.guiTrade);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		drawTexturedModalRect(guiLeft - 64, guiTop + 8, 176, 0, 67, 42);
		drawTexturedModalRect(guiLeft - 64, guiTop + 50, 176, 0, 67, 42);
	}
}
