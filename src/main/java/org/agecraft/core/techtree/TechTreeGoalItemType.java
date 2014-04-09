package org.agecraft.core.techtree;

import net.minecraft.item.ItemStack;

public class TechTreeGoalItemType extends TechTreeGoal {

	public Class<?> itemClass;
	
	public TechTreeGoalItemType(Class<?> itemClass) {
		this.itemClass = itemClass;
	}

	@Override
	public boolean hasReachedGoal(Object object) {
		if(object != null && object instanceof ItemStack) {
			ItemStack stack = (ItemStack) object;
			return stack.getItem().getClass().isAssignableFrom(itemClass) || itemClass.isInstance(stack.getItem());
		}
		return false;
	}
}
