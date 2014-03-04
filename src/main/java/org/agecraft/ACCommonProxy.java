package org.agecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.core.gui.ContainerWorkbench;
import org.agecraft.core.player.ACPlayerServer;
import org.agecraft.core.tileentities.TileEntityWorkbench;
import org.agecraft.prehistory.gui.ContainerSharpener;
import org.agecraft.prehistory.gui.InventorySharpener;

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
		if(id == 11) {
			return new ContainerWorkbench(player, player.inventory, (TileEntityWorkbench) world.getTileEntity(x, y, z), world, x, y, z);
		} else if(id == 12) {
			//TODO: smeltery container
		} else if(id == 13) {
			//TODO: anvil container
		} else if(id == 30) {
			return new ContainerSharpener(player, new InventorySharpener());
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
