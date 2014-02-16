package org.agecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.core.player.ACPlayerClient;
import org.agecraft.core.player.ACPlayerRender;
import org.agecraft.core.player.ACPlayerServer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.player.PlayerAPI;
import elcon.mods.elconqore.player.PlayerAPI.PlayerCoreType;

@SideOnly(Side.CLIENT)
public class ACClientProxy extends ACCommonProxy {

	@Override
	public void registerResources() {

	}
	
	@Override
	public void registerPlayerAPI() {
		PlayerAPI.register(PlayerCoreType.CLIENT, ACPlayerClient.class);
		PlayerAPI.register(PlayerCoreType.SERVER, ACPlayerServer.class);
		PlayerAPI.register(PlayerCoreType.RENDER, ACPlayerRender.class);
		AgeCraft.log.info("Registered PlayerAPI classes");
	}
	
	@Override
	public void registerRenderingInformation() {
		
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
