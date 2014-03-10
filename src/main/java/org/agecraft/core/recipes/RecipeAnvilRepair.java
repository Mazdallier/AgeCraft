package org.agecraft.core.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import elcon.mods.elconqore.gui.InventoryBasic;

public class RecipeAnvilRepair extends Recipe {

	public boolean matches(InventoryBasic inventory) {
		return false;
	}
	
	public ItemStack getOutput(InventoryBasic inventory) {
		return null;
	}
	
	@Override
	public Map<List<WrappedStack>, List<WrappedStack>> getRecipes() {
		HashMap<List<WrappedStack>, List<WrappedStack>> map = new HashMap<List<WrappedStack>, List<WrappedStack>>();
		return map;
	}

	@Override
	public RecipeType getRecipeType() {
		return RecipeType.SMITHING;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}
}
