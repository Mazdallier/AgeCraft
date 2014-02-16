package org.agecraft.core.science;

import java.util.HashMap;

public class Matter {

	public static HashMap<String, Matter> matterList = new HashMap<String, Matter>();
	
	public String name;
	public Solid solid;
	public Liquid liquid;
	public Gas gas;
	
	public double meltingTemperature;
	public double boilingTemperature;
	
	public Matter(Solid solid, Liquid liquid, Gas gas, double meltingTemperature, double boilingTemperature) {
		this.solid = solid;
		this.liquid = liquid;
		this.gas = gas;
		this.meltingTemperature = meltingTemperature;
		this.boilingTemperature = boilingTemperature;
	}	
	
	public static void get(String name) {
		matterList.get(name);
	}
	
	public static void registerMatter(Matter matter) {
		matterList.put(matter.name, matter);
	}
	
	public static void registerMatter() {
		Solid.registerSolids();
		Liquid.registerLiquids();
		Gas.registerGasses();
		
		registerMatter(new Matter(Solid.get("water"), Liquid.get("water"), Gas.get("water"), 0.0, 100.0));
	}
}
