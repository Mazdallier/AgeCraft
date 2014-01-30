package elcon.mods.agecraft.core.clothing;

import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;
import elcon.mods.core.lang.LanguageManager;

public class Clothing {
	
	public String name;
	public ClothingType type;
	public ClothingCategory category;
	public boolean[] colors = new boolean[16];
	public int price;
	public boolean defaultUnlocked;
	public boolean hideIfLocked;
	
	public Clothing(String name, ClothingType type, ClothingCategory category) {
		this.name = name;
		this.type = type;
		this.category = category;
		this.defaultUnlocked = true;
		this.hideIfLocked = false;
		disableAllColors();
	}
	
	public Clothing(String name, ClothingType type, ClothingCategory category, boolean[] colors) {
		this(name, type, category);
		this.colors = colors;
	}
	
	public void setHideIfLocked(boolean flag) {
		hideIfLocked = false;
	}
	
	public String getFileLocation(int color) {
		return category.name + "/" + type.name + "/" + getFileName(color);
	}
	
	public String getFileName(int color) {
		return name + "_" + color + ".png";
	}
	
	public String getLocalizedName() {
		return LanguageManager.getLocalization("clothing." + category.name + "." + type.name + "." + name);
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
