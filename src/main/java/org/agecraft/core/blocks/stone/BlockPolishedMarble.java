package org.agecraft.core.blocks.stone;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.agecraft.ACCreativeTabs;
import org.agecraft.ACUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockPolishedMarble extends BlockMetadata {
	
	private IIcon icon;
	
	public BlockPolishedMarble() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("color." + ACUtil.getColorName(stack.getItemDamage())) + " " + LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "stone.polishedMarble";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return BlockMarble.colors[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return BlockMarble.colors[blockAccess.getBlockMetadata(x, y, z)];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:stone/polishedMarble");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
