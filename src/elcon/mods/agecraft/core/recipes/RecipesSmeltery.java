package elcon.mods.agecraft.core.recipes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.Metals;
import elcon.mods.agecraft.recipes.RecipeSimple;
import elcon.mods.agecraft.recipes.RecipeType;
import elcon.mods.agecraft.recipes.WrappedStack;

public class RecipesSmeltery {

	public static class RecipeSmeltery extends RecipeSimple {

		public ArrayList<ItemStack> input = new ArrayList<ItemStack>();
		public ArrayList<FluidStack> output = new ArrayList<FluidStack>();
		
		public RecipeSmeltery(ItemStack input, FluidStack output) {
			super();
			this.input.add(input);
			this.output.add(output);
		}
		
		public RecipeSmeltery(ItemStack[] input, FluidStack output) {
			super();
			for(int i = 0; i < input.length; i++) {
				this.input.add(input[i]);
			}
			this.output.add(output);
		}
		
		public RecipeSmeltery(ItemStack input, FluidStack[] output) {
			super();
			this.input.add(input);
			for(int i = 0; i < output.length; i++) {
				this.output.add(output[i]);
			}
		}
		
		public RecipeSmeltery(ItemStack[] input, FluidStack[] output) {
			super();
			for(int i = 0; i < input.length; i++) {
				this.input.add(input[i]);
			}
			for(int i = 0; i < output.length; i++) {
				this.output.add(output[i]);
			}
		}
		
		@Override
		public List<WrappedStack> getInput() {
			return WrappedStack.createList(input);
		}

		@Override
		public List<WrappedStack> getOutput() {
			return WrappedStack.createList(output);
		}

		@Override
		public RecipeType getRecipeType() {
			return RecipeType.SMELTING;
		}

		@Override
		public int getRecipeSize() {
			return input.size();
		}
	}
	
	public static LinkedList<RecipeSmeltery> recipes = new LinkedList<RecipeSmeltery>();
	
	public static RecipeSmeltery getRecipe(ItemStack... input) {
		for(RecipeSmeltery recipe : recipes) {
			if(recipe.input.size() == input.length) {
				boolean match = true;
				for(int i = 0; i < input.length; i++) {
					if(!ACUtil.areItemStacksEqualNoSize(input[i], recipe.input.get(i))) {
						match = false;
					}
				}
				if(match) {
					return recipe;
				}
			}
		}
		return null;
	}
	
	public static void addRecipe(RecipeSmeltery recipe) {
		recipes.add(recipe);
	}
	
	public static void addRecipes() {
		addRecipe(new RecipeSmeltery(new ItemStack(Metals.ore.blockID, 1, 0), new FluidStack(FluidRegistry.WATER, 500)));
		addRecipe(new RecipeSmeltery(new ItemStack(Metals.ore.blockID, 1, 4), new FluidStack(FluidRegistry.WATER, 1000)));
	}
}
