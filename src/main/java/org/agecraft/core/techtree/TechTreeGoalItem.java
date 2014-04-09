package org.agecraft.core.techtree;

import org.agecraft.ACUtil;

import net.minecraft.item.ItemStack;

public class TechTreeGoalItem extends TechTreeGoal {

	public ItemStack itemStack;
	public boolean compareNBT = true;
	
	public TechTreeGoalItem(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	public TechTreeGoalItem disableNBT() {
		compareNBT = false;
		return this;
	}
	
	@Override
	public boolean hasReachedGoal(Object object) {
		if(object != null && object instanceof ItemStack) {
			ItemStack stack = (ItemStack) object;
			return compareNBT ? ACUtil.areItemStacksEqualNoSize(stack, itemStack) : ACUtil.areItemStacksEqualNoSizeNoTags(stack, itemStack);
		}
		return false;
	}
}
