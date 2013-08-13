package elcon.mods.agecraft.creativetabs;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabMetals extends CreativeTabs {

	public CreativeTabMetals(int id, String name) {
		super(id, name);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Block.oreCoal.blockID, 1, 0);
	}
}
