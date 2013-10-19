package elcon.mods.agecraft.core.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.gui.InventoryCraftMatrix;
import elcon.mods.agecraft.recipes.Recipe;
import elcon.mods.agecraft.recipes.RecipeType;

public class RecipeWorkbenchShaped extends Recipe {

	public ItemStack[] input;
	public ItemStack output;
	public int recipeWidth;
	public int recipeHeight;
	public boolean requiresHammer;
	public boolean requiresSaw;
	public int hammerDamage;
	public int sawDamage;
	public int hammerHarvestLevel;
	public int sawHarvestLevel;

	public RecipeWorkbenchShaped(ItemStack output, int hammerDamage, int sawDamage, int hammerHarvestLevel, int sawHarvestLevel, Object... input) {
		this(output, hammerDamage, sawDamage, input);
		this.hammerHarvestLevel = hammerHarvestLevel;
		this.sawHarvestLevel = sawHarvestLevel;
	}
	
	public RecipeWorkbenchShaped(ItemStack output, Object... input) {
		this(output, -1, -1, input);
	}
	
	public RecipeWorkbenchShaped(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		super();
		this.output = output;
		this.hammerDamage = hammerDamage;
		this.sawDamage = sawDamage;
		this.requiresHammer = hammerDamage > 0;
		this.requiresSaw = sawDamage > 0;
		this.hammerHarvestLevel = 0;
		this.sawHarvestLevel = 0;

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
		for(int i = 0; i <= 3 - recipeWidth; i++) {
			for(int j = 0; j <= 3 - recipeHeight; j++) {
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

	private boolean checkMatch(InventoryCraftMatrix inventory, int offsetX, int offsetY, boolean mirror) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
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
	public List<ItemStack> getInput() {
		return Arrays.asList(input);
	}

	@Override
	public List<ItemStack> getOutput() {
		return Arrays.asList(output);
	}

	@Override
	public RecipeType getRecipeType() {
		return RecipeType.CRAFTING;
	}

	@Override
	public int getRecipeSize() {
		return input.length;
	}
}
