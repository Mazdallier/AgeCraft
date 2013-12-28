package elcon.mods.agecraft.core.fluids;

import net.minecraftforge.fluids.Fluid;
import elcon.mods.core.lang.LanguageManager;

public class FluidName extends Fluid {

	public FluidName(String fluidName) {
		super(fluidName);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
}
