package elcon.mods.agecraft.core.clothing;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import elcon.mods.agecraft.ACLog;

public class ClothingRegistry {

	public static class ClothingType {

		public String name;
		public int index;
		public int renderIndex;
		
		public ClothingType(String name, int index, int renderIndex) {
			this.name = name;
			this.index = index;
			this.renderIndex = renderIndex;
		}
	}
	
	public static class ClothingTypeIndexComparator implements Comparator<ClothingType> {
		
		@Override
		public int compare(ClothingType a, ClothingType b) {
			if(a.index <= b.index) {
				return -1;
			}
			return 1;
		}
	}
	
	public static class ClothingTypeRenderIndexComparator implements Comparator<String> {

		public HashMap<String, ClothingType> map = new HashMap<String, ClothingType>();
		
		public ClothingTypeRenderIndexComparator(HashMap<String, ClothingType> map) {
			this.map = map;
		}
		
		@Override
		public int compare(String a, String b) {
			if(map.get(a).renderIndex <= map.get(b).renderIndex) {
				return -1;
			}
			return 1;
		}
	}
	
	public static HashMap<String, ClothingType> types = new HashMap<String, ClothingType>();
	public static TreeMap<String, ClothingType> typesSorted;
	public static HashMap<String, ClothingCategory> categories = new HashMap<String, ClothingCategory>();
	
	public static void registerClothingType(ClothingType type) {
		if(types.get(type.name) != null) {
			ACLog.warning("[ClothingRegistry] Overriding existing clothing type (" + types.get(type.name).name.toUpperCase() + ") with new clothing type (" + type.name.toUpperCase() + ")");
		}
		types.put(type.name, type);
	}
	
	public static void registerClothingCategory(ClothingCategory category) {
		if(categories.get(category.name) != null) {
			ACLog.warning("[ClothingRegistry] Overriding existing clothing category (" + categories.get(category.name).name.toUpperCase() + ") with new clothing category (" + category.name.toUpperCase() + ")");
		}
		categories.put(category.name, category);
	}
	
	public static void registerClothing(Clothing clothing) {
		clothing.category.registerClothing(clothing);
	}

	public static ClothingType getClothingType(String name) {
		return types.get(name);
	}
	
	public static ClothingCategory getClothingCategory(String name) {
		return categories.get(name);
	}

	public static void sortClothingTypes() {
		ClothingTypeRenderIndexComparator comparator = new ClothingTypeRenderIndexComparator(types);
		typesSorted = new TreeMap<String, ClothingType>(comparator);
		typesSorted.putAll(types);
	}
}
