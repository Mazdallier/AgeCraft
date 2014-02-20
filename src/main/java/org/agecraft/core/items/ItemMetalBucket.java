package org.agecraft.core.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import org.agecraft.core.registry.MetalRegistry;
import org.agecraft.core.registry.MetalRegistry.OreType;
import org.agecraft.core.registry.ToolRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemMetalBucket extends ItemBucket {

	public ItemMetalBucket() {
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.instance.get(stack.getItemDamage()).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "metals.bucket";
	}
	
	@Override
	public List<Fluid> getFluidBlacklist(ItemStack stack) {
		return new ArrayList<Fluid>();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		return MetalRegistry.instance.get(stack.getItemDamage()).bucket;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	}
	

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null && MetalRegistry.instance.get(i).type == OreType.METAL && MetalRegistry.instance.get(i).hasIngot && ToolRegistry.toolMaterials[128 + i] != null) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
