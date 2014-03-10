package org.agecraft.core.recipes;

import net.minecraft.item.ItemStack;

public class RecipeAnvilShapeless extends RecipeShapeless {

	public boolean requiresHammer;
	public int hammerDamage;
	public int hammerHarvestLevel;
	
	public RecipeAnvilShapeless(ItemStack output, int hammerDamage, int hammerHarvestLevel, Object... input) {
		super(output, hammerDamage, input);
		this.hammerHarvestLevel = hammerHarvestLevel;
	}
	
	public RecipeAnvilShapeless(ItemStack output, int hammerDamage, Object... input) {
		super(output, input);
		this.hammerDamage = hammerDamage;
		this.requiresHammer = hammerDamage > 0;
		this.hammerHarvestLevel = 0;
	}

	@Override
	public RecipeType getRecipeType() {
		return RecipeType.SMITHING;
	}
}
