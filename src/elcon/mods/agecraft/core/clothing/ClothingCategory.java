package elcon.mods.agecraft.core.clothing;

import java.util.ArrayList;
import java.util.HashMap;

import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;
import elcon.mods.core.lang.LanguageManager;

public class ClothingCategory {

	public String name;
	public String versionURL;
	public String updateURL;
	public ArrayList<String> expansionURLs = new ArrayList<String>();
	
	public HashMap<ClothingType, HashMap<String, Clothing>> clothing = new HashMap<ClothingType, HashMap<String, Clothing>>();
	
	public ClothingCategory(String name, String versionURL, String updateURL) {
		this.name = name;
		this.versionURL = versionURL;
		this.updateURL = updateURL;
	}
	
	public String getLocalizedName() {
		return LanguageManager.getLocalization("clothing.categories." + name);
	}
	
	public void registerClothing(Clothing c) {
		if(!clothing.containsKey(c.type)) {
			clothing.put(c.type, new HashMap<String, Clothing>());
		}
		HashMap<String, Clothing> clothings = clothing.get(c.type);
		if(clothings.get(c.name) != null) {
			ACLog.warning("[ClothingRegistry] Overriding existing clothing (" + clothings.get(c.name).name.toUpperCase() + ") with new clothing (" + c.name.toUpperCase() + ")");
		}
		clothings.put(c.name, c);
	}
	
	public Clothing getClothing(ClothingType type, String name) {
		if(clothing.containsKey(type)) {
			return clothing.get(type).get(name);
		}
		return null;
	}
	
	public void addExpansionURL(String url) {
		expansionURLs.add(url);
	}
	
	public void removeExpansionURL(String url) {
		expansionURLs.remove(url);
	}
}
