package org.agecraft.prehistory.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotStoneOutput extends Slot {

	public SlotStoneOutput(IInventory inv, int par2, int par3, int par4) {
		super(inv, par2, par3, par4);
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		player.closeScreen();
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
}
