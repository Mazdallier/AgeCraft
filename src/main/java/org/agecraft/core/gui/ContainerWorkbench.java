package org.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.agecraft.core.Tools;
import org.agecraft.core.recipes.Recipe;
import org.agecraft.core.recipes.RecipeWorkbenchShaped;
import org.agecraft.core.recipes.RecipeWorkbenchShapeless;
import org.agecraft.core.recipes.RecipesWorkbench;
import org.agecraft.core.tileentities.TileEntityWorkbench;

import elcon.mods.elconqore.gui.ContainerBasic;

public class ContainerWorkbench extends ContainerBasic {

	public TileEntityWorkbench workbench;
	public InventoryCraftMatrix craftMatrix;
	public InventoryCraftResult craftResult;
	private EntityPlayer player;
	private World world;
	private int x;
	private int y;
	private int z;
	private int damageHammer;
	private int damageSaw;

	public ContainerWorkbench(EntityPlayer player, InventoryPlayer inventory, TileEntityWorkbench tile, World world, int x, int y, int z) {
		this.player = player;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		workbench = tile;
		workbench.container = this;
		craftMatrix = new InventoryCraftMatrix(this, 3, 3);
		craftResult = new InventoryCraftResult();

		addSlotToContainer(new SlotTool(workbench, 0, 15, 17, 13));
		addSlotToContainer(new SlotTool(workbench, 1, 15, 53, 15));

		addSlotToContainer(new SlotCraftingWorkbench(inventory.player, this, craftMatrix, craftResult, 0, 138, 35));
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				addSlotToContainer(new Slot(craftMatrix, i + j * 3, 44 + i * 18, 17 + j * 18));
			}
		}

		addInventoryPlayer(inventory, 8, 84);
		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		Recipe recipe = RecipesWorkbench.findMatchingRecipe(craftMatrix);
		if(recipe != null) {
			if(recipe instanceof RecipeWorkbenchShaped) {
				damageHammer = ((RecipeWorkbenchShaped) recipe).hammerDamage;
				damageSaw = ((RecipeWorkbenchShaped) recipe).sawDamage;
			} else if(recipe instanceof RecipeWorkbenchShapeless) {
				damageHammer = ((RecipeWorkbenchShapeless) recipe).hammerDamage;
				damageSaw = ((RecipeWorkbenchShapeless) recipe).sawDamage;
			}
			craftResult.setInventorySlotContents(0, RecipesWorkbench.getRecipeOutput(craftMatrix, workbench));
		} else {
			craftResult.setInventorySlotContents(0, null);
		}
	}
	
	public void damageTools() {
		if(damageHammer > 0 && workbench.getStackInSlot(0) != null) {
			ItemStack stack = workbench.getStackInSlot(0);
			stack.damageItem(damageHammer, player);
			//ACUtil.damageItem(stack, damageHammer, player);
			workbench.setInventorySlotContents(1, stack);
			damageHammer = -1;
		}
		if(damageSaw > 0 && workbench.getStackInSlot(1) != null) {
			ItemStack stack = workbench.getStackInSlot(1);
			stack.damageItem(damageSaw, player);
			//ACUtil.damageItem(stack, damageSaw, player);
			workbench.setInventorySlotContents(1, stack);
			damageSaw = -1;
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if(!world.isRemote) {
			for(int i = 0; i < craftMatrix.getSizeInventory(); i++) {
				ItemStack stack = craftMatrix.getStackInSlotOnClosing(i);
				if(stack != null) {
					player.dropPlayerItemWithRandomChoice(stack, false);
				}
			}
		}
		workbench.container = null;
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
				if(stack.getItem() == Tools.hammer) {
					if(!mergeItemStack(stack, 0, 1, false)) {
						return null;
					}
				} else if(stack.getItem() == Tools.saw) {
					if(!mergeItemStack(stack, 1, 2, false)) {
						return null;
					}
				} else if(!mergeItemStack(stack, 39, 48, false)) {
					return null;
				}
			} else if(slotID >= 39 && slotID < 48) {
				if(stack.getItem() == Tools.hammer) {
					if(!mergeItemStack(stack, 0, 1, false)) {
						return null;
					}
				} else if(stack.getItem() == Tools.handsaw) {
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
