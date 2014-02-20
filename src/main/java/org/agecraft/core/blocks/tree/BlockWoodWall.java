package org.agecraft.core.blocks.tree;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.TreeRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockWoodWall extends BlockExtendedMetadata {

	public BlockWoodWall() {
		super(Material.wood);
		setHardness(2.0F);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(stack.getItemDamage()).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "trees.wall";
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);
		maxY = 1.5D;
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		boolean connectMinX = canConnectTo(blockAccess, x - 1, y, z, meta);
		boolean connectMaxX = canConnectTo(blockAccess, x + 1, y, z, meta);
		boolean connectMinZ = canConnectTo(blockAccess, x, y, z - 1, meta);
		boolean connectMaxZ = canConnectTo(blockAccess, z, y, z + 1, meta);
		float minX = 0.25F;
		float maxX = 0.75F;
		float maxY = 1.0F;
		float minZ = 0.25F;
		float maxZ = 0.75F;

		if(connectMinX) {
			minX = 0.0F;
		}
		if(connectMaxX) {
			maxX = 1.0F;
		}
		if(connectMinZ) {
			minZ = 0.0F;
		}
		if(connectMaxZ) {
			maxZ = 1.0F;
		}
		if(connectMinZ && connectMaxZ && !connectMinX && !connectMaxX) {
			maxY = 0.8125F;
			minX = 0.3125F;
			maxX = 0.6875F;
		} else if(!connectMinZ && !connectMaxZ && connectMinX && connectMaxX) {
			maxY = 0.8125F;
			minZ = 0.3125F;
			maxZ = 0.6875F;
		}
		setBlockBounds(minX, 0.0F, minZ, maxX, maxY, maxZ);
	}

	public boolean canConnectTo(IBlockAccess blockAccess, int x, int y, int z, int meta) {
		Block block = blockAccess.getBlock(x, y, z);
		if(Block.getIdFromBlock(block) == Block.getIdFromBlock(this)) {
			return meta == getMetadata(blockAccess, x, y, z);
		}
		return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false;
	}
	
	@Override
	public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 107;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return side == 0 ? super.shouldSideBeRendered(blockAccess, x, y, z, side) : true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? TreeRegistry.instance.get(meta).woodTop : TreeRegistry.instance.get(meta).wood;
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
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
