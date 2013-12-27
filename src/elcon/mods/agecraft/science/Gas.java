package elcon.mods.agecraft.science;

import net.minecraftforge.fluids.FluidStack;

public class Gas {

	public FluidStack gas;
	
	public double density;

	public Gas(FluidStack gas, double density) {
		this.gas = gas;
		this.density = density;
	}
}
