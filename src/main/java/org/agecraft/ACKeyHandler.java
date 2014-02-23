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

	public ACKeyHandler() {
		Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.addAll(Minecraft.getMinecraft().gameSettings.keyBindings, changeGuiScale, techTree);
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
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
