package elcon.mods.agecraft.core.clothing;

import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;
import elcon.mods.core.lang.LanguageManager;

public class Clothing {
	
	public int id;
	public String name;
	public ClothingType type;
	public int category;
	public boolean[] colors = new boolean[16];
	
	public Clothing(int id, String name, ClothingType type, int category) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.category = category;
		enableAllColors();
	}
	
	public Clothing(int id, String name, ClothingType type, int category, boolean[] colors) {
		this(id, name, type, category);
		this.colors = colors;
	}
	
	public String getFileLocation(int color) {
		return ClothingRegistry.categories[category].name + "/" + type.name + "/" + getFileName(color);
	}
	
	public String getFileName(int color) {
		return name + "_" + color + ".png";
	}
	
	public String getLocalizedName() {
		return LanguageManager.getLocalization("clothing." + category + "." + type.name + "." + ClothingRegistry.categories[category].name);
	}
	
	public void enableColor(int color) {
		colors[color] = true;
	}
	
	public void disableColor(int color) {
		colors[color] = false;
	}
	
	public void enableColors(int... c) {
		for(int i = 0; i < c.length; i++) {
			colors[c[i]] = true;
		}
	}
	
	public void disableColors(int... c) {
		for(int i = 0; i < c.length; i++) {
			colors[c[i]] = false;
		}
	}
	
	public void enableAllColors() {
		for(int i = 0; i < 16; i++) {
			colors[i] = true;
		}
	}

	public void disableAllColors() {
		for(int i = 0; i < 16; i++) {
			colors[i] = false;
		}
	}
}
