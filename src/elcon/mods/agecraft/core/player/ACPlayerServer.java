package elcon.mods.agecraft.core.player;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import elcon.mods.core.player.PlayerCoreServer;

public class ACPlayerServer extends PlayerCoreServer {
	
	public ACPlayerServer(MinecraftServer mcServer, World world, String username, ItemInWorldManager itemInWorldManager, int playerCoreIndex, PlayerCoreServer entityPlayerMP) {
		super(mcServer, world, username, itemInWorldManager, playerCoreIndex, entityPlayerMP);
		player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setAttribute(100.0D);
		//player.inventory = new InventoryPlayer(player);
		//player.inventoryContainer = new ContainerInventory((InventoryPlayer) player.inventory, !player.worldObj.isRemote, player);
	}
}
