package elcon.mods.agecraft.core.gui;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ReportedException;
import elcon.mods.agecraft.core.items.armor.ItemArmor;

public class InventoryPlayer extends net.minecraft.entity.player.InventoryPlayer implements IInventory {

	private ItemStack currentItemStack;
	private ItemStack itemStack;

	public InventoryPlayer(EntityPlayer player) {
		super(player);
		mainInventory = new ItemStack[26];
		armorInventory = new ItemStack[10];
	}

	@Override
	public int getSizeInventory() {
		return mainInventory.length + armorInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		ItemStack[] stacks = mainInventory;
		if(slot >= stacks.length) {
			slot -= stacks.length;
			stacks = armorInventory;
		}
		return stacks[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack[] stacks = mainInventory;
		if(slot >= stacks.length) {
			slot -= stacks.length;
			stacks = armorInventory;
		}
		if(stacks[slot] != null) {
			ItemStack oldStack = stacks[slot];
			stacks[slot] = null;
			return oldStack;
		}
		return null;
	}

	@Override
	public ItemStack getCurrentItem() {
		return currentItem >= 0 && currentItem < 9 ? mainInventory[currentItem] : null;
	}
	
	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}

	public int getInventorySlotContainItem(int id) {
		for(int i = 0; i < mainInventory.length; i++) {
			if(mainInventory[i] != null && mainInventory[i].itemID == id) {
				return i;
			}
		}
		return -1;
	}

	public int getInventorySlotContainItemAndDamage(int id, int damage) {
		for(int i = 0; i < mainInventory.length; i++) {
			if(mainInventory[i] != null && mainInventory[i].itemID == id && mainInventory[i].getItemDamage() == damage) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getFirstEmptyStack() {
		for(int i = 0; i < mainInventory.length; i++) {
			if(mainInventory[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public ItemStack getArmorInSlot(int slot) {
		return armorInventory[slot];
	}
	
	@Override
	public ItemStack armorItemInSlot(int slot) {
		return getArmorInSlot(slot);
	}

	@Override
	public int getTotalArmorValue() {
		int armorValue = 0;
		for(int i = 0; i < armorInventory.length; i++) {
			if(armorInventory[i] != null && armorInventory[i].getItem() instanceof ItemArmor) {
				armorValue += ((ItemArmor) armorInventory[i].getItem()).getDamageReduceAmount(armorInventory[i]);
			}
		}
		return armorValue;
	}

	@Override
	public boolean hasItemStack(ItemStack stack) {
		for(int i = 0; i < mainInventory.length; i++) {
			if(mainInventory[i] != null && mainInventory[i].isItemEqual(stack)) {
				return true;
			}
		}
		for(int i = 0; i < armorInventory.length; i++) {
			if(armorInventory[i] != null && armorInventory[i].isItemEqual(stack)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean hasItem(int id) {
		return getInventorySlotContainItem(id) >= 0;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		if(block.blockMaterial.isToolNotRequired()) {
			return true;
		} else {
			ItemStack stack = getStackInSlot(currentItem);
			return stack != null ? stack.canHarvestBlock(block) : false;
		}
	}

	@Override
	public float getStrVsBlock(Block block) {
		float strength = 1.0F;
		if(mainInventory[currentItem] != null) {
			strength *= mainInventory[currentItem].getStrVsBlock(block);
		}
		return strength;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack[] stacks = mainInventory;
		if(slot >= stacks.length) {
			slot -= stacks.length;
			stacks = armorInventory;
		}
		if(stacks[slot] != null) {
			ItemStack stack;
			if(stacks[slot].stackSize <= amount) {
				stack = stacks[slot];
				stacks[slot] = null;
				return stack;
			} else {
				stack = stacks[slot].splitStack(amount);
				if(stacks[slot].stackSize == 0) {
					stacks[slot] = null;
				}
				return stack;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		ItemStack[] stacks = mainInventory;
		if(slot >= stacks.length) {
			slot -= stacks.length;
			stacks = armorInventory;
		}
		stacks[slot] = stack;
	}

	@Override
	public void setCurrentItem(int id, int damage, boolean useDamage, boolean findEmpty) {
		currentItemStack = getCurrentItem();
		int slot;
		if(useDamage) {
			slot = getInventorySlotContainItemAndDamage(id, damage);
		} else {
			slot = getInventorySlotContainItem(id);
		}
		if(slot >= 0 && slot < 9) {
			currentItem = slot;
		} else {
			if(findEmpty && id > 0) {
				int emptySlot = getFirstEmptyStack();
				if(emptySlot >= 0 && emptySlot < 9) {
					currentItem = emptySlot;
				}
				func_70439_a(Item.itemsList[id], damage);
			}
		}
	}
	
	@Override
	public void setItemStack(ItemStack stack) {
		itemStack = stack;
	}

	@Override
	public void changeCurrentItem(int shift) {
		if(shift > 0) {
			shift = 1;
		}
		if(shift < 0) {
			shift = -1;
		}
		for(currentItem -= shift; currentItem < 0; currentItem += 9) {
		}

		while(currentItem >= 9) {
			currentItem -= 9;
		}
	}

	@Override
	public boolean addItemStackToInventory(ItemStack stack) {
		if(stack == null) {
			return false;
		} else if(stack.stackSize == 0) {
			return false;
		} else {
			try {
				if(stack.isItemDamaged()) {
					int slot = this.getFirstEmptyStack();

					if(slot >= 0) {
						this.mainInventory[slot] = ItemStack.copyItemStack(stack);
						this.mainInventory[slot].animationsToGo = 5;
						stack.stackSize = 0;
						return true;
					} else if(this.player.capabilities.isCreativeMode) {
						stack.stackSize = 0;
						return true;
					} else {
						return false;
					}
				} else {
					int stackSize;
					do {
						stackSize = stack.stackSize;
						stack.stackSize = storePartialItemStack(stack);
					} while(stack.stackSize > 0 && stack.stackSize < stackSize);
					if(stack.stackSize == stackSize && player.capabilities.isCreativeMode) {
						stack.stackSize = 0;
						return true;
					} else {
						return stack.stackSize < stackSize;
					}
				}
			} catch(Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
				crashreportcategory.addCrashSection("Item ID", Integer.valueOf(stack.itemID));
				crashreportcategory.addCrashSection("Item data", Integer.valueOf(stack.getItemDamage()));
				crashreportcategory.addCrashSectionCallable("Item name", new CallableItemName(this, stack));
				throw new ReportedException(crashreport);
			}
		}
	}

	public int storeItemStack(ItemStack stack) {
		for(int i = 0; i < mainInventory.length; i++) {
			if(mainInventory[i] != null && mainInventory[i].itemID == stack.itemID && mainInventory[i].isStackable() && mainInventory[i].stackSize < mainInventory[i].getMaxStackSize() && mainInventory[i].stackSize <= getInventoryStackLimit() && (!mainInventory[i].getHasSubtypes() || mainInventory[i].getItemDamage() == stack.getItemDamage()) && ItemStack.areItemStackTagsEqual(mainInventory[i], stack)) {
				return i;
			}
		}
		return -1;
	}

	public int storePartialItemStack(ItemStack stack) {
		int id = stack.itemID;
		int stackSize = stack.stackSize;
		if(stack.getMaxStackSize() == 1) {
			int slot = getFirstEmptyStack();
			if(slot < 0) {
				return stackSize;
			} else {
				if(mainInventory[slot] == null) {
					mainInventory[slot] = ItemStack.copyItemStack(stack);
				}
				return 0;
			}
		} else {
			int slot = storeItemStack(stack);
			if(slot < 0) {
				slot = getFirstEmptyStack();
			}
			if(slot < 0) {
				return stackSize;
			} else {
				if(mainInventory[slot] == null) {
					mainInventory[slot] = new ItemStack(id, 0, stack.getItemDamage());
					if(stack.hasTagCompound()) {
						mainInventory[slot].setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
					}
				}
				int stored = stackSize;
				if(stackSize > mainInventory[slot].getMaxStackSize() - mainInventory[slot].stackSize) {
					stored = mainInventory[slot].getMaxStackSize() - mainInventory[slot].stackSize;
				}
				if(stored > getInventoryStackLimit() - mainInventory[slot].stackSize) {
					stored = getInventoryStackLimit() - mainInventory[slot].stackSize;
				}
				if(stored == 0) {
					return stackSize;
				} else {
					stackSize -= stored;
					mainInventory[slot].stackSize += stored;
					mainInventory[slot].animationsToGo = 5;
					return stackSize;
				}
			}
		}
	}

	@Override
	public void func_70439_a(Item item, int damage) {
		if(item != null) {
			if(currentItemStack != null && currentItemStack.isItemEnchantable() && getInventorySlotContainItemAndDamage(currentItemStack.itemID, currentItemStack.getItemDamageForDisplay()) == currentItem) {
				return;
			}
			int slot = getInventorySlotContainItemAndDamage(item.itemID, damage);
			if(slot >= 0) {
				int stackSize = mainInventory[slot].stackSize;
				mainInventory[slot] = mainInventory[currentItem];
				mainInventory[currentItem] = new ItemStack(Item.itemsList[item.itemID], stackSize, damage);
			} else {
				mainInventory[currentItem] = new ItemStack(Item.itemsList[item.itemID], 1, damage);
			}
		}
	}

	public void copyInventory(InventoryPlayer inventory) {
		for(int i = 0; i < mainInventory.length; i++) {
			mainInventory[i] = ItemStack.copyItemStack(inventory.mainInventory[i]);
		}
		for(int i = 0; i < armorInventory.length; i++) {
			armorInventory[i] = ItemStack.copyItemStack(inventory.armorInventory[i]);
		}
		currentItem = inventory.currentItem;
	}
	

	@Override
	public void copyInventory(net.minecraft.entity.player.InventoryPlayer inventory) {
		if(inventory instanceof InventoryPlayer) {
			copyInventory((InventoryPlayer) inventory);
		}
		super.copyInventory(inventory);
	}

	public int clearInventory(int id, int damage) {
		int cleared = 0;
		for(int i = 0; i < mainInventory.length; i++) {
			ItemStack stack = mainInventory[i];
			if(stack != null && (id <= -1 || stack.itemID == id) && (damage <= -1 || stack.getItemDamage() == damage)) {
				cleared += stack.stackSize;
				mainInventory[i] = null;
			}
		}
		for(int i = 0; i < armorInventory.length; i++) {
			ItemStack stack = armorInventory[i];
			if(stack != null && (id <= -1 || stack.itemID == id) && (damage <= -1 || stack.getItemDamage() == damage)) {
				cleared += stack.stackSize;
				armorInventory[i] = null;
			}
		}
		if(itemStack != null) {
			if(id > -1 && itemStack.itemID != id) {
				return cleared;
			}
			if(damage > -1 && itemStack.getItemDamage() != damage) {
				return cleared;
			}
			cleared += itemStack.stackSize;
			setItemStack(null);
		}
		return cleared;
	}

	@Override
	public boolean consumeInventoryItem(int id) {
		int slot = getInventorySlotContainItem(id);
		if(slot >= 0) {
			mainInventory[slot].stackSize--;
			if(mainInventory[slot].stackSize <= 0) {
				mainInventory[slot] = null;
			}
			return true;
		}
		return false;
	}

	public boolean consumeInventoryItemAndDamage(int id, int damage) {
		int slot = getInventorySlotContainItemAndDamage(id, damage);
		if(slot >= 0) {
			mainInventory[slot].stackSize--;
			if(mainInventory[slot].stackSize <= 0) {
				mainInventory[slot] = null;
			}
			return true;
		}
		return false;
	}

	@Override
	public void damageArmor(float damage) {
		damage /= (float) armorInventory.length;
		if(damage < 1.0F) {
			damage = 1.0F;
		}
		for(int i = 0; i < armorInventory.length; i++) {
			if(armorInventory[i] != null && armorInventory[i].getItem() instanceof ItemArmor) {
				armorInventory[i].damageItem((int) damage, player);
				if(armorInventory[i].stackSize == 0) {
					armorInventory[i] = null;
				}
			}
		}
	}

	@Override
	public void dropAllItems() {
		for(int i = 0; i < mainInventory.length; i++) {
			if(mainInventory[i] != null) {
				player.dropPlayerItemWithRandomChoice(mainInventory[i], true);
				mainInventory[i] = null;
			}
		}
		for(int i = 0; i < armorInventory.length; i++) {
			if(armorInventory[i] != null) {
				player.dropPlayerItemWithRandomChoice(armorInventory[i], true);
				armorInventory[i] = null;
			}
		}
	}

	@Override
	public void decrementAnimations() {
		for(int i = 0; i < mainInventory.length; i++) {
			if(mainInventory[i] != null) {
				mainInventory[i].updateAnimation(player.worldObj, player, i, currentItem == i);
			}
		}
		for(int i = 0; i < armorInventory.length; i++) {
			if(armorInventory[i] != null) {
				armorInventory[i].getItem().onArmorTickUpdate(player.worldObj, player, armorInventory[i]);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagList nbtList) {
		mainInventory = new ItemStack[36];
		armorInventory = new ItemStack[10];
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound nbt = (NBTTagCompound) nbtList.tagAt(i);
			int slot = nbt.getByte("Slot") & 255;
			ItemStack stack = ItemStack.loadItemStackFromNBT(nbt);
			if(stack != null) {
				if(slot >= 0 && slot < mainInventory.length) {
					mainInventory[slot] = stack;
				}
				if(slot >= 100 && slot < 100 + armorInventory.length) {
					armorInventory[slot - 100] = stack;
				}
			}
		}
	}

	@Override
	public NBTTagList writeToNBT(NBTTagList nbtList) {
		NBTTagCompound nbt;
		for(int i = 0; i < this.mainInventory.length; i++) {
			if(mainInventory[i] != null) {
				nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) i);
				mainInventory[i].writeToNBT(nbt);
				nbtList.appendTag(nbt);
			}
		}
		for(int i = 0; i < armorInventory.length; i++) {
			if(armorInventory[i] != null) {
				nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) (i + 100));
				armorInventory[i].writeToNBT(nbt);
				nbtList.appendTag(nbt);
			}
		}
		return nbtList;
	}

	@Override
	public String getInvName() {
		return "container.inventory";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return player.isDead ? false : entityPlayer.getDistanceSqToEntity(player) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public void onInventoryChanged() {
		inventoryChanged = true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
}
