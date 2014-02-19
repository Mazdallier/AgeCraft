package org.agecraft.core.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.AgeCraft;

public class ArmorRegistry {

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
	}
	
	public static class ArmorMaterial {
		
		public int id;
		public String name;
		public String localization;
		
		public ItemStack stack;
		
		public boolean hasOverlay;
		public boolean hasColors;
		public int defaultColor;
		
		public int durability;
		
		public IIcon[] icons = new IIcon[128];
		public IIcon[] iconsOverlay = new IIcon[128];
		
		public ArmorMaterial(int id, String name, String localization, ItemStack stack, boolean hasOverlay, boolean hasColors, int defaultColor, int durability) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
			
			this.hasOverlay = hasOverlay;
			this.hasColors = hasColors;
			this.defaultColor = defaultColor;
			
			this.durability = durability;
		}
	}
	
	public static ArmorType[] armorTypes = new ArmorType[128];
	public static ArmorMaterial[] armorMaterials = new ArmorMaterial[256];
	
	public static void registerArmorType(ArmorType armorType) {
		if(armorTypes[armorType.id] != null) {
			AgeCraft.log.warn("[ArmorRegistry] Overriding existing armor type (" + armorTypes[armorType.id].id + ": " + armorTypes[armorType.id].name.toUpperCase() + ") with new armor type (" + armorType.id + ": " + armorType.name.toUpperCase() + ")");
		}
		armorTypes[armorType.id]= armorType;
	}
	
	public static void registerArmorMaterial(ArmorMaterial armorMaterial) {
		if(armorMaterials[armorMaterial.id] != null) {
			AgeCraft.log.warn("[ArmorRegistry] Overriding existing armor material (" + armorMaterials[armorMaterial.id].id + ": " + armorMaterials[armorMaterial.id].name.toUpperCase() + ") with new armor material (" + armorMaterial.id + ": " + armorMaterial.name.toUpperCase() + ")");
		}
		armorMaterials[armorMaterial.id]= armorMaterial;
	}
}
