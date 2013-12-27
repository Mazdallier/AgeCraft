package elcon.mods.agecraft.core.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import elcon.mods.agecraft.recipes.Recipe;
import elcon.mods.agecraft.recipes.RecipeType;

public class RecipesSmeltery {

	public static class RecipeSmeltery extends Recipe {

		public ArrayList<ItemStack> input;
		public ArrayList<FluidStack> output;
		
		@Override
		public List<ItemStack> getInput() {
			return input;
		}

		@Override
		public List<ItemStack> getOutput() {
			return null;
		}

		@Override
		public RecipeType getRecipeType() {
			return null;
		}

		@Override
		public int getRecipeSize() {
			return 0;
		}
	}
}
