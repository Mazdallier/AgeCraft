package elcon.mods.agecraft.core.items.tools;

import net.minecraft.item.ItemStack;
import codechicken.microblock.Saw;

public class ItemSaw extends ItemTool implements Saw {

	public ItemSaw(int id) {
		super(id);
	}

	@Override
	public int getCuttingStrength(ItemStack stack) {
		return getToolHarvestLevel(stack);
	}
	
	@Override
	public int getMaxCuttingStrength() {
		return 10;
	}
}
