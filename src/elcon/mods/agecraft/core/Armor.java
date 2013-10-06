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
		//init items
		helmet = (ItemArmor) new ItemHelmet(12600).setUnlocalizedName("armor_helmet");
		chestplate = (ItemArmor) new ItemChestplate(12600).setUnlocalizedName("armor_chestplate");
		leggings = (ItemArmor) new ItemLeggings(12600).setUnlocalizedName("armor_leggings");
		boots = (ItemArmor) new ItemBoots(12600).setUnlocalizedName("armor_boots");
	}
	
	@Override
	public void init() {
		//register armor types
		ArmorRegistry.registerArmorType(new ArmorType(0, "helmet", true));
		ArmorRegistry.registerArmorType(new ArmorType(1, "chestplate", true));
		ArmorRegistry.registerArmorType(new ArmorType(2, "leggings", false));
		ArmorRegistry.registerArmorType(new ArmorType(3, "boots", false));
		
		//register armor materials
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(128, "copper", "metals.copper", new ItemStack(Metals.ingot, 1, 0), false, false, 0xFFFFFF, 153));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.ingot, 1, 2), false, false, 0xFFFFFF, 277));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(131, "silver", "metals.silver", new ItemStack(Metals.ingot, 1, 3), false, false, 0xFFFFFF, 260));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(132, "iron", "metals.iron", new ItemStack(Metals.ingot, 1, 4), false, false, 0xFFFFFF, 314));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(133, "gold", "metals.gold", new ItemStack(Metals.ingot, 1, 5), false, false, 0xFFFFFF, 40));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.ingot, 1, 9), false, false, 0xFFFFFF, 443));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(139, "steel", "metals.steel", new ItemStack(Metals.ingot, 1, 11), false, false, 0xFFFFFF, 820));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.ingot, 1, 12), false, false, 0xFFFFFF, 1168));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.ingot, 1, 13), false, false, 0xFFFFFF, 1807));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.ingot, 1, 14), false, false, 0xFFFFFF, 2352));
	}
}
