package elcon.mods.agecraft.core.clothing;

import elcon.mods.agecraft.ACLog;

public class ClothingRegistry {

	public static class ClothingType {

		public int id;
		public String name;
		
		public boolean changeableStandard;
		
		public ClothingType(int id, String name, boolean changeableStandard) {
			this.id = id;
			this.name = name;
			this.changeableStandard = changeableStandard;
		}
	}
	
	public static ClothingType[] types = new ClothingType[16];
	public static ClothingCategory[] categories = new ClothingCategory[64];
	
	public static void registerClothingType(ClothingType type) {
		if(types[type.id] != null) {
			ACLog.warning("[ClothingRegistry] Overriding existing clothing type (" + types[type.id] + ": " + types[type.id].name.toUpperCase() + ") with new clothing type (" + type.id + ": " + type.name.toUpperCase() + ")");
		}
		types[type.id]= type;
	}
	
	public static void registerClothingCategory(ClothingCategory category) {
		if(categories[category.id] != null) {
			ACLog.warning("[ClothingRegistry] Overriding existing clothing category (" + categories[category.id] + ": " + categories[category.id].name.toUpperCase() + ") with new clothing category (" + category.id + ": " + category.name.toUpperCase() + ")");
		}
		categories[category.id]= category;
	}
	
	public static void registerClothing(Clothing clothing) {
		clothing.category.registerClothing(clothing);
	}

	public static ClothingType getClothingType(String name) {
		for(int i = 0; i < types.length; i++) {
			if(types[i] != null && types[i].name.equals(name)) {
				return types[i];
			}
		}
		return null;
	}
	
	public static ClothingCategory getClothingCategory(String name) {
		for(int i = 0; i < categories.length; i++) {
			if(categories[i] != null && categories[i].name.equals(name)) {
				return categories[i];
			}
		}
		return null;
	}
}
