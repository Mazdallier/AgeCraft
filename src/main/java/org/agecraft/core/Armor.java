package org.agecraft.core;

import net.minecraft.item.ItemStack;

import org.agecraft.ACComponent;
import org.agecraft.core.items.armor.ItemArmor;
import org.agecraft.core.items.armor.ItemBoots;
import org.agecraft.core.items.armor.ItemChestplate;
import org.agecraft.core.items.armor.ItemHelmet;
import org.agecraft.core.items.armor.ItemLeggings;
import org.agecraft.core.registry.ArmorMaterialRegistry;
import org.agecraft.core.registry.ArmorMaterialRegistry.ArmorMaterial;
import org.agecraft.core.registry.ArmorTypeRegistry;
import org.agecraft.core.registry.ArmorTypeRegistry.ArmorType;

import cpw.mods.fml.common.registry.GameRegistry;

public class Armor extends ACComponent {

	public static ItemArmor helmet;
	public static ItemArmor chestplate;
	public static ItemArmor leggings;
	public static ItemArmor boots;
	
	public Armor() {
		super("armor", false);
	}
	
	@Override
	public void preInit() {
		//init items
		helmet = (ItemArmor) new ItemHelmet().setUnlocalizedName("AC_armor_helmet");
		chestplate = (ItemArmor) new ItemChestplate().setUnlocalizedName("AC_armor_chestplate");
		leggings = (ItemArmor) new ItemLeggings().setUnlocalizedName("AC_armor_leggings");
		boots = (ItemArmor) new ItemBoots().setUnlocalizedName("AC_armor_boots");
		
		//register items
		GameRegistry.registerItem(helmet, "AC_armor_helmet");
		GameRegistry.registerItem(chestplate, "AC_armor_cheastplate");
		GameRegistry.registerItem(leggings, "AC_armor_leggings");
		GameRegistry.registerItem(boots, "AC_armor_boots");

		//register armor types
		ArmorTypeRegistry.registerArmorType(new ArmorType(0, "helmet", true));
		ArmorTypeRegistry.registerArmorType(new ArmorType(1, "chestplate", true));
		ArmorTypeRegistry.registerArmorType(new ArmorType(2, "leggings", false));
		ArmorTypeRegistry.registerArmorType(new ArmorType(3, "boots", false));
		//ArmorTypeRegistry.registerArmorType(new ArmorType(4, "cape", false));
		//ArmorTypeRegistry.registerArmorType(new ArmorType(5, "gloves", false));
		//ArmorTypeRegistry.registerArmorType(new ArmorType(6, "necklace", false));
		//ArmorTypeRegistry.registerArmorType(new ArmorType(7, "bracelet", false));
		//ArmorTypeRegistry.registerArmorType(new ArmorType(8, "ring", false));
		
		//register armor materials
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(128, "copper", "metals.copper", new ItemStack(Metals.ingot, 1, 0), false, false, 0xFFFFFF, 153));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.ingot, 1, 2), false, false, 0xFFFFFF, 277));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(131, "silver", "metals.silver", new ItemStack(Metals.ingot, 1, 3), false, false, 0xFFFFFF, 260));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(132, "iron", "metals.iron", new ItemStack(Metals.ingot, 1, 4), false, false, 0xFFFFFF, 314));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(133, "gold", "metals.gold", new ItemStack(Metals.ingot, 1, 5), false, false, 0xFFFFFF, 40));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.ingot, 1, 9), false, false, 0xFFFFFF, 443));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(139, "steel", "metals.steel", new ItemStack(Metals.ingot, 1, 11), false, false, 0xFFFFFF, 820));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.ingot, 1, 12), false, false, 0xFFFFFF, 1168));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.ingot, 1, 13), false, false, 0xFFFFFF, 1807));
		ArmorMaterialRegistry.registerArmorMaterial(new ArmorMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.ingot, 1, 14), false, false, 0xFFFFFF, 2352));
	}
}
