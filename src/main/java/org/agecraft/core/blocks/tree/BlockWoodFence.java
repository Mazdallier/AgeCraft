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
import org.agecraft.core.Trees;
import org.agecraft.core.registry.TreeRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockWoodFence extends BlockExtendedMetadata {

	public BlockWoodFence() {
		super(Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(ACCreativeTabs.wood);
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(stack.getItemDamage()).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "trees.fence";
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		int meta = getMetadata(world, x, y, z);
		boolean connectMinX = canConnectTo(world, x - 1, y, z, meta);
		boolean connectMaxX = canConnectTo(world, x + 1, y, z, meta);
		boolean connectMinZ = canConnectTo(world, x, y, z - 1, meta);
		boolean connectMaxZ = canConnectTo(world, x, y, z + 1, meta);
		float minX = 0.375F;
		float maxX = 0.625F;
		float minZ = 0.375F;
		float maxZ = 0.625F;

		if(connectMinZ) {
			minZ = 0.0F;
		}
		if(connectMaxZ) {
			maxZ = 1.0F;
		}
		if(connectMinZ || connectMaxZ) {
			setBlockBounds(minX, 0.0F, minZ, maxX, 1.5F, maxZ);
			super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
		}
		minZ = 0.375F;
		maxZ = 0.625F;
		if(connectMinX) {
			minX = 0.0F;
		}
		if(connectMaxX) {
			maxX = 1.0F;
		}
		if(connectMinX || connectMaxX || !connectMinZ && !connectMaxZ) {
			setBlockBounds(minX, 0.0F, minZ, maxX, 1.5F, maxZ);
			super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
		}
		if(connectMinZ) {
			minZ = 0.0F;
		}
		if(connectMaxZ) {
			maxZ = 1.0F;
		}
		setBlockBounds(minX, 0.0F, minZ, maxX, 1.0F, maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		boolean connectMinZ = canConnectTo(blockAccess, x, y, z - 1, meta);
		boolean connectMaxZ = canConnectTo(blockAccess, x, y, z + 1, meta);
		boolean connectMinX = canConnectTo(blockAccess, x - 1, y, z, meta);
		boolean connectMaxX = canConnectTo(blockAccess, x + 1, y, z, meta);
		float minX = 0.375F;
		float maxX = 0.625F;
		float minZ = 0.375F;
		float maxZ = 0.625F;

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
		setBlockBounds(minX, 0.0F, minZ, maxX, 1.0F, maxZ);
	}

	public boolean canConnectTo(IBlockAccess blockAccess, int x, int y, int z, int meta) {
		int id = Block.getIdFromBlock(blockAccess.getBlock(x, y, z));
		if(id == Block.getIdFromBlock(this)) {
			return meta == getMetadata(blockAccess, x, y, z);
		} else if(id == Block.getIdFromBlock(Trees.fenceGate)) {
			int m = getMetadata(blockAccess, x, y, z);
			return meta == ((m - (m & 7)) / 8);
		}
		Block block = blockAccess.getBlock(x, y, z);
		return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false;
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
	public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
		return false;
	}

	@Override
	public int getRenderType() {
		return 108;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return TreeRegistry.instance.get(meta).planks;
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
