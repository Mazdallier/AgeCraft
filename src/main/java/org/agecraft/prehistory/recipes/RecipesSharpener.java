package org.agecraft.prehistory.recipes;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.agecraft.core.recipes.RecipeSimple;
import org.agecraft.core.recipes.RecipeType;
import org.agecraft.core.recipes.WrappedStack;
import org.agecraft.prehistory.PrehistoryAge;

public class RecipesSharpener {

	public static class RecipeSharpener extends RecipeSimple {

		public boolean[] input = new boolean[64];
		public ItemStack output;
		
		public RecipeSharpener(boolean[] in, ItemStack out) {
			super();
			input = in;
			output = out;
		}

		@Override
		public List<WrappedStack> getInput() {
			return WrappedStack.createList(new ItemStack(PrehistoryAge.rock, 2, 0));
		}

		@Override
		public List<WrappedStack> getOutput() {
			return WrappedStack.createList(output);
		}

		@Override
		public RecipeType getRecipeType() {
			return RecipeType.SHARPENING;
		}

		@Override
		public int getRecipeSize() {
			return 1;
		}
	}
	
	public static LinkedList<RecipeSharpener> recipes = new LinkedList<RecipeSharpener>();
	
	public static RecipeSharpener getRecipe(boolean[] input) {
		for(RecipeSharpener r : recipes) {
			if(r != null) {
				boolean equal = true;
				for(int i = 0; i < r.input.length; i++) {
					if(input[i] != r.input[i]) {
						equal = false;
					}
				}
				if(equal) {
					return r;
				}
			}
		}
		return null;
	}
	
	public static void addRecipe(boolean[] input, ItemStack output) {
		recipes.add(new RecipeSharpener(input, output));
	}
	
	public static void addRecipes() {
		boolean[] r = new boolean[64];
		r[3] = true; r[4] = true;
		r[10] = true; r[11] = true; r[12] = true; r[13] = true;
		r[17] = true; r[18] = true; r[19] = true; r[20] = true; r[21] = true; r[22] = true;
		r[25] = true; r[26] = true; r[27] = true; r[28] = true; r[29] = true; r[30] = true;
		r[32] = true; r[33] = true; r[34] = true; r[35] = true; r[36] = true; r[37] = true; r[38] = true; r[39] = true;
		r[40] = true; r[41] = true; r[42] = true; r[43] = true; r[44] = true; r[45] = true; r[46] = true; r[47] = true;
		r[49] = true; r[50] = true; r[51] = true; r[52] = true; r[53] = true; r[54] = true;
		r[58] = true; r[59] = true; r[60] = true; r[61] = true;		
		addRecipe(r, new ItemStack(PrehistoryAge.rockTool));
		
		r = new boolean[64];
		r[1] = true; r[2] = true;
		r[9] = true; r[10] = true; r[11] = true; r[12] = true;
		r[17] = true; r[18] = true; r[19] = true; r[20] = true; r[21] = true;
		r[25] = true; r[26] = true; r[27] = true; r[28] = true; r[29] = true; r[30] = true;
		r[33] = true; r[34] = true; r[35] = true; r[36] = true; r[37] = true; r[38] = true;
		r[41] = true; r[42] = true; r[43] = true; r[44] = true; r[45] = true; r[46] = true;
		r[49] = true; r[50] = true; r[51] = true; r[52] = true; r[53] = true;
		r[58] = true; r[59] = true; r[60] = true;	
		addRecipe(r, new ItemStack(PrehistoryAge.rockSkinningTool));
		
		r = new boolean[64];
		r[10] = true; r[11] = true; r[12] = true; r[13] = true;
		r[17] = true; r[18] = true; r[19] = true; r[20] = true; r[21] = true; r[22] = true;
		r[24] = true; r[25] = true; r[30] = true; r[31] = true;
		addRecipe(r, new ItemStack(PrehistoryAge.rockPickaxeHead));
	}
}
