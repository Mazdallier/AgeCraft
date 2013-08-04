package elcon.mods.agecraft.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabAge extends CreativeTabs {

	public ItemStack stack;
	
	public CreativeTabAge(int id, String name, ItemStack item) {
		super(id, name);
		stack = item;
	}

	@Override
	public ItemStack getIconItemStack() {
		return stack;
	}
}
