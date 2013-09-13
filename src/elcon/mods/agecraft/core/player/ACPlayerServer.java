package elcon.mods.agecraft.core.player;

import net.minecraft.item.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import elcon.mods.core.player.PlayerCoreServer;

public class ACPlayerServer extends PlayerCoreServer {

	public ACPlayerServer(MinecraftServer mcServer, World world, String username, ItemInWorldManager itemInWorldManager, int playerCoreIndex, PlayerCoreServer entityPlayerMP) {
		super(mcServer, world, username, itemInWorldManager, playerCoreIndex, entityPlayerMP);
	}
	
	public void onLivingUpdate() {
		super.onLivingUpdate();
		heal(1.0F);
	}
}
