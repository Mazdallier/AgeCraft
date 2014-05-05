package org.agecraft.core.blocks;

import org.agecraft.ACCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockAgeTeleporterBlock extends Block {

	private IIcon icon;
	
	public BlockAgeTeleporterBlock() {
		super(Material.iron);
		setBlockUnbreakable();
		setResistance(6000000.0F);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(ACCreativeTabs.ageCraft);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.ageTeleporterBlock.name";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ageTeleporterBlock");
	}
}
