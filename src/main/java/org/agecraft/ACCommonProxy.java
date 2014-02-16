package org.agecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.core.player.ACPlayerServer;

import cpw.mods.fml.common.network.IGuiHandler;
import elcon.mods.elconqore.player.PlayerAPI;
import elcon.mods.elconqore.player.PlayerAPI.PlayerCoreType;

public class ACCommonProxy implements IGuiHandler {
	
	public void registerPlayerAPI() {
		PlayerAPI.register(PlayerCoreType.SERVER, ACPlayerServer.class);
		AgeCraft.log.info("Registered PlayerAPI classes");
	}

	public void registerRenderingInformation() {

	}
	
	public void postInit() {
		
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
