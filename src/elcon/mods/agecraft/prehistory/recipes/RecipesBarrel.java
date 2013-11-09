package elcon.mods.agecraft.prehistory.recipes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.recipes.Recipe;
import elcon.mods.agecraft.recipes.RecipeType;

public class RecipesBarrel {

	public static class RecipeBarrel extends Recipe {

		public ItemStack input;
		public ItemStack output;
		public String fluid;
		public int ticks;
		
		public RecipeBarrel(ItemStack input, ItemStack output, String fluid, int ticks) {
			super();
			this.input = input;
			this.output = output;
			this.fluid = fluid;
			this.ticks = ticks;
		}
		
		@Override
		public List<ItemStack> getInput() {
			return Arrays.asList(input);
		}

		@Override
		public List<ItemStack> getOutput() {
			return Arrays.asList(output);
		}

		@Override
		public RecipeType getRecipeType() {
			return RecipeType.SOAKING;
		}

		@Override
		public int getRecipeSize() {
			return 1;
		}
	}
	
	public static LinkedList<RecipeBarrel> recipes = new LinkedList<RecipeBarrel>();
	
	public static RecipeBarrel getRecipe(ItemStack stack) {
		for(RecipeBarrel recipe : recipes) {
			if(ACUtil.areItemStacksEqualNoSize(stack, recipe.input)) {
				return recipe;
			}
		}
		return null;
	}
	
	public static void addRecipe(ItemStack input, ItemStack output, String fluid, int ticks) {
		recipes.add(new RecipeBarrel(input, output, fluid, ticks));
	}
	
	public static void addRecipes() {
		addRecipe(new ItemStack(Item.leather), new ItemStack(Item.silk), "water", 1200);
	}
}
