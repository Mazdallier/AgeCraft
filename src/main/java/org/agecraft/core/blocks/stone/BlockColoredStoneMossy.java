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
import org.agecraft.core.Stone;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockMetadataOverlay;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockColoredStoneMossy extends BlockMetadataOverlay {

	private IIcon iconOverlay;
	
	public BlockColoredStoneMossy() {
		super(Material.rock);
		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("color." + ACUtil.getColorName(stack.getItemDamage())) + " " + String.format(super.getLocalizedName(stack), LanguageManager.getLocalization("stone.types.stone"));
	}
	
	@Override
	public String getUnlocalizedName() {
		return "stone.stoneMossy";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return BlockColoredStone.colors[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return BlockColoredStone.colors[blockAccess.getBlockMetadata(x, y, z)];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Stone.coloredStoneCracked.getIcon(side, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getBlockOverlayTexture(int side, int metadata) {
		return iconOverlay;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		iconOverlay = iconRegister.registerIcon("agecraft:stone/coloredStoneMossy");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
