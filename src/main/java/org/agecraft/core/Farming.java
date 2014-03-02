package org.agecraft.core;

import net.minecraft.item.Item;

import org.agecraft.ACComponent;
import org.agecraft.core.items.farming.ItemFood;
import org.agecraft.core.registry.FoodRegistry;
import org.agecraft.core.registry.FoodRegistry.Food;
import org.agecraft.core.registry.FoodRegistry.FoodProperties;
import org.agecraft.core.registry.FoodRegistry.FoodStage;
import org.agecraft.core.registry.FoodRegistry.FoodType;

import cpw.mods.fml.common.registry.GameRegistry;

public class Farming extends ACComponent {

	public static Item food;

	public Farming() {
		super("food", false);
	}

	@Override
	public void preInit() {
		// init items
		food = new ItemFood().setUnlocalizedName("AC_food_food");

		// register items
		GameRegistry.registerItem(food, "AC_food_food");				
		
		//register fruit
		FoodRegistry.registerFood(new Food(0, "appleRed", FoodType.FRUIT).setProperties(FoodStage.RAW, new FoodProperties(4, 0.3F)));
		FoodRegistry.registerFood(new Food(1, "appleGreen", FoodType.FRUIT).setProperties(FoodStage.RAW, new FoodProperties(4, 0.3F)));

		/*// register vegetables
		VegetableRegistry.registerVegetable(new Vegetable(0, "tomato", 4, 0.3F));
		VegetableRegistry.registerVegetable(new Vegetable(1, "potato", 1, 0.3F));
		VegetableRegistry.registerVegetable(new Vegetable(2, "carrot", 4, 0.6F));

		// register meat
		MeatRegistry.registerMeat(new Meat(0, "pork", 8, 0.8F).setAlwaysEdible());
		MeatRegistry.registerMeat(new Meat(1, "beef", 8, 0.8F).setAlwaysEdible());
		MeatRegistry.registerMeat(new Meat(2, "chicken", 6, 0.6F).setAlwaysEdible());

		// register fish
		FishRegistry.registerFish(new Fish(0, "cod", 5, 0.6F));
		FishRegistry.registerFish(new Fish(1, "salmon", 6, 0.8F));
		FishRegistry.registerFish(new Fish(2, "clownfish", 4, 0.5F));
		FishRegistry.registerFish(new Fish(3, "pufferfish", 4, 0.5F));*/
	}
}
