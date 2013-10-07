package elcon.mods.agecraft.core.gui;

import elcon.mods.agecraft.core.items.armor.ItemArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class ContainerInventory extends Container {

	public InventoryCraftMatrix craftMatrix;
	public InventoryCraftResult craftResult;
	public boolean isLocalWorld;
	public EntityPlayer player;

	public ContainerInventory(InventoryPlayer inventory, boolean isLocalWorld, EntityPlayer player) {
		this.isLocalWorld = isLocalWorld;
		this.player = player;
		craftMatrix = new InventoryCraftMatrix(this, 2, 2);
		craftResult = new InventoryCraftResult();

		addSlotToContainer(new SlotCrafting(inventory.player, craftMatrix, craftResult, 0, 143, 63));
		for(int i = 0; i < 2; ++i) {
			for(int j = 0; j < 2; ++j) {
				addSlotToContainer(new Slot(craftMatrix, j + i * 2, 134 + j * 18, 7 + i * 18));
			}
		}

		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 1, 89, 8, 0));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 2, 89, 26, 1));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 3, 89, 44, 2));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 4, 89, 62, 3));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 7, 71, 18, 6));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 8, 71, 36, 7));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 9, 71, 54, 8));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 5, 107, 18, 4));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 6, 107, 36, 5));
		addSlotToContainer(new SlotArmor(this, inventory, inventory.getSizeInventory() - 10, 107, 54, 8));

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		// craftResult.setInventoryContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, player.worldObj));
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		for(int i = 0; i < craftMatrix.getSizeInventory(); i++) {
			ItemStack stack = craftMatrix.getStackInSlotOnClosing(i);
			if(stack != null) {
				player.dropPlayerItem(stack);
			}
		}
		craftResult.setInventorySlotContents(0, null);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotID);
		if(slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if(slotID == 0) {
				if(!mergeItemStack(stackInSlot, 9, 45, true)) {
					return null;
				}
				slot.onSlotChange(stackInSlot, stack);
			} else if(slotID >= 1 && slotID < 5) {
				if(!mergeItemStack(stackInSlot, 9, 45, false)) {
					return null;
				}
			} else if(slotID >= 5 && slotID < 9) {
				if(!mergeItemStack(stackInSlot, 9, 45, false)) {
					return null;
				}
			} else if(stack.getItem() instanceof ItemArmor && !((Slot) inventorySlots.get(5 + ((ItemArmor) stack.getItem()).getArmorType(stack))).getHasStack()) {
				int armorSlot = 5 + ((ItemArmor) stack.getItem()).getArmorType(stack);
				if(!mergeItemStack(stackInSlot, armorSlot, armorSlot + 1, false)) {
					return null;
				}
			} else if(slotID >= 15 && slotID < 42) {
				if(!mergeItemStack(stackInSlot, 42, 51, false)) {
					return null;
				}
			} else if(slotID >= 42 && slotID < 51) {
				if(!mergeItemStack(stackInSlot, 15, 42, false)) {
					return null;
				}
			} else if(!mergeItemStack(stackInSlot, 15, 51, false)) {
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
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != craftResult && super.func_94530_a(stack, slot);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
