package org.agecraft.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.core.entity.EntityDrop;
import org.agecraft.core.entity.animals.EntityChicken;
import org.agecraft.core.entity.animals.EntityCow;
import org.agecraft.core.entity.animals.EntityPig;
import org.agecraft.core.entity.animals.EntitySheep;
import org.agecraft.core.items.farming.ItemFood;
import org.agecraft.core.registry.AnimalRegistry;
import org.agecraft.core.registry.AnimalRegistry.Animal;
import org.agecraft.core.registry.FoodRegistry.FoodStage;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Animals extends ACComponent {

	public Animals() {
		super("animals", false);
	}

	@Override
	public void preInit() {
		// register animals
		AnimalRegistry.registerAnimal(new Animal(0, "pig", EntityPig.class, new EntityDrop(ItemFood.createFood("pork", FoodStage.RAW), 0, 3).setShouldNotBurn(true), new EntityDrop(ItemFood.createFood("pork", FoodStage.COOKED), 0, 3).setShouldBurn(true)));
		AnimalRegistry.registerAnimal(new Animal(1, "cow", EntityCow.class, new EntityDrop(new ItemStack(Items.leather), 0, 3), new EntityDrop(ItemFood.createFood("beef", FoodStage.RAW), 1, 3).setShouldNotBurn(true), new EntityDrop(ItemFood.createFood("beef", FoodStage.COOKED), 1, 3).setShouldBurn(true)));
		AnimalRegistry.registerAnimal(new Animal(2, "chicken", EntityChicken.class, new EntityDrop(new ItemStack(Items.feather), 0, 3), new EntityDrop(ItemFood.createFood("chicken", FoodStage.RAW), 0, 1).setShouldNotBurn(true), new EntityDrop(ItemFood.createFood("chicken", FoodStage.COOKED), 0, 1).setShouldBurn(true)));
		AnimalRegistry.registerAnimal(new Animal(3, "sheep", EntitySheep.class, new EntityDrop(new ItemStack(Blocks.wool), 0, 1)));

		// register entities
		EntityRegistry.registerGlobalEntityID(EntityPig.class, "AC_Pig", EntityRegistry.findGlobalUniqueEntityId(), 0xF0A5A2, 0xDB635F);
		EntityRegistry.registerGlobalEntityID(EntityCow.class, "AC_Cow", EntityRegistry.findGlobalUniqueEntityId(), 0x443626, 0xA1A1A1);
		EntityRegistry.registerGlobalEntityID(EntityChicken.class, "AC_Chicken", EntityRegistry.findGlobalUniqueEntityId(), 0xA1A1A1, 0xFF0000);
		EntityRegistry.registerGlobalEntityID(EntitySheep.class, "AC_Sheep", EntityRegistry.findGlobalUniqueEntityId(), 0xE7E7E7, 0xFFB5B5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ACComponentClient getComponentClient() {
		return AnimalsClient.instance != null ? AnimalsClient.instance : new AnimalsClient(this);
	}
}
