package org.agecraft.prehistory.gui;

import org.agecraft.prehistory.PrehistoryAge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventorySharpener implements IInventory {

	public ContainerSharpener container;
	
	public ItemStack input;
	public ItemStack output;
	
	public void setContainer(ContainerSharpener c) {
		container = c;
	}

	@Override
	public int getSizeInventory() {
		return 66;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if(i == 0) {
			return input;
		} else if(i == 1) {
			return output;
		} else if(i < 66) {
			return new ItemStack(PrehistoryAge.fakeStone);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(i == 0 && input != null) {
			ItemStack stack;

			if(input.stackSize <= j) {
				stack = input;
				input = null;
				return stack;
			} else {
				stack = input.splitStack(j);

				if(input.stackSize == 0) {
					input = null;
				}
				if(container != null) {
					container.onCraftMatrixChanged(this);
				}
				return stack;
			}
		} else if(i == 1 && output != null) {
			ItemStack stack;

			if(output.stackSize <= j) {
				stack = output;
				output = null;
				return stack;
			} else {
				stack = output.splitStack(j);

				if(output.stackSize == 0) {
					output = null;
				}
				if(container != null) {
					container.onCraftMatrixChanged(this);
				}
				return stack;
			}
		} else if(i < 66) {
			return null;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack ret = null;
		if(i == 0) {
			ret = input;
			input = null;
		} else if(i == 1) {
			ret = output;
			output = null;
		} else if(i < 66) {
			return new ItemStack(PrehistoryAge.fakeStone);
		}
		return ret;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		if(i == 0) {
			input = stack;
		} else if(i == 1) {
			output = stack;
		}
		//if(container != null) {
		//	if(container.initDone) {
		//		container.onCraftMatrixChanged(this);
		//	}
		//}
	}

	@Override
	public String getInvName() {
		return "agecraft.gui.sharpener";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onInventoryChanged() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
}
