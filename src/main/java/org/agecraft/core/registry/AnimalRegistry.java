package org.agecraft.core.registry;

import java.util.ArrayList;
import java.util.HashMap;

import org.agecraft.core.entity.EntityDrop;
import org.agecraft.core.entity.animals.EntityAnimal;
import org.agecraft.core.registry.AnimalRegistry.Animal;

public class AnimalRegistry extends Registry<Animal> {

	public static class Animal {
		
		public int id;
		public String name;
		public Class<? extends EntityAnimal> entity;
		
		public ArrayList<EntityDrop> drops = new ArrayList<EntityDrop>();
		
		public Animal(int id, String name, Class<? extends EntityAnimal> entity, EntityDrop... drops) {
			this.id = id;
			this.name = name;
			this.entity = entity;
			for(int i = 0; i < drops.length; i++) {
				this.drops.add(drops[i]);
			}
		}
		
		public void addDrop(EntityDrop drop) {
			drops.add(drop);
		}
		
		public void removeDrop(EntityDrop drop) {
			drops.remove(drop);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static AnimalRegistry instance = new AnimalRegistry();
	public HashMap<Class<? extends EntityAnimal>, Animal> classToObject = new HashMap<Class<? extends EntityAnimal>, Animal>();
	
	public AnimalRegistry() {
		super(256);
	}
	
	@Override
	public void set(int index, Animal value) {
		super.set(index, value);
		classToObject.put(value.entity, value);
	}
	
	@Override
	public void setAll(Animal[] registered) {
		super.setAll(registered);
		classToObject.clear();
		for(int i = 0; i < registered.length; i++) {
			if(registered[i] != null) {
				classToObject.put(registered[i].entity, registered[i]);
			}
		}
	}
	
	public static Animal get(Class<? extends EntityAnimal> entity) {
		return instance.classToObject.get(entity);
	}
	
	public static void registerAnimal(Animal animal) {
		instance.set(animal.id, animal);
	}
}
