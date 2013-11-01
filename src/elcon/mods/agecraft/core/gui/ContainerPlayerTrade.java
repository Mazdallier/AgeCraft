package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.core.PlayerTradeManager.PlayerTrade;

public class ContainerPlayerTrade extends ContainerBasic {

	public PlayerTrade trade;
	
	public InventoryPlayerTrade inventoryPlayer1;
	public InventoryPlayerTrade inventoryPlayer2;

	public ContainerPlayerTrade otherContainer;

	public ContainerPlayerTrade(InventoryPlayer inventoryPlayer, PlayerTrade trade) {
		this.trade = trade;

		inventoryPlayer1 = new InventoryPlayerTrade(this, true);
		inventoryPlayer2 = new InventoryPlayerTrade(this, false);

		for(int j = 0; j < 3; j++) {
			for(int i = 0; i < 3; i++) {
				addSlotToContainer(new SlotTrade(inventoryPlayer1, i + j * 3, 8 + i * 18, 18 + j * 18, true));
				addSlotToContainer(new SlotTrade(inventoryPlayer2, i + j * 3, 116 + i * 18, 18 + j * 18, false));
			}
		}
		addInventoryPlayer(inventoryPlayer, 8, 86);
	}
	
	@Override
	public void putStackInSlot(int slotID, ItemStack stack) {
		super.putStackInSlot(slotID, stack);
		if(otherContainer != null && slotID < 9) {
			System.out.println(slotID + " " + stack);
			otherContainer.putStackInSlot(9 + slotID, stack);
			otherContainer.detectAndSendChanges();
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotID);
		if(slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if(slotID >= 0 && slotID < 17) {
				if(!mergeItemStack(stackInSlot, 18, 54, false)) {
					return null;
				}
			} else if(slotID >= 18 && slotID < 45) {
				if(!mergeItemStack(stackInSlot, 45, 54, false)) {
					return null;
				}
			} else if(slotID >= 45 && slotID < 54) {
				if(!mergeItemStack(stackInSlot, 18, 45, false)) {
					return null;
				}
			} else if(!mergeItemStack(stackInSlot, 18, 54, false)) {
				return null;
			}
			if(stackInSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if(stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
