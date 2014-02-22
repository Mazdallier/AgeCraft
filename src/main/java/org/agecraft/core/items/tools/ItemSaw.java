package org.agecraft.core.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSaw extends ItemTool {

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}
}
