package elcon.mods.agecraft.core.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.ToolRegistry;
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBackgroundIconIndex() {
		if(ToolRegistry.tools[toolType] != null) {
			return ToolRegistry.tools[toolType].outline;
		}
		return null;
	}
}
