package org.agecraft;

import net.minecraft.client.Minecraft;

import org.agecraft.core.Trees;
import org.agecraft.core.blocks.tree.BlockLeaves;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class ACTickHandlerClient {

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
