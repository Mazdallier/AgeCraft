package org.agecraft.core;

import net.minecraft.item.Item;

import org.agecraft.ACComponent;
import org.agecraft.core.items.food.FoodStage;
import org.agecraft.core.items.food.ItemFish;
import org.agecraft.core.items.food.ItemFruit;
import org.agecraft.core.items.food.ItemMeat;
import org.agecraft.core.items.food.ItemVegetable;
import org.agecraft.core.registry.FishRegistry;
import org.agecraft.core.registry.FishRegistry.Fish;
import org.agecraft.core.registry.FruitRegistry;
import org.agecraft.core.registry.FruitRegistry.Fruit;
import org.agecraft.core.registry.MeatRegistry;
import org.agecraft.core.registry.MeatRegistry.Meat;
import org.agecraft.core.registry.VegetableRegistry;
import org.agecraft.core.registry.VegetableRegistry.Vegetable;

import cpw.mods.fml.common.registry.GameRegistry;

public class Food extends ACComponent {

	public static Item fruit;
	public static Item vegetable;
	public static Item meatRaw;
	public static Item meatCooked;
	public static Item meatBurned;
	public static Item fishRaw;
	public static Item fishCooked;
	public static Item fishBurned;

	public Food() {
		super("food", false);
	}

	@Override
	public void preInit() {
		// init items
		fruit = new ItemFruit().setUnlocalizedName("AC_food_fruit");
		vegetable = new ItemVegetable().setUnlocalizedName("AC_food_vegetable");
		meatRaw = new ItemMeat(FoodStage.RAW).setUnlocalizedName("AC_food_meatRaw");
		meatCooked = new ItemMeat(FoodStage.COOKED).setUnlocalizedName("AC_food_meatCooked");
		meatBurned = new ItemMeat(FoodStage.BURNED).setUnlocalizedName("AC_food_meatBurned");
		fishRaw = new ItemFish(FoodStage.RAW).setUnlocalizedName("AC_food_fishRaw");
		fishCooked = new ItemFish(FoodStage.COOKED).setUnlocalizedName("AC_food_fishCooked");
		fishBurned = new ItemFish(FoodStage.BURNED).setUnlocalizedName("AC_food_fishBurned");

		// register items
		GameRegistry.registerItem(fruit, "AC_food_fruit");
		GameRegistry.registerItem(vegetable, "AC_food_vegetable");
		GameRegistry.registerItem(meatRaw, "AC_food_meatRaw");
		GameRegistry.registerItem(meatCooked, "AC_food_meatCooked");
		GameRegistry.registerItem(meatBurned, "AC_food_meatBurned");
		GameRegistry.registerItem(fishRaw, "AC_food_fishRaw");
		GameRegistry.registerItem(fishCooked, "AC_food_fishCooked");
		GameRegistry.registerItem(fishBurned, "AC_food_fishBurned");

		// register fruit
		FruitRegistry.registerFruit(new Fruit(0, "appleRed", 4, 0.3F));
		FruitRegistry.registerFruit(new Fruit(1, "appleGreen", 4, 0.3F));

		// register vegetables
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
		FishRegistry.registerFish(new Fish(3, "pufferfish", 4, 0.5F));
	}
}
