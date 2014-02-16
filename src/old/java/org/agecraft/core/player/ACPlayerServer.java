package org.agecraft.core.player;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import elcon.mods.core.player.PlayerCoreServer;

public class ACPlayerServer extends PlayerCoreServer {

	public ACPlayerServer(MinecraftServer mcServer, World world, String username, ItemInWorldManager itemInWorldManager, int playerCoreIndex, PlayerCoreServer entityPlayerMP) {
		super(mcServer, world, username, itemInWorldManager, playerCoreIndex, entityPlayerMP);
		player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setAttribute(100.0D);
		player.setHealth(100.0F);
		//player.inventoryContainer = new ContainerInventory(player.inventory, !world.isRemote, player);
		// player.dimension = 10;
		// ChunkCoordinates spawn = world.getSpawnPoint();
		// player.posX = spawn.posX;
		// player.posZ = spawn.posZ;
		// player.posY = getFirstUncoveredBlock(world, (int) player.posX, (int) player.posZ) + 1;
	}

	public int getFirstUncoveredBlock(World world, int x, int z) {
		int y;
		for(y = 63; !(world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z)); y++) {
		}
		return y;
	}
}
