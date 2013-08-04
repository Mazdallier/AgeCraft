package elcon.mods.agecraft.creativetabs;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabWood extends CreativeTabs {

	public CreativeTabWood(int id) {
		super(id, "Wood");
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Block.wood.blockID, 1, 0);
	}
}
