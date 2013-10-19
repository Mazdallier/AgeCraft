package elcon.mods.agecraft.recipes;

import java.util.HashMap;
import java.util.LinkedList;

public class RecipeManager {

	public static HashMap<RecipeType, LinkedList<Recipe>> recipes = new HashMap<RecipeType, LinkedList<Recipe>>();

	public static void addRecipe(Recipe recipe) {
		if(!recipes.containsKey(recipe.getRecipeType())) {
			recipes.put(recipe.getRecipeType(), new LinkedList<Recipe>());
		}
		LinkedList<Recipe> list = recipes.get(recipe.getRecipeType());
		if(!list.contains(recipe)) {
			list.add(recipe);
		}
	}
	
	public static void removeRecipe(Recipe recipe) {
		if(recipes.containsKey(recipe.getRecipeType())) {
			LinkedList<Recipe> list = recipes.get(recipe.getRecipeType());
			if(list.contains(recipe)) {
				list.remove(recipe);
			}
		}
	}
}
