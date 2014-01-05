package elcon.mods.agecraft.core.recipes;

import java.util.List;

import elcon.mods.agecraft.recipes.Recipe;
import elcon.mods.agecraft.recipes.RecipeType;
import elcon.mods.agecraft.recipes.WrappedStack;

public class RecipeTools extends Recipe {

	public String[] toolPatterns = new String[]{
			"",
			"",
			"",
	};
	
	@Override
	public List<List<WrappedStack>> getInputs() {
		return null;
	}

	@Override
	public List<WrappedStack> getOutput(List<WrappedStack> input) {
		return null;
	}

	@Override
	public RecipeType getRecipeType() {
		return RecipeType.CRAFTING;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}
}
