package org.agecraft.prehistory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.agecraft.ACUtil;
import org.agecraft.core.Trees;
import org.agecraft.core.items.farming.ItemFood;
import org.agecraft.core.recipes.RecipeSimple;
import org.agecraft.core.recipes.RecipeType;
import org.agecraft.core.recipes.WrappedStack;
import org.agecraft.core.registry.FoodRegistry;
import org.agecraft.core.registry.FoodRegistry.Food;
import org.agecraft.core.registry.FoodRegistry.FoodStage;
import org.agecraft.core.registry.FoodRegistry.FoodType;
import org.agecraft.core.registry.TreeRegistry;

public class RecipesCampfire {

	public static class RecipeCampfire extends RecipeSimple {
		
		public ItemStack raw;
		public ItemStack cooked;
		public ItemStack burned;
		
		public int cookTime;
		public int burnTime;
		
		public RecipeCampfire(ItemStack raw, ItemStack cooked, ItemStack burned, int cookTime, int burnTime) {
			super();
			this.raw = raw;
			this.cooked = cooked;
			this.burned = burned;
			
			this.cookTime = cookTime;
			this.burnTime = burnTime;
		}
		
		public static RecipeCampfire readFromNBT(NBTTagCompound nbt) {
			ItemStack raw = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Raw"));
			for(RecipeCampfire recipe : RecipesCampfire.recipes) {
				if(ACUtil.areItemStacksEqualNoSize(raw, recipe.raw)) {
					return recipe;
				}
			}
			return null;
		}
		
		public void writeToNBT(NBTTagCompound nbt) {
			NBTTagCompound tag = new NBTTagCompound();
			raw.writeToNBT(tag);
			nbt.setTag("Raw", tag);
		}

		@Override
		public List<WrappedStack> getInput() {
			return WrappedStack.createList(raw);
		}

		@Override
		public List<WrappedStack> getOutput() {
			return WrappedStack.createList(cooked);
		}

		@Override
		public RecipeType getRecipeType() {
			return RecipeType.COOKING;
		}

		@Override
		public int getRecipeSize() {
			return 1;
		}
	}
	
	public static HashMap<ItemStack, Integer> fuel = new HashMap<ItemStack, Integer>();
	public static LinkedList<RecipeCampfire> recipes = new LinkedList<RecipeCampfire>();
	
	public static int getFuelValue(ItemStack s) {
		for(ItemStack stack : fuel.keySet()) {
			if(ACUtil.areItemStacksEqualNoSize(stack, s)) {
				return fuel.get(stack);
			}
		}
		return 0;
	}
	
	public static void addFuel(ItemStack stack, int fuelValue) {
		fuel.put(stack, fuelValue);
	}
	
	public static RecipeCampfire getRecipe(ItemStack stack) {
		for(RecipeCampfire recipe : recipes) {
			if(ACUtil.areItemStacksEqualNoSize(stack, recipe.raw)) {
				return recipe;
			}
		}
		return null;
	}
	
	public static void addRecipe(ItemStack raw, ItemStack cooked, ItemStack burned) {
		recipes.add(new RecipeCampfire(raw, cooked, burned, 200, 100));
	}
	
	public static void addRecipes() {
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			if(TreeRegistry.instance.get(i) != null) {
				addFuel(new ItemStack(Trees.log, 1, i), 100);
			}
		}
		ArrayList<Food> foods = new ArrayList<Food>();
		foods.addAll(FoodRegistry.getAll(FoodType.MEAT));
		foods.addAll(FoodRegistry.getAll(FoodType.FISH));
		for(Food food : foods) {
			if(food.properties.containsKey(FoodStage.RAW) && food.properties.containsKey(FoodStage.COOKED) && food.properties.containsKey(FoodStage.BURNED)) {
				addRecipe(ItemFood.createFood(food.name, FoodStage.RAW), ItemFood.createFood(food.name, FoodStage.COOKED), ItemFood.createFood(food.name, FoodStage.BURNED));
			}
		}
	}
}
