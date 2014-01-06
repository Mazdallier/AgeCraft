package elcon.mods.agecraft.core.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.Stone;
import elcon.mods.agecraft.core.blocks.stone.BlockColoredStoneBrick;

public class ItemHammer extends ItemTool {

	public ItemHammer(int id) {
		super(id);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		int blockID = world.getBlockId(x, y, z);
		int blockMetadata = world.getBlockMetadata(x, y, z);
		System.out.println(blockID + " | " + blockMetadata);
		if(blockID == Stone.stone.blockID && blockMetadata == 0) {
			if(!world.isRemote) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 3);
			}
			return true;
		} else if(blockID == Stone.stoneBrick.blockID && blockMetadata == 0) {
			if(!world.isRemote) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 3);
			}
			return true;
		} else if(blockID == Stone.coloredStone.blockID) {
			if(!world.isRemote) {
				world.setBlock(x, y, z, Stone.coloredStoneCracked.blockID, world.getBlockMetadata(x, y, z), 3);
			}
			return true;
		}
		if(blockID == Stone.coloredStoneBrick.blockID && (((BlockColoredStoneBrick) Stone.coloredStoneBrick).getMetadata(world, x, y, z) & 1) == 0) {
			if(!world.isRemote) {
				BlockColoredStoneBrick block = ((BlockColoredStoneBrick) Stone.coloredStoneBrick);
				block.setMetadata(world, x, y, z, block.getMetadata(world, x, y, z) | 1);
				world.markBlockForUpdate(x, y, z);
			}
			return true;
		}
		return false;
	}
}
