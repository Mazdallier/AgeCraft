package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryPlayerTrade extends InventoryBasic {

	public boolean preventChangeEvent = false;
	public ContainerPlayerTrade container;
	public boolean canAccess;
	
	public InventoryPlayerTrade(ContainerPlayerTrade container, boolean canAccess) {
		super(container, 9, "container.trade");
		this.container = container;
		this.canAccess = canAccess;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(slot < getSizeInventory() && stacks[slot] != null) {
			ItemStack stack;
			if(stacks[slot].stackSize <= amount) {
				stack = stacks[slot];
				stacks[slot] = null;
				if(canAccess && !preventChangeEvent) {
					container.onCraftMatrixChanged(this);
				}
				return stack;
			} else {
				stack = stacks[slot].splitStack(amount);
				if(stacks[slot].stackSize == 0) {
					stacks[slot] = null;
				}
				if(canAccess && !preventChangeEvent) {
					container.onCraftMatrixChanged(this);
				}
				return stack;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot < getSizeInventory()) {
			stacks[slot] = stack;
			if(canAccess && !preventChangeEvent) {
				container.onCraftMatrixChanged(this);
			}
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack stack) {
		return canAccess && super.isItemValidForSlot(slotID, stack);
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return canAccess && super.isUseableByPlayer(player);
	}
}
