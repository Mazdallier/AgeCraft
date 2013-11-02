package elcon.mods.agecraft.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import elcon.mods.agecraft.ACPacketHandlerClient;
import elcon.mods.agecraft.assets.resources.ResourcesCore;

public class GuiPlayerTrade extends GuiContainer {
	
	public ContainerPlayerTrade container;
	public String player1;
	public String player2;
	
	public GuiPlayerTrade(ContainerPlayerTrade container) {
		super(container);
		this.container = container;
		xSize = 176;
		ySize = 168;
		player1 = container.current == 0 ? container.trade.player1 : container.trade.player2;
		player2 = container.current == 0 ? container.trade.player2 : container.trade.player1;
	}
	
	@Override
	protected void mouseClicked(int x, int y, int id) {
		if(x >= guiLeft + 77 && x < guiLeft + 99 && y >= guiTop + 27 && y < guiTop + 42) {
			PacketDispatcher.sendPacketToServer(ACPacketHandlerClient.getTradeAcceptPacket(player1, container.current, container.current == 0 ? !container.trade.accepted1 : !container.trade.accepted2));
		} else {
			super.mouseClicked(x, y, id);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(player1, 7, 6, 0x404040);
		fontRenderer.drawString(player2, 169 - fontRenderer.getStringWidth(player2), 6, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ResourcesCore.guiTrade);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		drawTexturedModalRect(guiLeft + 77, guiTop + 27, (container.current == 0 ? container.trade.accepted1 : container.trade.accepted2) ? 22 : 0, 168, 22, 15);
		drawTexturedModalRect(guiLeft + 77, guiTop + 46, (container.current == 0 ? container.trade.accepted2 : container.trade.accepted1) ? 22 : 0, 183, 22, 15);
	}
}
