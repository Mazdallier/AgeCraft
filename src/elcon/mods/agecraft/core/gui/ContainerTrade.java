package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerTrade extends ContainerBasic {

	public ContainerTrade(InventoryPlayer inventoryPlayer) {
		addInventoryPlayer(inventoryPlayer, 8, 84);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
