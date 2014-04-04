package org.agecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.core.gui.ContainerAnvil;
import org.agecraft.core.gui.ContainerWorkbench;
import org.agecraft.core.tileentities.TileEntityAnvil;
import org.agecraft.core.tileentities.TileEntityWorkbench;
import org.agecraft.prehistory.gui.ContainerSharpener;
import org.agecraft.prehistory.gui.InventorySharpener;

import cpw.mods.fml.common.network.IGuiHandler;

public class ACCommonProxy implements IGuiHandler {

	public void registerRenderingInformation() {

	}
	
	public void postInit() {
		
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == 11) {
			return new ContainerWorkbench(player, (TileEntityWorkbench) world.getTileEntity(x, y, z));
		} else if(id == 12) {
			//TODO: smeltery container
		} else if(id == 13) {
			return new ContainerAnvil(player, (TileEntityAnvil) world.getTileEntity(x, y, z), false);
		} else if(id == 14) {
			return new ContainerAnvil(player, (TileEntityAnvil) world.getTileEntity(x, y, z), true);
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
