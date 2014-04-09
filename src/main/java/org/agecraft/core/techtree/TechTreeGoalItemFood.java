package org.agecraft.core.techtree;

import net.minecraft.item.ItemStack;

import org.agecraft.core.items.farming.ItemFood;
import org.agecraft.core.registry.FoodRegistry.FoodType;

public class TechTreeGoalItemFood extends TechTreeGoal {

	public FoodType foodType;
	
	public TechTreeGoalItemFood(FoodType foodType) {
		this.foodType = foodType;
	}
	
	@Override
	public boolean hasReachedGoal(Object object) {
		if(object != null && object instanceof ItemStack) {
			ItemStack stack = (ItemStack) object;
			return stack.getItem() instanceof ItemFood && ((ItemFood) stack.getItem()).getFood(stack.getItemDamage()).type == foodType;
		}
		return false;
	}
}
