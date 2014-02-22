package org.agecraft.core.registry;

import net.minecraft.util.IIcon;

import org.agecraft.core.registry.ArmorTypeRegistry.ArmorType;

public class ArmorTypeRegistry extends Registry<ArmorType> {

	public static class ArmorType {
		
		public int id;
		public String name;
		
		public boolean hasHeraldry;
		
		public IIcon backgroundIcon;
		
		public ArmorType(int id, String name, boolean hasHeraldry) {
			this.id = id;
			this.name = name;
			this.hasHeraldry = hasHeraldry;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static ArmorTypeRegistry instance = new ArmorTypeRegistry();
	
	public ArmorTypeRegistry() {
		super(128);
	}
	
	public static void registerArmorType(ArmorType armorType) {
		instance.set(armorType.id, armorType);
	}
}
