package elcon.mods.agecraft.prehistory;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CampfireRecipes {

	private static HashMap<Integer, HashMap<Integer, Integer>> fuelValues = new HashMap<Integer, HashMap<Integer, Integer>>();
	private static HashMap<Integer, HashMap<Integer, ArrayList<ItemStack>>> cookRecipes = new HashMap<Integer, HashMap<Integer, ArrayList<ItemStack>>>();
	
	public static int getFuel(ItemStack stack) {
		if(fuelValues.containsKey(stack.itemID)) {
			if(fuelValues.get(stack.itemID).containsKey(stack.getItemDamage())) {
				return fuelValues.get(stack.itemID).get(stack.getItemDamage());
			}
		}
		return 0;
	}	
	
	public static void addFuel(ItemStack stack, int value) {
		HashMap<Integer, Integer> data;
		if(fuelValues.containsKey(stack.itemID)) {
			data = fuelValues.get(stack.itemID);
		} else {
			data = new HashMap<Integer, Integer>();
		}
		data.put(stack.getItemDamage(), value);
		fuelValues.put(stack.itemID, data);
	}
	
	public static void removeFuel(ItemStack stack) {
		if(fuelValues.containsKey(stack.itemID)) {
			if(fuelValues.get(stack.itemID).containsKey(stack.getItemDamage())) {
				fuelValues.get(stack.itemID).remove(stack.getItemDamage());
			}
		}
	}
	
	public static boolean isFuel(ItemStack stack) {
		return getFuel(stack) > 0;
	}
	
	public static ItemStack getRecipeOutput(ItemStack stack) {
		if(cookRecipes.containsKey(stack.itemID)) {
			if(cookRecipes.get(stack.itemID).containsKey(stack.getItemDamage())) {
				return cookRecipes.get(stack.itemID).get(stack.getItemDamage()).get(0);
			}
		}
		return null;
	}
	
	public static ItemStack getRecipeOutputBurned(ItemStack stack) {
		if(cookRecipes.containsKey(stack.itemID)) {
			if(cookRecipes.get(stack.itemID).containsKey(stack.getItemDamage())) {
				return cookRecipes.get(stack.itemID).get(stack.getItemDamage()).get(1);
			}
		}
		return null;
	}

	public static void addRecipe(ItemStack in, ItemStack out, ItemStack burned) {
		HashMap<Integer, ArrayList<ItemStack>> data;
		if(cookRecipes.containsKey(in.itemID)) {
			data = cookRecipes.get(in.itemID);
		} else {
			data = new HashMap<Integer, ArrayList<ItemStack>>();
		}
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(0, out);
		list.add(1, burned);
		data.put(in.getItemDamage(), list);
		cookRecipes.put(in.itemID, data);
	}
	
	public static void removeRecipe(ItemStack stack) {
		if(cookRecipes.containsKey(stack.itemID)) {
			if(cookRecipes.get(stack.itemID).containsKey(stack.getItemDamage())) {
				cookRecipes.get(stack.itemID).remove(stack.getItemDamage());
			}
		}
	}
	
	public static boolean hasRecipe(ItemStack stack) {
		return getRecipeOutput(stack) != null;
	}

	public static void addRecipes() {
		//add fuel
		addFuel(new ItemStack(Block.wood.blockID, 1, 0), 1);
		
		//add recipes
		addRecipe(new ItemStack(Item.beefRaw.itemID, 1, 0), new ItemStack(Item.beefCooked.itemID, 1, 0), new ItemStack(Item.gunpowder, 1, 0));
	}
}
