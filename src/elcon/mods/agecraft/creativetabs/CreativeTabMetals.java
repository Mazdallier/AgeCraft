package elcon.mods.agecraft.creativetabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.core.Metals;
import elcon.mods.agecraft.lang.LanguageManager;

public class CreativeTabMetals extends CreativeTabs {

	public CreativeTabMetals(int id, String name) {
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return LanguageManager.getLocalization("itemGroup." + getTabLabel());
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Metals.ore.blockID, 1, 0);
	}
}
