package org.agecraft.core.blocks.metal;

import java.util.List;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.MetalRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.blocks.BlockFluidMetadata;
import elcon.mods.core.lang.LanguageManager;

public class BlockMetalFluid extends BlockFluidMetadata {

	public BlockMetalFluid(int id) {
		super(id, Material.lava);
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
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
