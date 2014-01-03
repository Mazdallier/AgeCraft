package elcon.mods.agecraft.core.recipes;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.gui.InventoryCraftMatrix;
import elcon.mods.agecraft.core.items.tools.ItemTool;
import elcon.mods.agecraft.core.tileentities.TileEntityWorkbench;
import elcon.mods.agecraft.recipes.Recipe;

public class RecipesWorkbench {

	public static LinkedList<Recipe> recipes = new LinkedList<Recipe>();
	
	public static void addShapedRecipe(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		RecipesWorkbench.recipes.add(new RecipeWorkbenchShaped(output, hammerDamage, sawDamage, input));
	}
	
	public static void addShapelessRecipe(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		RecipesWorkbench.recipes.add(new RecipeWorkbenchShapeless(output, hammerDamage, sawDamage, input));
	}
	
	public static void addRecipes() {
		recipes.add(new RecipeMicroblocks());
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				addShapelessRecipe(new ItemStack(Trees.planks.blockID, 4, i), 0, 1, new ItemStack(Trees.wood, 1, i * 4));
			}
		}
	}
	
	public static Recipe findMatchingRecipe(InventoryCraftMatrix inventory) {
		for(Recipe recipe : recipes) {
			if(recipe instanceof RecipeWorkbenchShaped) {
				if(((RecipeWorkbenchShaped) recipe).matches(inventory)) {
					return recipe;
				}
			} else if(recipe instanceof RecipeWorkbenchShapeless) {
				 if(((RecipeWorkbenchShapeless) recipe).matches(inventory)) {
					return recipe;
				}
			} else if(recipe instanceof RecipeMicroblocks) {
				if(((RecipeMicroblocks) recipe).matches(inventory)) {
					return recipe;
				}
			}
		}
		return null;
	}
	
	public static ItemStack getRecipeOutput(InventoryCraftMatrix inventory, TileEntityWorkbench workbench) {
		Recipe recipe = findMatchingRecipe(inventory);
		if(recipe != null) {
			if(recipe instanceof RecipeWorkbenchShaped) {
				RecipeWorkbenchShaped r = (RecipeWorkbenchShaped) recipe;
				if(r.requiresHammer && !hasHammer(workbench, r.hammerHarvestLevel)) {
					return null;
				}
				if(r.requiresSaw && !hasSaw(workbench, r.sawHarvestLevel)) {
					return null;
				}
				return r.output.copy();
			} else if(recipe instanceof RecipeWorkbenchShapeless) {
				RecipeWorkbenchShapeless r = (RecipeWorkbenchShapeless) recipe;
				if(r.requiresHammer && !hasHammer(workbench, r.hammerHarvestLevel)) {
					return null;
				}
				if(r.requiresSaw && !hasSaw(workbench, r.sawHarvestLevel)) {
					return null;
				}
				return r.output.copy();
			} else if(recipe instanceof RecipeMicroblocks) {
				return ((RecipeMicroblocks) recipe).getCraftingResult(inventory);
			}
		}
		return null;
	}
	
	public static boolean hasHammer(TileEntityWorkbench workbench, int harvestLevel) {
		if(workbench.getStackInSlot(0) != null) {
			return ((ItemTool) workbench.getStackInSlot(0).getItem()).getToolHarvestLevel(workbench.getStackInSlot(0)) >= harvestLevel;
		}
		return false;
	}
	
	public static boolean hasSaw(TileEntityWorkbench workbench, int harvestLevel) {
		if(workbench.getStackInSlot(1) != null) {
			return ((ItemTool) workbench.getStackInSlot(1).getItem()).getToolHarvestLevel(workbench.getStackInSlot(1)) >= harvestLevel;
		}
		return false;
	}
}
