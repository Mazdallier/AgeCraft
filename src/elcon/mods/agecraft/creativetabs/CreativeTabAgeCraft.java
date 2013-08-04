package elcon.mods.agecraft.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabAgeCraft extends CreativeTabs {

	public CreativeTabAgeCraft(int id) {
		super(id, "AgeCraft");
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Item.paper.itemID, 1, 0);
	}
}
