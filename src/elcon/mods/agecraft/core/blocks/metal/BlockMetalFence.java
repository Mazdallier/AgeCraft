package elcon.mods.agecraft.core.blocks.metal;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.blocks.BlockExtendedMetadata;

public class BlockMetalFence extends BlockExtendedMetadata {

	public BlockMetalFence(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.wood);
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
		int id = blockAccess.getBlockId(x, y, z);
		if(id == blockID) {
			return meta == getMetadata(blockAccess, x, y, z);
		} else if(id == Trees.fenceGate.blockID) {
			int m = getMetadata(blockAccess, x, y, z);
			return meta == ((m - (m & 7)) / 8);
		}
		Block block = Block.blocksList[id];
		return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ? block.blockMaterial != Material.pumpkin : false;
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
	public Icon getIcon(int side, int meta) {
		return TreeRegistry.trees[meta].planks;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
