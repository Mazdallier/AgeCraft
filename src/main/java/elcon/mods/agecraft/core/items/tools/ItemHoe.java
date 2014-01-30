package elcon.mods.agecraft.core.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHoe extends ItemTool {

	public ItemHoe(int id) {
		super(id);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		if(!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else {
			int blockID = world.getBlockId(x, y, z);
			boolean air = world.isAirBlock(x, y + 1, z);
			if(side != 0 && air && (blockID == Block.grass.blockID || blockID == Block.dirt.blockID)) {
				//TODO: change farmland to AgeCraft farmland
				Block block = Block.tilledField;
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				if(world.isRemote) {
					return true;
				} else {
					world.setBlock(x, y, z, block.blockID);
					stack.damageItem(1, player);
					return true;
				}
			} else {
				return false;
			}
		}
	}
}
