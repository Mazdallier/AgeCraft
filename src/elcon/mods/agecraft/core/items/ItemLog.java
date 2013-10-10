package elcon.mods.agecraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.prehistory.PrehistoryAge;

public class ItemLog extends ItemBlockExtendedMetadata {

	public ItemLog(int id) {
		super(id);
	}
	
	public int getPlaceBlockID() {
		return PrehistoryAge.campfire.blockID;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		int id = world.getBlockId(x, y, z);
		System.out.println(id);
		if(id == getPlaceBlockID()) {
			return false;
		} else {
			if(id == Block.snow.blockID && (world.getBlockMetadata(x, y, z) & 7) < 1) {
				side = 1;
			} else if(id != Block.vine.blockID && id != Block.tallGrass.blockID && id != Block.deadBush.blockID && (Block.blocksList[id] == null || !Block.blocksList[id].isBlockReplaceable(world, x, y, z))) {
				if(side == 0) {
					y--;
				}
				if(side == 1) {
					y++;
				}
				if(side == 2) {
					z--;
				}
				if(side == 3) {
					z++;
				}
				if(side == 4) {
					x--;
				}
				if(side == 5) {
					x++;
				}
			}
			if(stack.stackSize == 0) {
				return false;
			} else if(!player.canPlayerEdit(x, y, z, side, stack)) {
				return false;
			} else if(y == 255 && Block.blocksList[getPlaceBlockID()].blockMaterial.isSolid()) {
				return false;
			} else if(world.canPlaceEntityOnSide(getPlaceBlockID(), x, y, z, false, side, player, stack)) {
				Block block = Block.blocksList[getPlaceBlockID()];
				int meta = getMetadata(stack.getItemDamage());
				int placedMeta = Block.blocksList[getPlaceBlockID()].onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
				if(placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, placedMeta)) {
					world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
					stack.stackSize--;
				}
				return true;
			} else {
				return false;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canPlaceItemBlockOnSide(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
		int id = world.getBlockId(x, y, z);
		if(id == Block.snow.blockID) {
			side = 1;
		} else if(id != Block.vine.blockID && id != Block.tallGrass.blockID && id != Block.deadBush.blockID && (Block.blocksList[id] == null || !Block.blocksList[id].isBlockReplaceable(world, x, y, z))) {
			if(side == 0) {
				y--;
			}
			if(side == 1) {
				y++;
			}
			if(side == 2) {
				z--;
			}
			if(side == 3) {
				z++;
			}
			if(side == 4) {
				x--;
			}
			if(side == 5) {
				x++;
			}
		}
		return world.canPlaceEntityOnSide(getPlaceBlockID(), x, y, z, false, side, (Entity) null, stack);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(!world.setBlock(x, y, z, getPlaceBlockID(), metadata, 3)) {
			return false;
		}
		if(world.getBlockId(x, y, z) == getPlaceBlockID()) {
			Block.blocksList[getPlaceBlockID()].onBlockPlacedBy(world, x, y, z, player, stack);
			Block.blocksList[getPlaceBlockID()].onPostBlockPlaced(world, x, y, z, metadata);
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return TreeRegistry.trees[meta].log;
	}
}
