package org.agecraft.core.items.food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.registry.FoodRegistry.Food;
import org.agecraft.core.registry.FruitRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemFruit extends ItemFood {

	@Override
	public Food getFood(int meta) {
		return FruitRegistry.instance.get(meta);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "food.fruit." + getFood(stack).name;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return FruitRegistry.instance.get(meta).icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < FruitRegistry.instance.getAll().length; i++) {
			if(FruitRegistry.instance.get(i) != null) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
