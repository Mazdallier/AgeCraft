package org.agecraft.core.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

import org.agecraft.core.AgeCraftCore;
import org.agecraft.core.AgeCraftCoreClient;
import org.lwjgl.opengl.GL11;

import elcon.mods.elconqore.gui.GuiToggleButton;

public class GuiAnvil extends GuiContainer {

	public ContainerAnvil container;
	
	public GuiAnvil(ContainerAnvil container) {
		super(container);
		this.container = container;
		
		xSize = 176;
		ySize = 204;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		GuiToggleButton button1 = new GuiToggleButton(0, guiLeft + 130, guiTop + 5, 176, 0, 18, 18, AgeCraftCoreClient.guiAnvil, "", true);
		button1.toggled = true;
		buttonList.add(button1);
		GuiToggleButton button2 = new GuiToggleButton(1, guiLeft + 150, guiTop + 5, 194, 0, 18, 18, AgeCraftCoreClient.guiAnvil, "", true);
		buttonList.add(button2);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 1) {
			AgeCraftCore.instance.packetHandler.sendToServer(new MessageOpenGui(container.player.worldObj.provider.dimensionId, container.player.getCommandSenderName(), container.anvil.xCoord, container.anvil.yCoord, container.anvil.zCoord, 14));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(AgeCraftCoreClient.guiAnvil);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
