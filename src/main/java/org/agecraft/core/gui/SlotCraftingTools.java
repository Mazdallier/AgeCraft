package org.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotCraftingTools extends SlotCrafting {

	public IContainerTools container;
	
	public SlotCraftingTools(EntityPlayer player, IContainerTools container, InventoryCraftMatrix craftMatrix, IInventory inventory, int id, int x, int y) {
		super(player, craftMatrix, inventory, id, x, y);
		this.container = container;
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer currentPlayer, ItemStack stack) {
		super.onPickupFromSlot(currentPlayer, stack);
		container.damageTools();
	}
}
