package elcon.mods.agecraft.core;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterChest;

public class AgeTeleport {

	public static HashMap<String, AgeTeleport> teleportList = new HashMap<String, AgeTeleport>();

	public TileEntityAgeTeleporterChest[] chests = new TileEntityAgeTeleporterChest[16];

	public AgeTeleport(TileEntityAgeTeleporterChest[] c) {
		chests = c;
	}

	public static AgeTeleport create(World world, int i, int j, int k) {
		TileEntityAgeTeleporterChest[] c = new TileEntityAgeTeleporterChest[16];

		c[0] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 2, j + 2, k + 5);
		c[1] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 2, j + 3, k + 5);
		c[2] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 4, j + 2, k + 5);
		c[3] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 4, j + 3, k + 5);
		c[4] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 2, j + 2, k + 1);
		c[5] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 2, j + 3, k + 1);
		c[6] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 4, j + 2, k + 1);
		c[7] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 4, j + 3, k + 1);

		c[8] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 5, j + 2, k + 2);
		c[9] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 5, j + 3, k + 2);
		c[10] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 5, j + 2, k + 4);
		c[11] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 5, j + 3, k + 4);
		c[12] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 1, j + 2, k + 2);
		c[13] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 1, j + 3, k + 2);
		c[14] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 1, j + 2, k + 4);
		c[15] = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(i + 1, j + 3, k + 4);

		return new AgeTeleport(c);
	}

	public void addItem(ItemStack stack) {
		for(int i = 0; i < chests.length; i++) {
			if(chests[i] != null) {
				ItemStack[] stacks = chests[i].chestContents;
				for(int j = 0; j < stacks.length; j++) {
					if(stacks[j] == null || stacks[j].stackSize == 0) {
						chests[i].setInventorySlotContents(j, stack);
					}
				}
			}
		}
	}

	public void placeChests(World world, int x, int y, int z) {
		System.out.println("placing chests at" + x + "," + y + "," + z);
		for(int i = 0; i < 11; i++) {
			for(int j = 0; j < 11; j++) {
				for(int k = 0; k < 11; k++) {
					int l = world.getBlockId(x + i, y - 5 + j, z + k);
					if((i < 5 && j < 5 && k < 5) || l == AgeCraftCore.ageTeleporterChest.blockID || l == Block.bedrock.blockID) {
						world.setBlock(x + i, y - 5 + j, z + k, 0);
					}
				}
			}
		}

		y = ACUtil.getFirstUncoveredBlock(world, x, z);

		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				world.setBlock(x + i, y, z + j, Block.bedrock.blockID);
			}
		}

		world.setBlock(x + 1, y + 1, z + 0, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 1, y + 1, z + 0, chests[0]);

		world.setBlock(x + 3, y + 1, z + 0, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 3, y + 1, z + 0, chests[1]);

		world.setBlock(x + 1, y + 1, z + 4, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 1, y + 1, z + 4, chests[2]);

		world.setBlock(x + 3, y + 1, z + 4, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 3, y + 1, z + 4, chests[3]);

		world.setBlock(x + 0, y + 1, z + 1, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 0, y + 1, z + 1, chests[4]);

		world.setBlock(x + 0, y + 1, z + 3, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 0, y + 1, z + 3, chests[5]);

		world.setBlock(x + 4, y + 1, z + 1, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 4, y + 1, z + 1, chests[6]);

		world.setBlock(x + 4, y + 1, z + 3, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 4, y + 1, z + 3, chests[7]);

		world.setBlock(x + 1, y + 2, z + 0, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 1, y + 2, z + 0, chests[8]);

		world.setBlock(x + 3, y + 2, z + 0, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 3, y + 2, z + 0, chests[9]);

		world.setBlock(x + 1, y + 2, z + 4, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 1, y + 2, z + 4, chests[10]);

		world.setBlock(x + 3, y + 2, z + 4, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 3, y + 2, z + 4, chests[11]);

		world.setBlock(x + 0, y + 2, z + 1, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 0, y + 2, z + 1, chests[12]);

		world.setBlock(x + 0, y + 2, z + 3, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 0, y + 2, z + 3, chests[13]);

		world.setBlock(x + 4, y + 2, z + 1, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 4, y + 2, z + 1, chests[14]);

		world.setBlock(x + 4, y + 2, z + 3, AgeCraftCore.ageTeleporterChest.blockID);
		world.setBlockTileEntity(x + 4, y + 2, z + 3, chests[15]);
	}
}
