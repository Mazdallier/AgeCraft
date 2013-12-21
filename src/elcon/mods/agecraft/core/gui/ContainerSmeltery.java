package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import elcon.mods.agecraft.core.tileentities.TileEntitySmelteryFurnace;
import elcon.mods.core.gui.ContainerBasic;

public class ContainerSmeltery extends ContainerBasic {

	private TileEntitySmelteryFurnace tile;
	
	public ContainerSmeltery(EntityPlayer player, TileEntitySmelteryFurnace tile) {
		this.tile = tile;
		addInventoryPlayer(player.inventory, 7, 101);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(tile.xCoord + 0.5D, tile.yCoord + 0.5D, tile.zCoord + 0.5D) <= 64.0D;
	}
}
