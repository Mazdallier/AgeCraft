package elcon.mods.agecraft.core.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.core.items.armor.ItemArmor;

public class ContainerInventory extends ContainerPlayer {

	public InventoryCraftMatrix inventoryCraftMatrix;
	public InventoryCraftResult inventoryCraftResult;
	public InventoryArmor inventoryArmor;

	public ContainerInventory(InventoryPlayer inventory, boolean isLocalWorld, EntityPlayer player) {
		super(inventory, isLocalWorld, player);
		inventoryCraftMatrix = new InventoryCraftMatrix(this, 2, 2);
		inventoryCraftResult = new InventoryCraftResult();
		inventoryArmor = new InventoryArmor(this);
		inventorySlots.clear();
		inventoryItemStacks.clear();

		addSlotToContainer(new SlotCrafting(inventory.player, inventoryCraftMatrix, inventoryCraftResult, 0, 143, 63));
		for(int i = 0; i < 2; ++i) {
			for(int j = 0; j < 2; ++j) {
				addSlotToContainer(new Slot(inventoryCraftMatrix, j + i * 2, 134 + j * 18, 7 + i * 18));
			}
		}
		addPlayerInventory(inventory, 8, 84);

		addSlotToContainer(new SlotArmor(this, inventoryArmor, 0, 89, 8, 0));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 1, 89, 26, 1));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 2, 89, 44, 2));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 3, 89, 62, 3));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 4, 71, 18, 6));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 5, 71, 36, 7));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 6, 71, 54, 8));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 7, 107, 18, 4));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 8, 107, 36, 5));
		addSlotToContainer(new SlotArmor(this, inventoryArmor, 9, 107, 54, 8));

		onCraftMatrixChanged(inventoryCraftMatrix);
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		ItemStack stack = null;
		if(inventoryCraftMatrix.getStackInRowAndColumn(1, 1) != null && inventoryCraftMatrix.getStackInRowAndColumn(1, 1).itemID == Block.cloth.blockID) {
			stack = new ItemStack(Block.cloth);
		}
		inventoryCraftResult.setInventorySlotContents(0, stack);
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
        crafters.add(crafting);
        crafting.sendContainerAndContentsToPlayer(this, getInventory());
        detectAndSendChanges();
	}

	private void addPlayerInventory(InventoryPlayer inventory, int x, int y) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, x + j * 18, y + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, x + i * 18, y + 58));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotID);
		if(slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if(slotID == 0) {
				if(!mergeItemStack(stackInSlot, 5, 41, true)) {
					return null;
				}
				slot.onSlotChange(stackInSlot, stack);
			} else if(slotID >= 1 && slotID < 5) {
				if(!mergeItemStack(stackInSlot, 5, 41, false)) {
					return null;
				}
			} else if(stack.getItem() instanceof ItemArmor && !((Slot) inventorySlots.get(42 + ((ItemArmor) stack.getItem()).getArmorType(stack))).getHasStack()) {
				int armorSlot = 42 + ((ItemArmor) stack.getItem()).getArmorType(stack);
				if(!mergeItemStack(stackInSlot, armorSlot, armorSlot + 1, false)) {
					return null;
				}
			} else if(slotID >= 5 && slotID < 32) {
				if(!mergeItemStack(stackInSlot, 32, 41, false)) {
					return null;
				}
			} else if(slotID >= 32 && slotID < 41) {
				if(!mergeItemStack(stackInSlot, 5, 32, false)) {
					return null;
				}
			} else if(slotID >= 42 && slotID < 51) {
				if(!mergeItemStack(stackInSlot, 5, 41, false)) {
					return null;
				}
			} else if(!mergeItemStack(stackInSlot, 5, 41, false)) {
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

	public EntityPlayer getPlayer() {
		return thePlayer;
	}
}
