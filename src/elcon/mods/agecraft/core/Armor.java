package elcon.mods.agecraft.core;

import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorType;
import elcon.mods.agecraft.core.items.armor.ItemArmor;

public class Armor extends ACComponent {

	public static ItemArmor helmet;
	public static ItemArmor chestplate;
	public static ItemArmor leggings;
	public static ItemArmor boots;
	
	@Override
	public void preInit() {
		
	}
	
	@Override
	public void init() {
		ArmorRegistry.registerArmorType(new ArmorType(0, "helmet", true));
		ArmorRegistry.registerArmorType(new ArmorType(1, "chestplate", true));
		ArmorRegistry.registerArmorType(new ArmorType(2, "leggings", false));
		ArmorRegistry.registerArmorType(new ArmorType(3, "boots", false));
	}
}
