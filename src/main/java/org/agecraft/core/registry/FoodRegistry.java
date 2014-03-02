package org.agecraft.core.registry;

import java.util.HashMap;

import net.minecraft.util.IIcon;

import org.agecraft.core.registry.FoodRegistry.Food;

import elcon.mods.elconqore.EQUtil;

public class FoodRegistry extends Registry<Food> {

	public static enum FoodType {
		FRUIT,
		VEGETABLES,
		MEAT,
		FISH;
	}
	
	public static enum FoodStage {
		RAW,
		COOKED,
		BURNED;
	}
	
	public static class FoodProperties {
		
		public FoodStage foodStage;
		
		public String prefix;
		public String postfix;
		public String iconPostfix;
		
		public int health;
		public float saturation;
		public boolean alwaysEdible;
		
		public int itemUseDuration;
		
		public int potionID;
		public int potionDuration;
		public int potionAmplifier;
		public float potionChance;
		
		public IIcon icon;
		
		public FoodProperties(int health, float saturtation) {
			this(FoodStage.RAW, health, saturtation);
			resetPrePostfix();
		}
		
		public FoodProperties(FoodStage foodStage, int health, float saturation) {
			this.foodStage = foodStage;
			
			this.prefix = "food." + foodStage.toString().toLowerCase();
			this.iconPostfix = EQUtil.firstUpperCase(foodStage.toString().toLowerCase());
			
			this.health = health;
			this.saturation = saturation;
			this.alwaysEdible = false;
			
			this.itemUseDuration = 32;
		}
		
		public FoodProperties setPrefix(String prefix) {
			this.prefix = prefix;
			return this;
		}
		
		public FoodProperties setPostfix(String postfix) {
			this.postfix = postfix;
			return this;
		}
		
		public FoodProperties resetPrePostfix() {
			this.prefix = "";
			this.postfix = "";
			this.iconPostfix = "";
			return this;
		}
		
		public FoodProperties setAlwaysEdible() {
			alwaysEdible = true;
			return this;
		}
		
		public FoodProperties setItemUseDuration(int itemUseDuration) {
			this.itemUseDuration = itemUseDuration;
			return this;
		}
		
		public FoodProperties setPotionEffect(int potionID, int potionDuration, int potionAmplifier, float potionChance) {
			this.potionID = potionID;
			this.potionDuration = potionDuration;
			this.potionAmplifier = potionAmplifier;
			this.potionChance = potionChance;
			return this;
		}
	}
	
	public static class Food {

		public int id;
		public String name;
		public FoodType type;
		
		public HashMap<FoodStage, FoodProperties> properties = new HashMap<FoodStage, FoodProperties>();
		
		public Food(int id, String name, FoodType type) {
			this.id = id;
			this.name = name;
			this.type = type;
		}
		
		public Food setProperties(FoodStage foodStage, FoodProperties foodProperties) {
			properties.put(foodStage, foodProperties);
			return this;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static FoodRegistry instance = new FoodRegistry();

	public FoodRegistry() {
		super(8192);
	}
	
	public static void registerFood(Food food) {
		instance.set(food.id, food);
	}
}
