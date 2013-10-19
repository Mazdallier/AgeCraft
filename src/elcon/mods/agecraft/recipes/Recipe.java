package elcon.mods.agecraft.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;

public abstract class Recipe {
	
	public Recipe() {
		RecipeManager.addRecipe(this);
	}

	public abstract List<ItemStack> getInput();
	
	public abstract List<ItemStack> getOutput();
	
	public abstract RecipeType getRecipeType();
	
	public abstract int getRecipeSize();
}
