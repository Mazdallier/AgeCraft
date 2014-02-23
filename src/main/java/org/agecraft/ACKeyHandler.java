package org.agecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.agecraft.core.gui.GuiTechTree;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class ACKeyHandler {

	public static KeyBinding changeGuiScale = new KeyBinding("Change Gui Scale", Keyboard.KEY_F10, "key.categories.misc");
	public static KeyBinding techTree = new KeyBinding("Technology Tree", Keyboard.KEY_F, "key.categories.inventory");
	public static KeyBinding trade = new KeyBinding("Trade", Keyboard.KEY_G, "key.categories.gameplay");
	
	public ACKeyHandler() {
		Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.addAll(Minecraft.getMinecraft().gameSettings.keyBindings, changeGuiScale, techTree, trade);
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.inGameHasFocus) {
			if(trade.isPressed()) {
				//if(mc.pointedEntityLiving != null && mc.pointedEntityLiving instanceof EntityPlayer) {
				//	PacketDispatcher.sendPacketToServer(ACPacketHandlerClient.getTradePacket(mc.thePlayer.dimension, mc.thePlayer.username, ((EntityPlayer) mc.pointedEntityLiving).username));
				//}
			}
		}
		if(mc.currentScreen == null) {
			if(techTree.isPressed()) {
				mc.displayGuiScreen(new GuiTechTree());
			}
		} else {
			if(changeGuiScale.isPressed()) {
				ACUtilClient.changeGuiScale(mc);
			}
		}
		
	}
}
