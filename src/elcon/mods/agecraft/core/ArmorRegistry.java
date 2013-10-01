package elcon.mods.agecraft.core;

import net.minecraft.item.ItemStack;

public class ArmorRegistry {

	public static class ArmorType {
		
		public int id;
		public String name;
		
		public boolean hasHeraldry;
		
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
		
		public int durability;
		
		public ArmorMaterial(int id, String name, String localization, ItemStack stack, int durability) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
			
			this.durability = durability;
		}
	}
	
	public static ArmorType[] armorTypes = new ArmorType[128];
	public static ArmorMaterial[] armorMaterials = new ArmorMaterial[256];
}
