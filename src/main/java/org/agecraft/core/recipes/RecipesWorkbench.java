package org.agecraft.core.recipes;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;

import org.agecraft.core.Stone;
import org.agecraft.core.Trees;
import org.agecraft.core.gui.InventoryCraftMatrix;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.registry.TreeRegistry;
import org.agecraft.core.tileentities.TileEntityWorkbench;

public class RecipesWorkbench {

	public static LinkedList<Recipe> recipes = new LinkedList<Recipe>();
	
	public static void addShapedRecipe(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		RecipesWorkbench.recipes.add(new RecipeWorkbenchShaped(output, hammerDamage, sawDamage, input));
	}
	
	public static void addShapelessRecipe(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		RecipesWorkbench.recipes.add(new RecipeWorkbenchShapeless(output, hammerDamage, sawDamage, input));
	}
	
	public static void addRecipes() {
		recipes.add(new RecipeTools());
		recipes.add(new RecipeMicroblocks());
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			if(TreeRegistry.instance.get(i) != null) {
				addShapelessRecipe(new ItemStack(Trees.planks, 4, i), 0, 1, new ItemStack(Trees.wood, 1, i * 4));
			}
		}		
		for(int i = 0; i < 16; i++) {
			addShapelessRecipe(new ItemStack(Stone.stoneCracked, 1, i), 1, 0, new ItemStack(Stone.stone, 1, i));
			addShapelessRecipe(new ItemStack(Stone.stoneBrick, 1, 1 + i * 8), 1, 0, new ItemStack(Stone.stoneBrick, 1, i * 8));
			
			addShapelessRecipe(new ItemStack(Stone.coloredStoneCracked, 1, i), 1, 0, new ItemStack(Stone.coloredStone, 1, i));
			addShapelessRecipe(new ItemStack(Stone.coloredStoneBrick, 1, 1 + i* 8), 1, 0, new ItemStack(Stone.coloredStoneBrick, 1, i * 8));
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
			} else if(recipe instanceof RecipeTools) {
				if(((RecipeTools) recipe).matches(inventory)) {
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
			} else if(recipe instanceof RecipeTools) {
				return ((RecipeTools) recipe).getCraftingResult(inventory);
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
