package org.agecraft.core.recipes;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.agecraft.ACUtil;
import org.agecraft.core.gui.InventoryCraftMatrix;

public abstract class RecipeShaped extends RecipeSimple {

	public ItemStack[] input;
	public ItemStack output;
	public int recipeWidth;
	public int recipeHeight;
	
	public RecipeShaped(ItemStack output, Object... input) {
		super();
		this.output = output;

		String recipeString = "";
		int i = 0;
		int j = 0;
		int k = 0;
		if(input[i] instanceof String[]) {
			String[] strings = (String[]) input[i++];
			for(int l = 0; l < strings.length; l++) {
				String s = strings[l];
				k++;
				j = s.length();
				recipeString += s;
			}
		} else {
			while(input[i] instanceof String) {
				String s = (String) input[i++];
				k++;
				j = s.length();
				recipeString += s;
			}
		}
		HashMap<Character, ItemStack> map;
		for(map = new HashMap(); i < input.length; i += 2) {
			Character c = (Character) input[i];
			ItemStack stack = null;
			if(input[i + 1] instanceof Item) {
				stack = new ItemStack((Item) input[i + 1]);
			} else if(input[i + 1] instanceof Block) {
				stack = new ItemStack((Block) input[i + 1]);
			} else if(input[i + 1] instanceof ItemStack) {
				stack = (ItemStack) input[i + 1];
			}
			map.put(c, stack);
		}
		this.recipeWidth = j;
		this.recipeHeight = k;
		this.input = new ItemStack[j * k];
		for(int l = 0; l < j * k; l++) {
			char c = recipeString.charAt(l);
			if(map.containsKey(Character.valueOf(c))) {
				this.input[l] = ((ItemStack) map.get(Character.valueOf(c))).copy();
			} else {
				this.input[l] = null;
			}
		}
	}

	public boolean matches(InventoryCraftMatrix inventory) {
		for(int i = 0; i <= inventory.width - recipeWidth; i++) {
			for(int j = 0; j <= inventory.height - recipeHeight; j++) {
				if(checkMatch(inventory, i, j, true)) {
					return true;
				}
				if(checkMatch(inventory, i, j, false)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkMatch(InventoryCraftMatrix inventory, int offsetX, int offsetY, boolean mirror) {
		for(int i = 0; i < inventory.width; i++) {
			for(int j = 0; j < inventory.height; j++) {
				int x = i - offsetX;
				int y = j - offsetY;
				ItemStack stack = null;
				if(x >= 0 && y >= 0 && x < recipeWidth && y < recipeHeight) {
					if(mirror) {
						stack = input[recipeWidth - x - 1 + y * recipeWidth];
					} else {
						stack = input[x + y * recipeWidth];
					}
				}
				ItemStack currentStack = inventory.getStackInRowAndColumn(x, y);
				if(!ACUtil.areItemStacksEqualNoSize(stack, currentStack)) {
					return false;
				}
			}
		}
		return true;
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
	public int getRecipeSize() {
		return input.length;
	}
}
