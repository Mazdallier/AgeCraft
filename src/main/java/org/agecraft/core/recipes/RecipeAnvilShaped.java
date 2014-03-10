package org.agecraft.core.recipes;

import net.minecraft.item.ItemStack;

public class RecipeAnvilShaped extends RecipeShaped {

	public boolean requiresHammer;
	public int hammerDamage;
	public int hammerHarvestLevel;
	
	public RecipeAnvilShaped(ItemStack output, int hammerDamage, int hammerHarvestLevel, Object... input) {
		super(output, hammerDamage, input);
		this.hammerHarvestLevel = hammerHarvestLevel;
	}
	
	public RecipeAnvilShaped(ItemStack output, int hammerDamage, Object... input) {
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
