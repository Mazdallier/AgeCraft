package org.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import elcon.mods.elconqore.lang.LanguageManager;

public class InventoryCraftMatrix implements IInventory {

	private Container container;
	private ItemStack[] stacks;
	public int width;
	public int height;
	public String unlocalizedName;
	
	public InventoryCraftMatrix(Container container, int width, int height, String unlocalizedName) {
		this.container = container;
		this.stacks = new ItemStack[width * height];
		this.width = width;
		this.height = height;
		this.unlocalizedName = unlocalizedName;
	}
	
	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot >= getSizeInventory() ? null : stacks[slot];
	}
	
	public ItemStack getStackInRowAndColumn(int x, int y) {
		if(x >= 0 && x < width && y >= 0 && y < height) {
			return getStackInSlot(x + y * width);
		}
		return null;
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

	public void readFromNBT(NBTTagList list) {
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.getCompoundTagAt(i);
			int slot = tag.getInteger("Slot");
			ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
			if(stack != null && slot >= 0 && slot < getSizeInventory()) {
				stacks[slot] = stack;
			}
		}
	}
	
	public void writeToNBT(NBTTagList list) {
		for(int i = 0; i < stacks.length; i++) {
			if(stacks[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("SlotID", i);
				stacks[i].writeToNBT(tag);
				list.appendTag(tag);
			}
		}
	}
	
	@Override
	public String getInventoryName() {
		return LanguageManager.getLocalization(unlocalizedName);
	}

	@Override
	public boolean hasCustomInventoryName() {
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
	public void markDirty() {
	}
	
	@Override
	public void openInventory() {
	}
	
	@Override
	public void closeInventory() {
	}
}
