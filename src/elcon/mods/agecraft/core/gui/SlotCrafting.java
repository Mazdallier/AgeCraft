package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACUtil;

public class SlotCrafting extends Slot {

	private InventoryCraftMatrix craftMatrix;
	private EntityPlayer player;
	private int amountCrafted;

	public SlotCrafting(EntityPlayer player, InventoryCraftMatrix craftMatrix, IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		this.player = player;
		this.craftMatrix = craftMatrix;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		if(getHasStack()) {
			amountCrafted += Math.min(amount, getStack().stackSize);
		}
		return super.decrStackSize(amount);
	}

	@Override
	protected void onCrafting(ItemStack stack, int amount) {
		amountCrafted += amount;
		onCrafting(stack);
	}

	@Override
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(player.worldObj, player, amountCrafted);
		amountCrafted = 0;
		//achievement stuff
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer currentPlayer, ItemStack stack) {
		GameRegistry.onItemCrafted(currentPlayer, stack, craftMatrix);
		onCrafting(stack);
		for(int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
			ItemStack stackInSlot = craftMatrix.getStackInSlot(i);
			if(stackInSlot != null) {
				if(stackInSlot.getItem().isDamageable()) {
					if(stackInSlot.getItemDamage() > stackInSlot.getMaxDamage()) {
						stackInSlot = null;
					} else {
						ACUtil.damageItem(stackInSlot, 1, currentPlayer);
					}
					craftMatrix.setInventorySlotContents(i, stackInSlot);					
				} else {
					craftMatrix.decrStackSize(i, 1);
					if(stackInSlot.getItem().hasContainerItem()) {
						ItemStack stackContainer = stackInSlot.getItem().getContainerItemStack(stackInSlot);
						if(stackContainer.isItemStackDamageable() && stackContainer.getItemDamage() > stackContainer.getMaxDamage()) {
							stackContainer = null;
						}
						if(stackContainer != null && (!stackInSlot.getItem().doesContainerItemLeaveCraftingGrid(stackInSlot) || !player.inventory.addItemStackToInventory(stackContainer))) {
							if(craftMatrix.getStackInSlot(i) == null) {
								craftMatrix.setInventorySlotContents(i, stackContainer);
							} else {
								currentPlayer.dropPlayerItem(stackContainer);
							}
						}
					} 
				}
			}
		}
	}
}
