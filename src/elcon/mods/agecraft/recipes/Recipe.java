package elcon.mods.agecraft.recipes;

import java.util.List;

public abstract class Recipe {
	
	public Recipe() {
		RecipeManager.addRecipe(this);
	}

	public abstract List<List<WrappedStack>> getInputs();
	
	public abstract List<WrappedStack> getOutput(List<WrappedStack> input);
	
	public abstract RecipeType getRecipeType();
	
	public abstract int getRecipeSize();
}
