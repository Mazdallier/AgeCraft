package org.agecraft.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RecipeSimple extends Recipe {

	@Override
	public Map<List<WrappedStack>, List<WrappedStack>> getRecipes() {
		HashMap<List<WrappedStack>, List<WrappedStack>> map = new HashMap<List<WrappedStack>, List<WrappedStack>>();
		map.put(getInput(), getOutput());
		return map;
	}

	public abstract List<WrappedStack> getInput();
	
	public abstract List<WrappedStack> getOutput();
}
