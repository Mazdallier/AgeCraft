package org.agecraft.core.player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.WorldServer;

import com.mojang.authlib.GameProfile;

import elcon.mods.elconqore.player.PlayerCoreServer;

public class ACPlayerServer extends PlayerCoreServer {

	public ACPlayerServer(MinecraftServer mcServer, WorldServer worldServer, GameProfile gameProfile, ItemInWorldManager itemInWorldManager, int playerCoreIndex, PlayerCoreServer entityPlayerMP) {
		super(mcServer, worldServer, gameProfile, itemInWorldManager, playerCoreIndex, entityPlayerMP);
	}
}
