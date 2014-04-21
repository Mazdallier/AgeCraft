package org.agecraft.core.blocks.building;

import org.agecraft.ACCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockHardenedClay extends Block {

	private IIcon icon;
	
	public BlockHardenedClay() {
		super(Material.rock);
		setHardness(1.25F);
		setResistance(7.0F);
		setStepSound(Block.soundTypePiston);
		setCreativeTab(ACCreativeTabs.building);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "building.hardenedClay";
	}
	
	@Override
	public MapColor getMapColor(int meta) {
		return MapColor.adobeColor;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:building/hardenedClay");
	}
}
