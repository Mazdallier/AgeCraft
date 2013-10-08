package elcon.mods.agecraft.prehistory.recipes;

import java.util.LinkedList;

import elcon.mods.agecraft.ACUtil;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesCampfire {

	public static class RecipeCampfire {
		
		public ItemStack raw;
		public ItemStack cooked;
		public ItemStack burned;
		
		public RecipeCampfire(ItemStack raw, ItemStack cooked, ItemStack burned) {
			this.raw = raw;
			this.cooked = cooked;
			this.burned = burned;
		}
	}
	
	public static LinkedList<RecipeCampfire> recipes = new LinkedList<RecipeCampfire>();
	
	public static RecipeCampfire getRecipe(ItemStack stack) {
		for(RecipeCampfire recipe : recipes) {
			if(ACUtil.areItemStacksEqualNoSize(stack, recipe.raw)) {
				return recipe;
			}
		}
		return null;
	}
	
	public static void addRecipe(ItemStack raw, ItemStack cooked, ItemStack burned) {
		recipes.add(new RecipeCampfire(raw, cooked, burned));
	}
	
	public static void addRecipes() {
		addRecipe(new ItemStack(Item.beefRaw), new ItemStack(Item.beefCooked), new ItemStack(Item.gunpowder));
	}
}
