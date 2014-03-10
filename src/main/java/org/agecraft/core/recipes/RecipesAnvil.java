package org.agecraft.core.recipes;

import java.util.LinkedList;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.agecraft.core.gui.InventoryCraftMatrix;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.tileentities.TileEntityAnvil;

import elcon.mods.elconqore.gui.InventoryBasic;

public class RecipesAnvil {

	public static LinkedList<Recipe> recipes = new LinkedList<Recipe>();
	
	public static void addShapedRecipe(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		RecipesAnvil.recipes.add(new RecipeAnvilShaped(output, hammerDamage, sawDamage, input));
	}
	
	public static void addShapelessRecipe(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		RecipesAnvil.recipes.add(new RecipeAnvilShapeless(output, hammerDamage, sawDamage, input));
	}
	
	public static void addRecipes() {
		
	}
	
	public static Recipe findMatchingRecipe(IInventory inventory) {
		for(Recipe recipe : recipes) {
			if(inventory instanceof InventoryCraftMatrix) {
				if(recipe instanceof RecipeAnvilShaped) {
					if(((RecipeAnvilShaped) recipe).matches((InventoryCraftMatrix) inventory)) {
						return recipe;
					}
				} else if(recipe instanceof RecipeAnvilShapeless) {
					 if(((RecipeAnvilShapeless) recipe).matches((InventoryCraftMatrix) inventory)) {
						return recipe;
					}
				}
			} else {
				if(recipe instanceof RecipeAnvilRepair) {
					if(((RecipeAnvilRepair) recipe).matches((InventoryBasic) inventory)) {
						return recipe;
					}
				}
			}			
		}
		return null;
	}
	
	public static ItemStack getRecipeOutput(IInventory inventory, TileEntityAnvil anvil) {
		Recipe recipe = findMatchingRecipe(inventory);
		if(recipe != null) {
			if(recipe instanceof RecipeAnvilShaped) {
				RecipeAnvilShaped r = (RecipeAnvilShaped) recipe;
				if(r.requiresHammer && !hasHammer(anvil, r.hammerHarvestLevel)) {
					return null;
				}
				return r.output.copy();
			} else if(recipe instanceof RecipeAnvilShapeless) {
				RecipeAnvilShapeless r = (RecipeAnvilShapeless) recipe;
				if(r.requiresHammer && !hasHammer(anvil, r.hammerHarvestLevel)) {
					return null;
				}
				return r.output.copy();
			} else if(recipe instanceof RecipeAnvilRepair) {
				RecipeAnvilRepair r = (RecipeAnvilRepair) recipe;
				if(!hasHammer(anvil, 0)) {
					return null;
				}
				return r.getOutput(((InventoryBasic) inventory));
			}
		}
		return null;
	}
	
	public static boolean hasHammer(TileEntityAnvil anvil, int harvestLevel) {
		if(anvil.inventory.getStackInSlot(0) != null) {
			return ((ItemTool) anvil.inventory.getStackInSlot(0).getItem()).getToolHarvestLevel(anvil.inventory.getStackInSlot(0)) >= harvestLevel;
		}
		return false;
	}
}
