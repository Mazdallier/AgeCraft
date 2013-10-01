package elcon.mods.agecraft.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import elcon.mods.agecraft.ACLog;

public class ArmorRegistry {

	public static class ArmorType {
		
		public int id;
		public String name;
		
		public boolean hasHeraldry;
		
		public Icon backgroundIcon;
		
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
		
		public boolean hasColors;
		public int defaultColor;
		
		public int durability;
		
		public Icon[] icons = new Icon[128];
		
		public ArmorMaterial(int id, String name, String localization, ItemStack stack, boolean hasColors, int defaultColor, int durability) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
			
			this.hasColors = hasColors;
			this.defaultColor = defaultColor;
			
			this.durability = durability;
		}
	}
	
	public static ArmorType[] armorTypes = new ArmorType[128];
	public static ArmorMaterial[] armorMaterials = new ArmorMaterial[256];
	
	public static void registerArmorType(ArmorType armorType) {
		if(armorTypes[armorType.id] != null) {
			ACLog.warning("[ArmorRegistry] Overriding existing armor type (" + armorTypes[armorType.id] + ": " + armorTypes[armorType.id].name.toUpperCase() + ") with new armor type (" + armorType.id + ": " + armorType.name.toUpperCase() + ")");
		}
		armorTypes[armorType.id]= armorType;
	}
	
	public static void registerArmorMaterial(ArmorMaterial armorMaterial) {
		if(armorMaterials[armorMaterial.id] != null) {
			ACLog.warning("[ArmorRegistry] Overriding existing armor material (" + armorMaterials[armorMaterial.id] + ": " + armorMaterials[armorMaterial.id].name.toUpperCase() + ") with new armor material (" + armorMaterial.id + ": " + armorMaterial.name.toUpperCase() + ")");
		}
		armorMaterials[armorMaterial.id]= armorMaterial;
	}
}
