package org.agecraft.core.registry;

public abstract class FoodRegistry<T> extends Registry<T> {

	public static abstract class Food {

		public int id;
		public String name;
		
		public int itemUseDuration;
		
		public int health;
		public float saturation;
		public boolean alwaysEdible;

		public int potionID;
		public int potionDuration;
		public int potionAmplifier;
		public float potionChance;
		
		public Food(int id, String name, int health, float saturation) {
			this(id, name, health, saturation, 32);
		}
		
		public Food(int id, String name, int health, float saturation, int itemUseDuration) {
			this.id = id;
			this.name = name;
			
			this.itemUseDuration = itemUseDuration;
			
			this.health = health;
			this.saturation = saturation;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}

	public FoodRegistry(int maxSize) {
		super(maxSize);
	}
}
