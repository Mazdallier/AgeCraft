package elcon.mods.agecraft.core.blocks.tree;

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

public class BlockLog extends BlockExtendedMetadata {

	public BlockLog(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.wood);
		
		setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.75F, 0.6875F);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		float minX = 0.3125F;
		float minY = 0.0F;
		float minZ = 0.3125F;
		float maxX = 0.6875F;
		float maxY = 0.75F;
		float maxZ = 0.6875F;
		
		int meta = getMetadata(blockAccess, x, y, z);
		if(canConnectTo(blockAccess, x - 1, y, z, meta, false)) {
			minX = 0.0F;
		}
		if(canConnectTo(blockAccess, x + 1, y, z, meta, false)) {
			maxX = 1.0F;
		}
		if(canConnectTo(blockAccess, x, y - 1, z, meta, false)) {
			minY = 0.0F;
		}
		if(canConnectTo(blockAccess, x, y + 1, z, meta, true)) {
			maxY = 1.0F;
		}
		if(canConnectTo(blockAccess, x, y, z - 1, meta, false)) {
			minZ = 0.0F;
		}
		if(canConnectTo(blockAccess, x, y, z + 1, meta, false)) {
			maxZ = 1.0F;
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.75F, 0.6875F);
	}
	
	public boolean canConnectTo(IBlockAccess blockAccess, int x, int y, int z, int meta, boolean leaves) {
		if(leaves && blockAccess.getBlockId(x, y, z) == Trees.leaves.blockID) {
			return true;
		} else if(blockAccess.getBlockId(x, y, z) == blockID) {
			return meta == getMetadata(blockAccess, x, y, z);
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		super.breakBlock(world, x, y, z, id, meta);
		byte size = 4;
		int range = size + 1;
		if(world.checkChunksExist(x - range, y - range, z - range, x + range, y + range, z + range)) {
			for(int i = -size; i <= size; ++i) {
				for(int j = -size; j <= size; ++j) {
					for(int k = -size; k <= size; ++k) {
						int blockID = world.getBlockId(x + i, y + j, z + k);
						if(Block.blocksList[blockID] != null) {
							Block.blocksList[blockID].beginLeavesDecay(world, x + i, y + j, z + k);
						}
					}
				}
			}
		}
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta;
	}
	
	@Override
	public boolean canSustainLeaves(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean isWood(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 106;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? TreeRegistry.trees[meta].logTop : TreeRegistry.trees[meta].wood;
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
