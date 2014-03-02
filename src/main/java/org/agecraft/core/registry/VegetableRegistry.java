package org.agecraft.core.registry;

import net.minecraft.util.IIcon;

import org.agecraft.core.registry.VegetableRegistry.Vegetable;

public class VegetableRegistry extends FoodRegistry<Vegetable> {

	public static class Vegetable extends org.agecraft.core.registry.FoodRegistry.Food {
		
		public IIcon icon;
		
		public Vegetable(int id, String name, int health, float saturation) {
			super(id, name, health, saturation);
		}
		
		public Vegetable setAlwaysEdible() {
			alwaysEdible = true;
			return this;
		}
	}
	
	public static VegetableRegistry instance = new VegetableRegistry();
	
	public VegetableRegistry() {
		super(1024);
	}
	
	public static void registerVegetable(Vegetable vegetable) {
		instance.set(vegetable.id, vegetable);
	}
}
