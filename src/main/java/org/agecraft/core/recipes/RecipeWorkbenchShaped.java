package org.agecraft.core.recipes;

import net.minecraft.item.ItemStack;

public class RecipeWorkbenchShaped extends RecipeShaped {

	public boolean requiresHammer;
	public boolean requiresSaw;
	public int hammerDamage;
	public int sawDamage;
	public int hammerHarvestLevel;
	public int sawHarvestLevel;

	public RecipeWorkbenchShaped(ItemStack output, int hammerDamage, int sawDamage, int hammerHarvestLevel, int sawHarvestLevel, Object... input) {
		this(output, hammerDamage, sawDamage, input);
		this.hammerHarvestLevel = hammerHarvestLevel;
		this.sawHarvestLevel = sawHarvestLevel;
	}
	
	public RecipeWorkbenchShaped(ItemStack output, Object... input) {
		this(output, -1, -1, input);
	}
	
	public RecipeWorkbenchShaped(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		super(output, input);
		this.hammerDamage = hammerDamage;
		this.sawDamage = sawDamage;
		this.requiresHammer = hammerDamage > 0;
		this.requiresSaw = sawDamage > 0;
		this.hammerHarvestLevel = 0;
		this.sawHarvestLevel = 0;
	}

	@Override
	public RecipeType getRecipeType() {
		return RecipeType.CRAFTING;
	}
}
