package elcon.mods.agecraft.recipes;

import java.util.List;

public abstract class Recipe {
	
	public Recipe() {
		RecipeManager.addRecipe(this);
	}

	public abstract List<WrappedStack> getInput();
	
	public abstract List<WrappedStack> getOutput();
	
	public abstract RecipeType getRecipeType();
	
	public abstract int getRecipeSize();
}
