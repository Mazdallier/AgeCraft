package elcon.mods.agecraft.recipes;

import java.util.List;
import java.util.Map;

public abstract class Recipe {
	
	public Recipe() {
		RecipeManager.addRecipe(this);
	}
	
	public abstract Map<List<WrappedStack>, List<WrappedStack>> getRecipes();
	
	public abstract RecipeType getRecipeType();
	
	public abstract int getRecipeSize();
}
