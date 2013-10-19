package elcon.mods.agecraft.core.gui;

import elcon.mods.core.lang.LanguageManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryWorkbench implements IInventory {

	private Container container;
	private ItemStack[] stacks;
	
	public InventoryWorkbench(Container container) {
		this.container = container;
		stacks = new ItemStack[2];
	}
	
	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot >= getSizeInventory() ? null : stacks[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(slot < getSizeInventory() && stacks[slot] != null) {
			ItemStack oldStack = stacks[slot];
			stacks[slot] = null;
			return oldStack;
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(slot < getSizeInventory() && stacks[slot] != null) {
			ItemStack stack;
			if(stacks[slot].stackSize <= amount) {
				stack = stacks[slot];
				stacks[slot] = null;
				container.onCraftMatrixChanged(this);
				return stack;
			} else {
				stack = stacks[slot].splitStack(amount);
				if(stacks[slot].stackSize == 0) {
					stacks[slot] = null;
				}
				container.onCraftMatrixChanged(this);
				return stack;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot < getSizeInventory()) {
			stacks[slot] = stack;
			container.onCraftMatrixChanged(this);
		}
	}

	@Override
	public String getInvName() {
		return LanguageManager.getLocalization("container.workbench");
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	
	@Override
	public void onInventoryChanged() {
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
}
