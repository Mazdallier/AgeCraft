package elcon.mods.agecraft.core.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.TreeRegistry;

public class BlockWood extends BlockExtendedMetadata {

	public BlockWood(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	@Override
	public int getRenderType() {
		return 105;
	}
	
	@Override
	public int getPlacedMetadata(ItemStack stack, World world, int x, int y, int z, int side) {
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
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(blockID, 1, (meta - (meta & 3)) / 4);
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
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		int direction = meta & 3;
		int type = (meta - direction) / 4;
		if(direction == 0) {
			return side == 0 || side == 1 ? TreeRegistry.trees[type].woodTop : TreeRegistry.trees[type].wood;
		} else if(direction == 1) {
			return side == 2 || side == 3 ? TreeRegistry.trees[type].woodTop : TreeRegistry.trees[type].wood;
		} else if(direction == 2) {
			return side == 4 || side == 5 ? TreeRegistry.trees[type].woodTop : TreeRegistry.trees[type].wood;
		}
		return TreeRegistry.trees[type].wood;
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
				list.add(new ItemStack(id, 1, i * 4));
			}
		}
	}
}
