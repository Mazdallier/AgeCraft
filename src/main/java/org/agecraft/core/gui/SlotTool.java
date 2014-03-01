package org.agecraft.core.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.registry.ToolRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	public IIcon getBackgroundIconIndex() {
		if(ToolRegistry.instance.get(toolType) != null) {
			return ToolRegistry.instance.get(toolType).outline;
		}
		return null;
	}
}
