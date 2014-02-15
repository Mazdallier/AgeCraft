package org.agecraft.science;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Liquid {
	
	public static final int BLOCK_VOLUME_LITER = 1000;
	
	public static HashMap<String, Liquid> liquidList = new HashMap<String, Liquid>();

	public String name;
	public FluidStack liquid;
	public ArrayList<FluidStack> equalLiquids = new ArrayList<FluidStack>();

	public double density;

	public Liquid(String name, FluidStack liquid, double density) {
		this.name = name;
		this.liquid = liquid;
		
		this.density = density;
	}
	
	public Liquid(String name, FluidStack liquid, ArrayList<FluidStack> equalLiquids, double density) {
		this(name, liquid, density);
		this.equalLiquids.addAll(equalLiquids);
	}
	
	public static Liquid get(String name) {
		return liquidList.get(name);
	}
	
	public static void registerLiquid(Liquid liquid) {
		liquidList.put(liquid.name, liquid);
	}
	
	public static void registerLiquids() {
		registerLiquid(new Liquid("water", new FluidStack(FluidRegistry.WATER, BLOCK_VOLUME_LITER), 0.998));
	}
}
