package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.tileentities.TileEntitySmelteryFurnace;
import elcon.mods.core.gui.ContainerBasic;

public class ContainerSmeltery extends ContainerBasic {

	public TileEntitySmelteryFurnace tile;
	public EntityPlayer player;

	public ContainerSmeltery(EntityPlayer player, TileEntitySmelteryFurnace tile) {
		this.tile = tile;
		this.player = player;

		addInventoryPlayer(player.inventory, 8, 102);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotID);
		if(slot != null && slot.getHasStack()) {
			ItemStack stackSlot = slot.getStack();
			stack = stackSlot.copy();
			if(slotID >= 0 && slotID < 27) {
				if(!mergeItemStack(stackSlot, 27, 36, false)) {
					return null;
				}
			} else if(slotID >= 27 && slotID < 36) {
				if(!mergeItemStack(stackSlot, 0, 27, false)) {
					return null;
				}
			} else if(!mergeItemStack(stackSlot, 0, 36, false)) {
				return null;
			}
			if(stackSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if(stackSlot.stackSize == stack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackSlot);
		}
		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(tile.xCoord + 0.5D, tile.yCoord + 0.5D, tile.zCoord + 0.5D) <= 64.0D;
	}

	public void onSlotClick(boolean isTopSlots, int slotX, int slotY, boolean isRightClick, boolean pressedShift) {
		System.out.println("clicked slot: " + isTopSlots + " " + slotX + ", " + slotY);
		boolean hasChanged = false;
		int slotID = slotX + slotY * 5;
		if(isTopSlots) {
			if(slotID < tile.ores.length) {
				ItemStack stack = tile.ores[slotID];
				if(player.inventory.getItemStack() != null) {
					ItemStack stackRest = addStackToOres(player.inventory.getItemStack().copy());
					player.inventory.setItemStack(stackRest);
					hasChanged = true;
				} else {
					if(stack != null) {
						if(pressedShift) {
							ItemStack stackRest = stack.copy();
							if(!player.inventory.addItemStackToInventory(stackRest)) {
								tile.ores[slotID] = stackRest;
							} else {
								tile.ores[slotID] = null;
							}
							hasChanged = true;
						} else {
							player.inventory.setItemStack(stack.copy());
							tile.ores[slotID] = null;
							hasChanged = true;
						}
					}
				}
			}
		} else {
			if(slotID < tile.fuel.length) {
				ItemStack stack = tile.fuel[slotID];
				if(player.inventory.getItemStack() != null) {
					ItemStack stackRest = addStackToFuel(player.inventory.getItemStack().copy());
					player.inventory.setItemStack(stackRest);
					hasChanged = true;
				} else {
					if(stack != null) {
						if(isRightClick) {
							player.inventory.setItemStack(tile.fuel[slotID].splitStack(tile.fuel[slotID].stackSize / 2));
							hasChanged = true;
						} else {
							if(pressedShift) {
								ItemStack stackRest = stack.copy();
								if(!player.inventory.addItemStackToInventory(stackRest)) {
									tile.fuel[slotID] = stackRest;
								} else {
									tile.fuel[slotID] = null;
								}
								hasChanged = true;
							} else {
								player.inventory.setItemStack(stack.copy());
								tile.fuel[slotID] = null;
								hasChanged = true;
							}
						}
					}
				}
			}
		}
		if(hasChanged) {
			tile.worldObj.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
			detectAndSendChanges();
			if(player instanceof EntityPlayerMP) {
				((EntityPlayerMP) player).sendContainerToPlayer(this);
			}
		}
	}

	private ItemStack addStackToOres(ItemStack stack) {
		for(int i = 0; i < tile.ores.length; i++) {
			if(tile.ores[i] == null) {
				tile.ores[i] = stack.copy();
				tile.ores[i].stackSize = 1;
				stack.stackSize -= 1;
				if(stack.stackSize <= 0) {
					return null;
				}
			}
		}
		return stack;
	}

	private ItemStack addStackToFuel(ItemStack stack) {
		for(int i = 0; i < tile.fuel.length; i++) {
			if(tile.fuel[i] == null) {
				tile.fuel[i] = stack;
				return null;
			} else if(ACUtil.areItemStacksEqualNoSize(tile.fuel[i], stack)) {
				int maxSize = 64 - tile.fuel[i].stackSize;
				if(maxSize >= stack.stackSize) {
					tile.fuel[i].stackSize += stack.stackSize;
					return null;
				} else if(maxSize != 0 && maxSize < stack.stackSize) {
					tile.fuel[i].stackSize += maxSize;
					stack.stackSize -= maxSize;
				}
			}
		}
		return stack;
	}
}
