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
import elcon.mods.core.blocks.BlockExtendedMetadata;
import elcon.mods.core.lang.LanguageManager;

public class BlockWoodWall extends BlockExtendedMetadata {

	public BlockWoodWall(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.trees[stack.getItemDamage()].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
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
		int id = blockAccess.getBlockId(x, y, z);
		if(id == blockID) {
			return meta == getMetadata(blockAccess, x, y, z);
		}
		Block block = Block.blocksList[id];
		return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ? block.blockMaterial != Material.pumpkin : false;
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
	public Icon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? TreeRegistry.trees[meta].woodTop : TreeRegistry.trees[meta].wood;
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
