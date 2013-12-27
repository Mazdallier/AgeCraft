package elcon.mods.agecraft.science;

import net.minecraftforge.fluids.FluidStack;

public class Liquid {

	public FluidStack liquid;

	public double density;

	public Liquid(FluidStack liquid, double density) {
		this.liquid = liquid;
		this.density = density;
	}
}
