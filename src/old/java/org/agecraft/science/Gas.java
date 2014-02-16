package org.agecraft.science;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraftforge.fluids.FluidStack;

public class Gas {
	
	public static HashMap<String, Gas> gasList = new HashMap<String, Gas>();

	public String name;
	public FluidStack gas;
	public ArrayList<FluidStack> equalGasses = new ArrayList<FluidStack>();
	
	public double density;

	public Gas(String name, FluidStack gas, double density) {
		this.name = name;
		this.gas = gas;
		
		this.density = density;
	}
	
	public Gas(String name, FluidStack gas, ArrayList<FluidStack> equalGasses, double density) {
		this(name, gas, density);
		this.equalGasses.addAll(equalGasses);
	}
	
	public static Gas get(String name) {
		return gasList.get(name);
	}
	
	public static void registerGas(Gas gas) {
		gasList.put(gas.name, gas);
	}
	
	public static void registerGasses() {
		registerGas(new Gas("water", null, 0.598));
	}
}
