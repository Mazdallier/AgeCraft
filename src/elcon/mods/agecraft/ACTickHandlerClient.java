package elcon.mods.agecraft;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.blocks.tree.BlockLeaves;
import elcon.mods.agecraft.core.gui.GuiInGame;
import elcon.mods.agecraft.core.tech.gui.GuiTechTreePopup;

public class ACTickHandlerClient implements ITickHandler {

	public static GuiTechTreePopup techTreePopup = null;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		Minecraft mc = Minecraft.getMinecraft();
		//EntityPlayer player = (EntityPlayer) mc.thePlayer;
		//long time = System.currentTimeMillis();
		
		if(techTreePopup == null) {
			techTreePopup = new GuiTechTreePopup(mc);
		}
		techTreePopup.updateTechTreeWindow();
		
		if(type.contains(TickType.RENDER)) {
			
		}
		if(type.contains(TickType.CLIENT)) {
			if(!(Minecraft.getMinecraft().ingameGUI instanceof GuiInGame)) {
				Minecraft.getMinecraft().ingameGUI = new GuiInGame(Minecraft.getMinecraft());
			}
			if(((BlockLeaves) Trees.leaves).fancyGraphics != mc.gameSettings.fancyGraphics) {
				((BlockLeaves) Trees.leaves).fancyGraphics = mc.gameSettings.fancyGraphics;
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
