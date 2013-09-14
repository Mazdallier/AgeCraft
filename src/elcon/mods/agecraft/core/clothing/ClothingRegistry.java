package elcon.mods.agecraft.core.clothing;

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
}
