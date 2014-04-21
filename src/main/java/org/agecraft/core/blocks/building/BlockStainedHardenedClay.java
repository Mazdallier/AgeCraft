package org.agecraft.core.blocks.building;

import java.util.List;

import org.agecraft.ACCreativeTabs;
import org.agecraft.ACUtil;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;
import elcon.mods.elconqore.blocks.BlockMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockStainedHardenedClay extends BlockMetadata {

	private IIcon[] icons = new IIcon[16];
	
	public BlockStainedHardenedClay() {
		super(Material.rock);
		setHardness(1.25F);
		setResistance(7.0F);
		setStepSound(Block.soundTypePiston);
		setCreativeTab(ACCreativeTabs.building);
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("color." + ACUtil.getColorName(stack.getItemDamage())) + " " + LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "building.stainedHardenedClay";
	}
	
	@Override
	public MapColor getMapColor(int meta) {
		return MapColor.getMapColorForBlockColored(meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < 16; i++) {
			icons[i] = iconRegister.registerIcon("agecraft:building/hardenedClay" + EQUtil.firstUpperCase(ACUtil.getColorName(i)));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
