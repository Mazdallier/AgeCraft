package elcon.mods.agecraft.core.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.core.items.tools.ItemTool;

public class SlotTool extends Slot {

	public int toolType;
	
	public SlotTool(IInventory inventory, int id, int x, int y, int toolType) {
		super(inventory, id, x, y);
		this.toolType = toolType;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack.getItem() instanceof ItemTool) {
			return ((ItemTool) stack.getItem()).getToolType(stack) == toolType;
		}
		return false;
	}
}
