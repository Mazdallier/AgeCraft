package elcon.mods.agecraft.recipes;

import java.util.Arrays;
import java.util.List;

public abstract class RecipeSimple extends Recipe {

	@Override
	public List<List<WrappedStack>> getInputs() {
		return Arrays.asList(getInput());
	}
	
	@Override
	public List<WrappedStack> getOutput(List<WrappedStack> input) {
		return getOutput();
	}

	public abstract List<WrappedStack> getInput();
	
	public abstract List<WrappedStack> getOutput();
}
