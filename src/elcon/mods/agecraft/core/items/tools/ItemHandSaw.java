package elcon.mods.agecraft.core.items.tools;

import net.minecraft.item.ItemStack;
import codechicken.microblock.Saw;

public class ItemHandSaw extends ItemTool implements Saw {

	public ItemHandSaw(int id) {
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
