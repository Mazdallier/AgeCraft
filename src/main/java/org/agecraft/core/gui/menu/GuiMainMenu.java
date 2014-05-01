package org.agecraft.core.gui.menu;

import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMainMenu extends net.minecraft.client.gui.GuiMainMenu {
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.id == 1) {
			mc.displayGuiScreen(new GuiSelectWorld(this));
		} else {
			super.actionPerformed(button);
		}
	}
}
