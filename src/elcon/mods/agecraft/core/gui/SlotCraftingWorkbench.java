package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotCraftingWorkbench extends SlotCrafting {

	public ContainerWorkbench container;
	
	public SlotCraftingWorkbench(EntityPlayer player, ContainerWorkbench container, InventoryCraftMatrix craftMatrix, IInventory inventory, int id, int x, int y) {
		super(player, craftMatrix, inventory, id, x, y);
		this.container = container;
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer currentPlayer, ItemStack stack) {
		super.onPickupFromSlot(currentPlayer, stack);
		container.damageTools();
	}
}
