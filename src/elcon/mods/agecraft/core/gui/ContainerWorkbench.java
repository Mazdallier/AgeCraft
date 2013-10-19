package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.Tools;

public class ContainerWorkbench extends Container {

	public InventoryWorkbench inventoryWorkbench;
	public InventoryCraftMatrix craftMatrix;
	public InventoryCraftResult craftResult;
	private World world;
	private int x;
	private int y;
	private int z;

	public ContainerWorkbench(InventoryPlayer inventory, World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		inventoryWorkbench = new InventoryWorkbench(this);
		craftMatrix = new InventoryCraftMatrix(this, 3, 3);
		craftResult = new InventoryCraftResult();

		addSlotToContainer(new SlotTool(inventoryWorkbench, 0, 15, 17, 13));
		addSlotToContainer(new SlotTool(inventoryWorkbench, 1, 15, 53, 15));

		addSlotToContainer(new SlotCrafting(inventory.player, craftMatrix, craftResult, 0, 138, 35));
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				addSlotToContainer(new Slot(craftMatrix, i + j * 3, 44 + i * 18, 17 + j * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, 9 + j + i * 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		// craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, world));
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if(!world.isRemote) {
			for(int i = 0; i < craftMatrix.getSizeInventory(); i++) {
				ItemStack stack = craftMatrix.getStackInSlotOnClosing(i);
				if(stack != null) {
					player.dropPlayerItem(stack);
				}
			}
			for(int i = 0; i < inventoryWorkbench.getSizeInventory(); i++) {
				ItemStack stack = inventoryWorkbench.getStackInSlotOnClosing(i);
				if(stack != null) {
					player.dropPlayerItem(stack);
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack stackCopy = null;
		Slot slot = (Slot) inventorySlots.get(slotID);
		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			stackCopy = stack.copy();
			if(slotID >= 0 && slotID < 12) {
				if(!mergeItemStack(stack, 12, 48, true)) {
					return null;
				}
				slot.onSlotChange(stack, stackCopy);
			} else if(slotID >= 12 && slotID < 39) {
				if(stack.itemID == Tools.hammer.itemID) {
					if(!mergeItemStack(stack, 0, 1, false)) {
						return null;
					}
				} else if(stack.itemID == Tools.saw.itemID) {
					if(!mergeItemStack(stack, 1, 2, false)) {
						return null;
					}
				} else if(!mergeItemStack(stack, 39, 48, false)) {
					return null;
				}
			} else if(slotID >= 39 && slotID < 48) {
				if(stack.itemID == Tools.hammer.itemID) {
					if(!mergeItemStack(stack, 0, 1, false)) {
						return null;
					}
				} else if(stack.itemID == Tools.handsaw.itemID) {
					if(!mergeItemStack(stack, 1, 2, false)) {
						return null;
					}
				} else if(!mergeItemStack(stack, 12, 39, false)) {
					return null;
				}
			} else if(!mergeItemStack(stack, 12, 48, false)) {
				return null;
			}
			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if(stack.stackSize == stackCopy.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stack);
		}
		return stackCopy;
	}

	@Override
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != craftResult && super.func_94530_a(stack, slot);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(x + 0.5D, y + 0.5D, z + 0.5D) <= 64.0D;
	}
}
