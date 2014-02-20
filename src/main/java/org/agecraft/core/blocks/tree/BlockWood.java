package org.agecraft.core.blocks.tree;

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
import org.agecraft.core.registry.TreeRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockWood extends BlockExtendedMetadata {

	public BlockWood() {
		super(Material.wood);
		setHardness(2.0F);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.instance.get((stack.getItemDamage() - (stack.getItemDamage() & 3)) / 4).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "trees.wood";
	}
	
	@Override
	public int getRenderType() {
		return 105;
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta - (meta & 3);
	}
	
	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		switch(side) {
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
		return stack.getItemDamage();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		super.breakBlock(world, x, y, z, block, meta);
		byte size = 4;
		int range = size + 1;
		if(world.checkChunksExist(x - range, y - range, z - range, x + range, y + range, z + range)) {
			for(int i = -size; i <= size; ++i) {
				for(int j = -size; j <= size; ++j) {
					for(int k = -size; k <= size; ++k) {
						Block otherBlock = world.getBlock(x + i, y + j, z + k);
						if(otherBlock != null) {
							otherBlock.beginLeavesDecay(world, x + i, y + j, z + k);
						}
					}
				}
			}
		}
	}
	
	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(this, 1, (meta - (meta & 3)) / 4);
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess blockAccess, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess blockAccess, int x, int y, int z) {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		int direction = meta & 3;
		int type = (meta - direction) / 4;
		if(direction == 0) {
			return side == 0 || side == 1 ? TreeRegistry.instance.get(type).woodTop : TreeRegistry.instance.get(type).wood;
		} else if(direction == 1) {
			return side == 2 || side == 3 ? TreeRegistry.instance.get(type).woodTop : TreeRegistry.instance.get(type).wood;
		} else if(direction == 2) {
			return side == 4 || side == 5 ? TreeRegistry.instance.get(type).woodTop : TreeRegistry.instance.get(type).wood;
		}
		return TreeRegistry.instance.get(type).wood;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			if(TreeRegistry.instance.get(i) != null) {
				list.add(new ItemStack(item, 1, i * 4));
			}
		}
	}
}
