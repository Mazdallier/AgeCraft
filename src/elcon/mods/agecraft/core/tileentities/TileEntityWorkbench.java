package elcon.mods.agecraft.core.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import elcon.mods.core.lang.LanguageManager;

public class TileEntityWorkbench extends TileEntityMetadata implements IInventory {

	public Container container;
	private ItemStack[] stacks;

	public TileEntityWorkbench() {
		stacks = new ItemStack[2];
	}

	public TileEntityWorkbench(Container container) {
		this();
		this.container = container;
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
				if(container != null) {
					container.onCraftMatrixChanged(this);
				}
				return stack;
			} else {
				stack = stacks[slot].splitStack(amount);
				if(stacks[slot].stackSize == 0) {
					stacks[slot] = null;
				}
				if(container != null) {
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
			if(container != null) {
				container.onCraftMatrixChanged(this);
			}
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

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("Inventory")) {
			NBTTagList list = nbt.getTagList("Inventory");
			for(int i = 0; i < list.tagCount(); ++i) {
				NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
				int slot = tag.getByte("Slot") & 255;
				ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
				if(stack != null && slot >= 0 && slot < getSizeInventory()) {
					stacks[slot] = stack;
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++) {
			if(stacks[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stacks[i].writeToNBT(tag);
				list.appendTag(tag);
			}
		}
		nbt.setTag("Inventory", list);
	}
}
