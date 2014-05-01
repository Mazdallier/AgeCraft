package org.agecraft;

import net.minecraft.client.Minecraft;

import org.agecraft.core.Trees;
import org.agecraft.core.blocks.tree.BlockLeaves;
import org.agecraft.core.gui.GuiInGame;
import org.agecraft.core.gui.GuiTechTreeComponent;
import org.agecraft.core.gui.menu.GuiMainMenu;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ACTickHandlerClient {

	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event) {
		if(event.phase == Phase.END) {
			Minecraft mc = Minecraft.getMinecraft();
			if(mc.currentScreen instanceof net.minecraft.client.gui.GuiMainMenu && !(mc.currentScreen instanceof GuiMainMenu)) {
				mc.displayGuiScreen(new GuiMainMenu());
			}
			if(!(mc.ingameGUI instanceof GuiInGame)) {
				mc.ingameGUI = new GuiInGame(mc);
			}
			if(GuiTechTreeComponent.instance == null) {
				GuiTechTreeComponent.instance = new GuiTechTreeComponent();
			}
			GuiTechTreeComponent.instance.updateTechTreeWindow();
		}
	}
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			Minecraft mc = Minecraft.getMinecraft();
			if(((BlockLeaves) Trees.leaves).fancyGraphics != mc.gameSettings.fancyGraphics) {
				((BlockLeaves) Trees.leaves).fancyGraphics = mc.gameSettings.fancyGraphics;
			}
		}
	}
}
