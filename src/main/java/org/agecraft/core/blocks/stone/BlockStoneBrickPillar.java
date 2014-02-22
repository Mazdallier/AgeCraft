package org.agecraft.core.blocks.stone;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.StoneTypeRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.blocks.IBlockRotated;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockStoneBrickPillar extends BlockExtendedMetadata implements IBlockRotated {

	public BlockStoneBrickPillar() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public int getBlockRotation(IBlockAccess blockAccess, int x, int y, int z) {
		return getMetadata(blockAccess, x, y, z) & 3;
	}
	
	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		switch(side) {
		default:
		case 0:
		case 1:
			return stack.getItemDamage();
		case 2:
		case 3:
			return stack.getItemDamage() | 1;
		case 4:
		case 5:
			return stack.getItemDamage() | 2;
		}
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta - (meta & 3);
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return String.format(super.getLocalizedName(stack), LanguageManager.getLocalization("stone.types." + StoneTypeRegistry.instance.get((stack.getItemDamage() - (stack.getItemDamage() & 3)) / 4)));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "stone.stoneBrick.pillar";
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 91;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if((meta & 3) == 0 && (side == 0 || side == 1)) {
			return StoneTypeRegistry.instance.get((meta - (meta & 3)) / 4).iconBrickPillarTop;
		} else if((meta & 3) == 1 && (side == 2 || side == 3)) {
			return StoneTypeRegistry.instance.get((meta - (meta & 3)) / 4).iconBrickPillarTop;
		} else if((meta & 3) == 2 && (side == 4 || side == 5)) {
			return StoneTypeRegistry.instance.get((meta - (meta & 3)) / 4).iconBrickPillarTop;
		}
		return StoneTypeRegistry.instance.get((meta - (meta & 3)) / 4).iconBrickPillar;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < StoneTypeRegistry.instance.getAll().length; i++) {
			if(StoneTypeRegistry.instance.get(i) != null) {
				list.add(new ItemStack(item, 1, i * 4));
			}
		}
	}
}
