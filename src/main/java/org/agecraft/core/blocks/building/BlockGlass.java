package org.agecraft.core.blocks.building;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.agecraft.ACCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockMetadata;

public class BlockGlass extends BlockMetadata {

	public static String[] types = new String[]{"glass", "clearGlass"};
	private IIcon[] icons = new IIcon[types.length];

	public BlockGlass() {
		super(Material.glass);
		setHardness(0.3F);
		setStepSound(Block.soundTypeGlass);
		setCreativeTab(ACCreativeTabs.building);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "building." + types[stack.getItemDamage()];
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 0;
	}
	
	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		Block block = blockAccess.getBlock(x, y, z);
		if(blockAccess.getBlockMetadata(x, y, z) != blockAccess.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side])) {
			return true;
		}
		if(block == this) {
			return false;
		}
		return block == this ? false : super.shouldSideBeRendered(blockAccess, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < types.length; i++) {
			icons[i] = iconRegister.registerIcon("agecraft:building/" + types[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < types.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
