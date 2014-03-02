package org.agecraft.core.registry;

import net.minecraft.util.IIcon;

import org.agecraft.core.items.food.FoodStage;
import org.agecraft.core.registry.FishRegistry.Fish;

public class FishRegistry extends FoodRegistry<Fish> {

	public static class Fish extends org.agecraft.core.registry.FoodRegistry.Food {
		
		public IIcon iconRaw;
		public IIcon iconCooked;
		public IIcon iconBurned;
		
		public Fish(int id, String name, int health, float saturation) {
			super(id, name, health, saturation);
		}
		
		public Fish setAlwaysEdible() {
			alwaysEdible = true;
			return this;
		}
		
		public IIcon getIcon(FoodStage foodStage) {
			if(foodStage == FoodStage.RAW) {
				return iconRaw;
			} else if(foodStage == FoodStage.COOKED) {
				return iconCooked;
			} else if(foodStage == FoodStage.BURNED) {
				return iconBurned;
			}
			return null;
		}
	}
	
	public static FishRegistry instance = new FishRegistry();
	
	public FishRegistry() {
		super(512);
	}
	
	public static void registerFish(Fish fish) {
		instance.set(fish.id, fish);
	}
}
