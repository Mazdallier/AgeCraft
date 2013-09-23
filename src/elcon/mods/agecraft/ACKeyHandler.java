package elcon.mods.agecraft;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import elcon.mods.agecraft.core.tech.gui.GuiTechTree;

public class ACKeyHandler extends KeyHandler {

	public static KeyBinding techTree = new KeyBinding("Technology Tree", Keyboard.KEY_F);
	
	public ACKeyHandler() {
		super(new KeyBinding[]{techTree}, new boolean[]{false});
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if(Minecraft.getMinecraft().currentScreen == null) {
			if(kb.keyCode == techTree.keyCode) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiTechTree());
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
	
	@Override
	public String getLabel() {
		return "AgeCraftKeyHandler";
	}
}
