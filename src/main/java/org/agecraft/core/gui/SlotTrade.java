package org.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTrade extends Slot {

	public boolean preventChange = false;
	public boolean canAccess;
	
	public SlotTrade(IInventory inventory, int id, int x, int y, boolean canAccess) {
		super(inventory, id, x, y);
		this.canAccess = canAccess;
	}
	
	@Override
	public void onSlotChanged() {
		if(canAccess && !preventChange) {
			super.onSlotChanged();
		}
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return canAccess && super.canTakeStack(player);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return canAccess && super.isItemValid(stack);
	}
}
