package org.agecraft.core.blocks.building;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.Building;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockClay extends Block {

	private IIcon icon;
	
	public BlockClay() {
		super(Material.clay);
		setHardness(0.6F);
		setStepSound(Block.soundTypeGravel);
		setCreativeTab(ACCreativeTabs.building);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "building.clay";
	}
	
	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return Building.clayBall;
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 4;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:building/clay");
	}
}
