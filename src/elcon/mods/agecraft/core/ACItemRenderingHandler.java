package elcon.mods.agecraft.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ACItemRenderingHandler implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type.ordinal()) {
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
	}
}
