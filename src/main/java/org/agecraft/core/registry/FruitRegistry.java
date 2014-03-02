package org.agecraft.core.registry;

import net.minecraft.util.IIcon;

import org.agecraft.core.registry.FruitRegistry.Fruit;

public class FruitRegistry extends FoodRegistry<Fruit> {

	public static class Fruit extends org.agecraft.core.registry.FoodRegistry.Food {

		public IIcon icon;
		
		public Fruit(int id, String name, int health, float saturation) {
			super(id, name, health, saturation);
		}
		
		public Fruit setAlwaysEdible() {
			alwaysEdible = true;
			return this;
		}
	}
	
	public static FruitRegistry instance = new FruitRegistry();
	
	public FruitRegistry() {
		super(1024);
	}
	
	public static void registerFruit(Fruit fruit) {
		instance.set(fruit.id, fruit);
	}
}
