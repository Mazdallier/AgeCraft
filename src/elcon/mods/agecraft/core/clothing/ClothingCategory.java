package elcon.mods.agecraft.core.clothing;

import java.util.HashMap;

import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;
import elcon.mods.core.lang.LanguageManager;

public class ClothingCategory {

	public int id;
	public String name;
	public String updateURL;
	
	public HashMap<ClothingType, Clothing[]> clothing = new HashMap<ClothingType, Clothing[]>();
	
	public String getLocalizedName() {
		return LanguageManager.getLocalization("clothing.categories." + name);
	}
}
