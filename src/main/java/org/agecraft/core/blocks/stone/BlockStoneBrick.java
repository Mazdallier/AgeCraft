package org.agecraft.core.blocks.stone;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.ACCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;
import elcon.mods.elconqore.blocks.BlockMetadata;

public class BlockStoneBrick extends BlockMetadata {

	public static final String[] types = new String[]{"normal", "cracked", "mossy", "small", "circle", "creeper", "chiseled", "smooth"};
	
	private IIcon icons[] = new IIcon[8];
	private IIcon iconChiseledTop;
	
	public BlockStoneBrick() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "stone.stoneBrick." + types[stack.getItemDamage()];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if((side == 0 || side == 1) && meta == 6) {
			return iconChiseledTop;
		}
		return icons[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < types.length; i++) {
			icons[i] = iconRegister.registerIcon("agecraft:stone/stoneBrick" + EQUtil.firstUpperCase(types[i]));
		}
		iconChiseledTop = iconRegister.registerIcon("agecraft:stone/stoneBrickChiseledTop");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < types.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
