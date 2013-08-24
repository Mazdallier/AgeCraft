package elcon.mods.agecraft.creativetabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.lang.LanguageManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabAge extends CreativeTabs {

	public ItemStack stack;
	
	public CreativeTabAge(int id, String name, ItemStack item) {
		super(id, name);
		stack = item;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return LanguageManager.getLocalization("itemGroup." + getTabLabel());
	}

	@Override
	public ItemStack getIconItemStack() {
		return stack;
	}
}
