package org.agecraft;

import java.util.Calendar;
import java.util.EnumSet;

import org.agecraft.core.Trees;
import org.agecraft.core.blocks.tree.BlockLeaves;
import org.agecraft.core.gui.GuiInGame;
import org.agecraft.core.tech.gui.GuiTechTreeComponent;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatMessageComponent;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ACTickHandlerClient implements ITickHandler {
	
	public boolean welcomeMessage;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		Minecraft mc = Minecraft.getMinecraft();				
		if(type.contains(TickType.RENDER)) {
			if(GuiTechTreeComponent.instance == null) {
				GuiTechTreeComponent.instance = new GuiTechTreeComponent();
			}
			GuiTechTreeComponent.instance.updateTechTreeWindow();
		}
		if(type.contains(TickType.CLIENT)) {
			if(!(mc.ingameGUI instanceof GuiInGame)) {
				mc.ingameGUI = new GuiInGame(Minecraft.getMinecraft());
			}
			//if(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory && !(mc.currentScreen instanceof GuiInventory)) {
				//mc.thePlayer.closeScreen();
				//PacketDispatcher.sendPacketToServer(ACPacketHandlerClient.getInventoryOpenPacket(mc.thePlayer.dimension, mc.thePlayer.username));
			//}
			if(((BlockLeaves) Trees.leaves).fancyGraphics != mc.gameSettings.fancyGraphics) {
				((BlockLeaves) Trees.leaves).fancyGraphics = mc.gameSettings.fancyGraphics;
			}
			if(mc.theWorld != null && mc.thePlayer != null) {
				if(!welcomeMessage) {
					int month = 1 + Calendar.getInstance().get(Calendar.MONTH);
					int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
					if(month == 12) {
						if(day == 25 || day == 26) {
							mc.thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("<AgeCraftTeam> Merry Christmas!"));
						} else if(day == 31) {
							mc.thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("<AgeCraftTeam> Last day of the year!"));
						}
					} else if(month == 1) {
						if(day == 1) {
							mc.thePlayer.sendChatToPlayer(ChatMessageComponent.createFromText("<AgeCraftTeam> Happy new year!"));
						}
					}
					welcomeMessage = true;
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "AgeCraftTickHandler";
	}
}
