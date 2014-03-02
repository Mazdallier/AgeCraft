package org.agecraft.core.items.food;

public enum FoodStage {

	RAW("raw"),
	COOKED("cooked"),
	BURNED("burned");
	
	public String name;
	
	FoodStage(String name) {
		this.name = name;
	}
}
