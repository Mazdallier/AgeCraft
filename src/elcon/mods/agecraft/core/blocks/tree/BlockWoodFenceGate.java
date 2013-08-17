package elcon.mods.agecraft.core.blocks.tree;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.blocks.BlockExtendedMetadata;

public class BlockWoodFenceGate extends BlockExtendedMetadata {

	public BlockWoodFenceGate(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return !world.getBlockMaterial(x, y - 1, z).isSolid() ? false : super.canPlaceBlockAt(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = getMetadata(world, x, y, z);
		int direction = meta & 3;
		return isFenceGateOpen(meta) ? null : (direction != 2 && direction != 0 ? AxisAlignedBB.getAABBPool().getAABB((double)((float)x + 0.375F), (double)y, (double)z, (double)((float)x + 0.625F), (double)((float)y + 1.5F), (double)(z + 1)) : AxisAlignedBB.getAABBPool().getAABB((double)x, (double)y, (double)((float)z + 0.375F), (double)(x + 1), (double)((float)y + 1.5F), (double)((float)z + 0.625F)));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int direction = getMetadata(blockAccess, x, y, z) & 3;
		if(direction != 2 && direction != 0) {
			setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
		} else {
			setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
		}
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta - (meta & 7);
	}

	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
		return ((MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4) | stack.getItemDamage();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xx, float yy, float zz) {
		int meta = getMetadata(world, x, y, z);
		int direction = meta & 3;
		int type = (meta - (meta & 7)) / 8;
		if(isFenceGateOpen(meta)) {
			setMetadata(world, x, y, z, meta & -5);
			world.markBlockForUpdate(x, y, z);
		} else {
			int newDirection = (MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
			if(direction == (newDirection + 2) % 4) {
				meta = newDirection + (type * 8);
			}
			setMetadata(world, x, y, z, meta | 4);
			world.markBlockForUpdate(x, y, z);
		}
		world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
		if(!world.isRemote) {
			int meta = getMetadata(world, x, y, z);
			boolean indirectPower = world.isBlockIndirectlyGettingPowered(x, y, z);
			if(indirectPower || blockID > 0 && Block.blocksList[blockID].canProvidePower()) {
				if(indirectPower && !isFenceGateOpen(meta)) {
					setMetadata(world, x, y, z, meta | 4);
					world.markBlockForUpdate(x, y, z);
					world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
				} else if(!indirectPower && isFenceGateOpen(meta)) {
					setMetadata(world, x, y, z, meta & -5);
					world.markBlockForUpdate(x, y, z);
					world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
				}
			}
		}
	}

	public boolean isFenceGateOpen(int meta) {
		return (meta & 4) != 0;
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
		return isFenceGateOpen(getMetadata(blockAccess, x, y, z));
	}

	@Override
	public int getRenderType() {
		return 109;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return TreeRegistry.trees[(meta - (meta & 7)) / 8].planks;
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
				list.add(new ItemStack(id, 1, i * 8));
			}
		}
	}
}
