package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.core.entity.EntityDrop;
import org.agecraft.core.entity.animals.EntityPig;
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

		// register entities
		EntityRegistry.registerGlobalEntityID(EntityPig.class, "AC_Pig", EntityRegistry.findGlobalUniqueEntityId(), 0xF0A5A2, 0xDB635F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ACComponentClient getComponentClient() {
		return AnimalsClient.instance != null ? AnimalsClient.instance : new AnimalsClient(this);
	}
}
