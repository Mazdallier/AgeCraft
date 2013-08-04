package elcon.mods.agecraft.prehistory.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotStone extends Slot {

	public SlotStone(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}
	
	@Override
	public ItemStack getStack() {
		if(((InventorySharpener) inventory).container.blocks[slotNumber - 2]) {
			return super.getStack();
		} else {
			return null;
		}
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
		super.onPickupFromSlot(player, itemStack);
		((InventorySharpener) inventory).container.blocks[slotNumber - 2] = false;
		((InventorySharpener) inventory).container.onCraftMatrixChanged(inventory);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return true;
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}
}