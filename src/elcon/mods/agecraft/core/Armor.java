package elcon.mods.agecraft.core;

import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorMaterial;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorType;
import elcon.mods.agecraft.core.items.armor.ItemArmor;
import elcon.mods.agecraft.core.items.armor.ItemBoots;
import elcon.mods.agecraft.core.items.armor.ItemChestplate;
import elcon.mods.agecraft.core.items.armor.ItemHelmet;
import elcon.mods.agecraft.core.items.armor.ItemLeggings;

public class Armor extends ACComponent {

	public static ItemArmor helmet;
	public static ItemArmor chestplate;
	public static ItemArmor leggings;
	public static ItemArmor boots;
	
	@Override
	public void preInit() {
		helmet = (ItemArmor) new ItemHelmet(12600).setUnlocalizedName("armor_helmet");
		chestplate = (ItemArmor) new ItemChestplate(12600).setUnlocalizedName("armor_chestplate");
		leggings = (ItemArmor) new ItemLeggings(12600).setUnlocalizedName("armor_leggings");
		boots = (ItemArmor) new ItemBoots(12600).setUnlocalizedName("armor_boots");
	}
	
	@Override
	public void init() {
		ArmorRegistry.registerArmorType(new ArmorType(0, "helmet", true));
		ArmorRegistry.registerArmorType(new ArmorType(1, "chestplate", true));
		ArmorRegistry.registerArmorType(new ArmorType(2, "leggings", false));
		ArmorRegistry.registerArmorType(new ArmorType(3, "boots", false));
		
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(128, "copper", "metals.copper", new ItemStack(Metals.ingot, 1, 0), false, false, 0xFFFFFF, 144));
	}
}
