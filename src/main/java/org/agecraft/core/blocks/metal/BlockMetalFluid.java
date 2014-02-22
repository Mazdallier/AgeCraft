package org.agecraft.core.blocks.metal;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.MetalRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockFluidMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockMetalFluid extends BlockFluidMetadata {

	public BlockMetalFluid() {
		super(Material.lava);
		setTickRate(30);
		setCreativeTab(ACCreativeTabs.metals);
	}

	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + getFluid(stack.getItemDamage()).getName()) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName() {
		return "metals.fluid";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
