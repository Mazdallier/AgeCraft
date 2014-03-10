package org.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import org.agecraft.ACUtil;
import org.agecraft.core.Tools;
import org.agecraft.core.items.armor.ItemArmor;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.recipes.Recipe;
import org.agecraft.core.recipes.RecipeAnvilShaped;
import org.agecraft.core.recipes.RecipeAnvilShapeless;
import org.agecraft.core.recipes.RecipesAnvil;
import org.agecraft.core.tileentities.TileEntityAnvil;

import elcon.mods.elconqore.gui.ContainerBasic;
import elcon.mods.elconqore.gui.InventoryBasic;

public class ContainerAnvil extends ContainerBasic implements IContainerTools {

	public EntityPlayer player;
	public TileEntityAnvil anvil;
	public InventoryCraftMatrix craftMatrix;
	public InventoryBasic repairMatrix;
	public InventoryCraftResult craftResult;

	public boolean isRepair = false;
	private int damageHammer = -1;

	public ContainerAnvil(EntityPlayer player, TileEntityAnvil anvil, boolean isRepair) {
		this.player = player;
		this.anvil = anvil;
		this.isRepair = isRepair;
		anvil.inventory.container = this;
		craftResult = new InventoryCraftResult();

		addSlotToContainer(new SlotTool(anvil.inventory, 0, 142, 98, 13));

		if(isRepair) {
			repairMatrix = new InventoryBasic(this, 2, "container.anvil");
			addSlotToContainer(new SlotItemExtends(repairMatrix, 0, 22, 63, ItemTool.class, ItemArmor.class));
			addSlotToContainer(new Slot(repairMatrix, 1, 68, 63));
			addSlotToContainer(new SlotCraftingTools(player, this, repairMatrix, craftResult, 0, 142, 63));
		} else {
			craftMatrix = new InventoryCraftMatrix(this, 5, 5, "container.anvil");
			addSlotToContainer(new SlotCraftingTools(player, this, craftMatrix, craftResult, 0, 142, 63));
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 5; j++) {
					addSlotToContainer(new Slot(craftMatrix, i + j * 5, 8 + i * 18, 26 + j * 18));
				}
			}
		}
		addInventoryPlayer(player.inventory, 8, 122);
		if(isRepair) {
			onCraftMatrixChanged(repairMatrix);
		} else {
			onCraftMatrixChanged(craftMatrix);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack stackCopy = null;
		Slot slot = (Slot) inventorySlots.get(slotID);
		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			stackCopy = stack.copy();
			if(isRepair) {
				if(slotID >= 0 && slotID < 4) {
					if(!mergeItemStack(stack, 4, 40, true)) {
						return null;
					}
					slot.onSlotChange(stack, stackCopy);
				} else if(slotID >= 4 && slotID < 31) {
					if(stack.getItem() == Tools.hammer) {
						if(!mergeItemStack(stack, 0, 1, false)) {
							if(!mergeItemStack(stack, 31, 40, false)) {
								return null;
							}
						}
					} else if(!mergeItemStack(stack, 31, 40, false)) {
						return null;
					}
				} else if(slotID >= 31 && slotID < 40) {
					if(stack.getItem() == Tools.hammer) {
						if(!mergeItemStack(stack, 0, 1, false)) {
							if(!mergeItemStack(stack, 4, 31, false)) {
								return null;
							}
						}
					} else if(!mergeItemStack(stack, 4, 31, false)) {
						return null;
					}
				} else if(!mergeItemStack(stack, 4, 40, false)) {
					return null;
				}
			} else {
				if(slotID >= 0 && slotID < 27) {
					if(!mergeItemStack(stack, 27, 63, true)) {
						return null;
					}
					slot.onSlotChange(stack, stackCopy);
				} else if(slotID >= 27 && slotID < 54) {
					if(stack.getItem() == Tools.hammer) {
						if(!mergeItemStack(stack, 0, 1, false)) {
							if(!mergeItemStack(stack, 54, 63, false)) {
								return null;
							}						}
					} else if(!mergeItemStack(stack, 54, 63, false)) {
						return null;
					}
				} else if(slotID >= 54 && slotID < 63) {
					if(stack.getItem() == Tools.hammer) {
						if(!mergeItemStack(stack, 0, 1, false)) {
							if(!mergeItemStack(stack, 27, 54, false)) {
								return null;
							}
						}
					} else if(!mergeItemStack(stack, 27, 54, false)) {
						return null;
					}
				} else if(!mergeItemStack(stack, 27, 63, false)) {
					return null;	
				}
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
	public void onCraftMatrixChanged(IInventory inventory) {
		if(isRepair) {
			Recipe recipe = RecipesAnvil.findMatchingRecipe(repairMatrix);
			if(recipe != null) {
				craftResult.setInventorySlotContents(0, RecipesAnvil.getRecipeOutput(repairMatrix, anvil));
			}
		} else {
			Recipe recipe = RecipesAnvil.findMatchingRecipe(craftMatrix);
			if(recipe != null) {
				if(recipe instanceof RecipeAnvilShaped) {
					damageHammer = ((RecipeAnvilShaped) recipe).hammerDamage;
				} else if(recipe instanceof RecipeAnvilShapeless) {
					damageHammer = ((RecipeAnvilShapeless) recipe).hammerDamage;
				}
				craftResult.setInventorySlotContents(0, RecipesAnvil.getRecipeOutput(craftMatrix, anvil));
			} else {
				craftResult.setInventorySlotContents(0, null);
			}
		}
	}

	@Override
	public void damageTools() {
		if(damageHammer > 0 && anvil.inventory.getStackInSlot(0) != null) {
			ItemStack stack = anvil.inventory.getStackInSlot(0);
			ACUtil.damageItem(stack, damageHammer, player);
			anvil.inventory.setInventorySlotContents(1, stack);
			damageHammer = -1;
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if(!anvil.getWorldObj().isRemote) {
			if(isRepair) {
				for(int i = 0; i < repairMatrix.getSizeInventory(); i++) {
					ItemStack stack = repairMatrix.getStackInSlotOnClosing(i);
					if(stack != null) {
						player.dropPlayerItemWithRandomChoice(stack, false);
					}
				}
			} else {
				for(int i = 0; i < craftMatrix.getSizeInventory(); i++) {
					ItemStack stack = craftMatrix.getStackInSlotOnClosing(i);
					if(stack != null) {
						player.dropPlayerItemWithRandomChoice(stack, false);
					}
				}
			}
		}
		anvil.inventory.container = null;
	}

	@Override
	public boolean func_94530_a(ItemStack stack, Slot slot) {
		return slot.inventory != craftResult && super.func_94530_a(stack, slot);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(anvil.xCoord + 0.5D, anvil.yCoord + 0.5D, anvil.zCoord + 0.5D) <= 64.0D;
	}
}
