package org.agecraft.core.blocks.building;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.Building;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockMetadata;

public class BlockDirt extends BlockMetadata {

	public static String[] types = new String[]{"dirt", "infertileDirt", "podzol"};
	private IIcon[] icons = new IIcon[types.length];
	private IIcon iconPodzolTop;

	public BlockDirt() {
		super(Material.ground);
		setHardness(0.5F);
		setStepSound(Block.soundTypeGravel);
		setCreativeTab(ACCreativeTabs.building);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "building." + types[stack.getItemDamage()];
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(meta == 2) {
			return side == 1 ? iconPodzolTop : (side == 0 ? icons[0] : icons[meta]);
		}
		return icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int meta = blockAccess.getBlockMetadata(x, y, z);
		if(meta == 2) {
			if(side == 1) {
				return iconPodzolTop;
			}
			if(side != 0) {
				Material material = blockAccess.getBlock(x, y + 1, z).getMaterial();
				if(material == Material.snow || material == Material.craftedSnow) {
					return Building.grass.getIcon(blockAccess, x, y, z, side);
				}
				Block block = blockAccess.getBlock(x, y + 1, z);
				if(block != Building.dirt && block != Building.grass) {
					return icons[2];
				}
			}
		}
		return side == 0 ? icons[0] : icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < types.length; i++) {
			icons[i] = iconRegister.registerIcon("agecraft:building/" + types[i]);
		}
		iconPodzolTop = iconRegister.registerIcon("agecraft:building/podzolTop");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < types.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
