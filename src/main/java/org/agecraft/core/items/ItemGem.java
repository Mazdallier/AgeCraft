package org.agecraft.core.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.MetalRegistry;
import org.agecraft.core.registry.MetalRegistry.OreType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemGem extends Item {

	public ItemGem() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(ACCreativeTabs.metals);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.instance.get(stack.getItemDamage()).name);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "metals.gem";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return MetalRegistry.instance.get(meta).ingot;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null) {
				if(MetalRegistry.instance.get(i).type == OreType.GEM && MetalRegistry.instance.get(i).hasIngot) {
					list.add(new ItemStack(item, 1, i));
				}
			}
		}
	}
}
