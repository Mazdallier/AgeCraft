package org.agecraft.core.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotItemExtends extends Slot {

	public Class[] classes;
	
	public SlotItemExtends(IInventory inventory, int id, int x, int y, Class... classes) {
		super(inventory, id, x, y);
		this.classes = classes;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack != null) {
			for(int i = 0; i < classes.length; i++) {
				if(classes[i].isInstance(stack.getItem()) || classes[i].isAssignableFrom(stack.getItem().getClass())) {
					return true;
				}
			}
		}
		return false;
	}
}
