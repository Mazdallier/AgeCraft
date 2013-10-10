package elcon.mods.agecraft;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.blocks.tree.BlockLeaves;
import elcon.mods.agecraft.core.gui.GuiInGame;
import elcon.mods.agecraft.core.tech.gui.GuiTechTreeComponent;

@SideOnly(Side.CLIENT)
public class ACTickHandlerClient implements ITickHandler {
	
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
			if(!(Minecraft.getMinecraft().ingameGUI instanceof GuiInGame)) {
				Minecraft.getMinecraft().ingameGUI = new GuiInGame(Minecraft.getMinecraft());
			}
			//if(Minecraft.getMinecraft().currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory && !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory)) {
			//	Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(Minecraft.getMinecraft().thePlayer));
			//}
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
