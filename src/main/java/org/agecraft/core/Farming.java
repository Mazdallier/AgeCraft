package org.agecraft.core;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

import org.agecraft.ACComponent;
import org.agecraft.core.items.farming.ItemFood;
import org.agecraft.core.registry.FoodRegistry;
import org.agecraft.core.registry.FoodRegistry.Food;
import org.agecraft.core.registry.FoodRegistry.FoodPotion;
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
		FoodRegistry.registerFood(new Food(0, "appleRed", FoodType.FRUIT).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 4, 0.3F).resetPrePostfix()));
		FoodRegistry.registerFood(new Food(1, "appleGreen", FoodType.FRUIT).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 4, 0.3F).resetPrePostfix()));

		FoodRegistry.registerFood(new Food(1024, "carrot", FoodType.VEGETABLES).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 4, 0.6F).resetPrePostfix()));
		FoodRegistry.registerFood(new Food(1025, "potato", FoodType.VEGETABLES).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 1, 0.3F).resetPrePostfix()).setProperties(FoodStage.COOKED, new FoodProperties(FoodStage.COOKED, 6, 0.6F).setPrefix("food.baked")));
		FoodRegistry.registerFood(new Food(1026, "tomato", FoodType.VEGETABLES).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 4, 0.3F).resetPrePostfix()));
		
		FoodRegistry.registerFood(new Food(2048, "pork", FoodType.MEAT).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 3, 0.3F)).setProperties(FoodStage.COOKED, new FoodProperties(FoodStage.COOKED, 8, 0.8F)).setProperties(FoodStage.BURNED, new FoodProperties(FoodStage.BURNED, 1, 0.1F).addPotionEffect(new FoodPotion(Potion.hunger.id, 30, 0, 0.8F))));
		FoodRegistry.registerFood(new Food(2049, "beef", FoodType.MEAT).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 3, 0.3F)).setProperties(FoodStage.COOKED, new FoodProperties(FoodStage.COOKED, 8, 0.8F)).setProperties(FoodStage.BURNED, new FoodProperties(FoodStage.BURNED, 1, 0.1F).addPotionEffect(new FoodPotion(Potion.hunger.id, 30, 0, 0.8F))));
		FoodRegistry.registerFood(new Food(2050, "chicken", FoodType.MEAT).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 2, 0.3F).addPotionEffect(new FoodPotion(Potion.hunger.id, 30, 0, 0.3F))).setProperties(FoodStage.COOKED, new FoodProperties(FoodStage.COOKED, 6, 0.6F)).setProperties(FoodStage.BURNED, new FoodProperties(FoodStage.BURNED, 1, 0.1F).addPotionEffect(new FoodPotion(Potion.hunger.id, 30, 0, 0.8F))));

		FoodRegistry.registerFood(new Food(3072, "cod", FoodType.FISH).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 2, 0.1F)).setProperties(FoodStage.COOKED, new FoodProperties(FoodStage.COOKED, 5, 0.6F)));
		FoodRegistry.registerFood(new Food(3073, "salmon", FoodType.FISH).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 2, 0.1F)).setProperties(FoodStage.COOKED, new FoodProperties(FoodStage.COOKED, 6, 0.8F)));
		FoodRegistry.registerFood(new Food(3074, "clownfish", FoodType.FISH).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 1, 0.1F)));
		FoodRegistry.registerFood(new Food(3075, "pufferfish", FoodType.FISH).setProperties(FoodStage.RAW, new FoodProperties(FoodStage.RAW, 1, 0.1F).addPotionEffect(new FoodPotion(Potion.poison.id, 1200, 3, 1.0F)).addPotionEffect(new FoodPotion(Potion.hunger.id, 300, 2, 1.0F)).addPotionEffect(new FoodPotion(Potion.confusion.id, 300, 1, 1.0F))));
	}
}
