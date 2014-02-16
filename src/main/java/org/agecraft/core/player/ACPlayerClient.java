package org.agecraft.core.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import elcon.mods.elconqore.player.PlayerCoreClient;

public class ACPlayerClient extends PlayerCoreClient {

	public ACPlayerClient(Minecraft mc, World world, Session session, NetHandlerPlayClient netHandlerClient, StatFileWriter statFileWriter, int playerCoreIndex, PlayerCoreClient entityPlayerSP) {
		super(mc, world, session, netHandlerClient, statFileWriter, playerCoreIndex, entityPlayerSP);
	}
}
