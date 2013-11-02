package elcon.mods.agecraft.core.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.player.PlayerCoreClient;

@SideOnly(Side.CLIENT)
public class ACPlayerClient extends PlayerCoreClient {

	public ACPlayerClient(Minecraft mc, World world, Session session, NetClientHandler netClientHandler, int playerCoreIndex, PlayerCoreClient entityPlayerSP) {
		super(mc, world, session, netClientHandler, playerCoreIndex, entityPlayerSP);
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
