package elcon.mods.agecraft.core.clothing;

import java.util.HashMap;

import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;
import elcon.mods.core.lang.LanguageManager;

public class ClothingCategory {

	public int id;
	public String name;
	public String versionURL;
	public String updateURL;
	
	public HashMap<ClothingType, Clothing[]> clothing = new HashMap<ClothingType, Clothing[]>();
	
	public ClothingCategory(int id, String name, String versionURL, String updateURL) {
		this.id = id;
		this.name = name;
		this.versionURL = versionURL;
		this.updateURL = updateURL;
	}
	
	public String getLocalizedName() {
		return LanguageManager.getLocalization("clothing.categories." + name);
	}
	
	public void registerClothing(Clothing c) {
		if(!clothing.containsKey(c.type)) {
			
		}
		Clothing[] clothings = clothing.get(c.type);
		if(clothings[c.id] != null) {
			ACLog.warning("[ClothingRegistry] Overriding existing clothing (" + clothings[c.id] + ": " + clothings[c.id].name.toUpperCase() + ") with new clothing (" + c.id + ": " + c.name.toUpperCase() + ")");
		}
		clothings[c.id] = c;
	}
	
	public int getNextClothingID(ClothingType type) {
		if(!clothing.containsKey(type)) {
			clothing.put(type, new Clothing[1024]);
			return 0;
		}
		Clothing[] clothings = clothing.get(type);
		for(int i = 0; i < clothings.length; i++) {
			if(clothings[i] != null) {
				return i;
			}
		}
		return -1;
	}
	
	public Clothing getClothing(ClothingType type, int id) {
		if(clothing.containsKey(type)) {
			return clothing.get(type)[id];
		}
		return null;
	}
	
	public Clothing getClothing(ClothingType type, String name) {
		if(clothing.containsKey(type)) {
			Clothing[] clothings = clothing.get(type);
			for(int i = 0; i < clothings.length; i++) {
				if(clothings[i] != null && clothings[i].name.equalsIgnoreCase(name)) {
					return clothings[i];
				}
			}
		}
		return null;
	}
}
