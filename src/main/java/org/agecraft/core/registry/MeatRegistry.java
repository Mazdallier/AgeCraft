package org.agecraft.core.registry;

import net.minecraft.util.IIcon;

import org.agecraft.core.items.food.FoodStage;
import org.agecraft.core.registry.MeatRegistry.Meat;

public class MeatRegistry extends FoodRegistry<Meat> {

	public static class Meat extends org.agecraft.core.registry.FoodRegistry.Food {
		
		public IIcon iconRaw;
		public IIcon iconCooked;
		public IIcon iconBurned;
		
		public Meat(int id, String name, int health, float saturation) {
			super(id, name, health, saturation);
		}
		
		public Meat setAlwaysEdible() {
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
	
	public static MeatRegistry instance = new MeatRegistry();
	
	public MeatRegistry() {
		super(512);
	}
	
	public static void registerMeat(Meat meat) {
		instance.set(meat.id, meat);
	}
}
