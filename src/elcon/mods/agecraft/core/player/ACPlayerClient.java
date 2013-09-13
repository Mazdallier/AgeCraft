package elcon.mods.agecraft.core.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.player.PlayerCoreClient;

@SideOnly(Side.CLIENT)
public class ACPlayerClient extends PlayerCoreClient {

	public ACPlayerClient(Minecraft mc, World world, Session session, NetClientHandler netClientHandler, int playerCoreIndex, PlayerCoreClient entityPlayerSP) {
		super(mc, world, session, netClientHandler, playerCoreIndex, entityPlayerSP);
	}
}
