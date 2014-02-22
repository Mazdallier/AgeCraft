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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;
import elcon.mods.elconqore.blocks.BlockExtendedMetadataOverlay;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockColoredStoneBrick extends BlockExtendedMetadataOverlay {

	public static final String[] types = new String[]{"normal", "cracked", "mossy", "small", "circle", "creeper", "chiseled", "smooth"};
	
	private IIcon[] icons = new IIcon[8];
	private IIcon iconChiseledTop;
	private IIcon iconOverlayMossy;
	
	public BlockColoredStoneBrick() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return String.format(super.getLocalizedName(stack), LanguageManager.getLocalization("stone.types.stone"));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "stone.stoneBrick." + types[stack.getItemDamage() & 7];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return BlockColoredStone.colors[(meta & 120) / 8];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return BlockColoredStone.colors[(getMetadata(blockAccess, x, y, z) & 120) / 8];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (meta & 7) == 6 && (side == 0 || side == 1) ? iconChiseledTop : icons[meta & 7];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getBlockOverlayTexture(int side, int meta) {
		if((meta & 7) == 2) {
			return iconOverlayMossy;
		}
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 16; j++) {
				icons[i] = iconRegister.registerIcon("agecraft:stone/coloredStoneBrick" + EQUtil.firstUpperCase(types[i]));
			}
		}
		iconChiseledTop = iconRegister.registerIcon("agecraft:stone/coloredStoneBrickChiseledTop");
		iconOverlayMossy = iconRegister.registerIcon("agecraft:stone/coloredStoneBrickMossyOverlay");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 16; j++) {
				list.add(new ItemStack(item, 1, i + j * 8));
			}
		}
	}
}
