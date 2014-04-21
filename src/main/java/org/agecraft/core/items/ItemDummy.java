package org.agecraft.core.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemDummy extends Item {

	public String name;

	public ItemDummy(String name) {
		this.name = name;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return name;
	}
}
