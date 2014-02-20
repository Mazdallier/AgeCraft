package org.agecraft.core.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.registry.ArmorMaterialRegistry.ArmorMaterial;

public class ArmorMaterialRegistry extends Registry<ArmorMaterial> {
	
	public static class ArmorMaterial {
		
		public int id;
		public String name;
		public String localization;
		
		public ItemStack stack;
		
		public boolean hasOverlay;
		public boolean hasColors;
		public int defaultColor;
		
		public int durability;
		
		public IIcon[] icons = new IIcon[128];
		public IIcon[] iconsOverlay = new IIcon[128];
		
		public ArmorMaterial(int id, String name, String localization, ItemStack stack, boolean hasOverlay, boolean hasColors, int defaultColor, int durability) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
			
			this.hasOverlay = hasOverlay;
			this.hasColors = hasColors;
			this.defaultColor = defaultColor;
			
			this.durability = durability;
		}
	}
	
	public static ArmorMaterialRegistry instance = new ArmorMaterialRegistry();
	
	public ArmorMaterialRegistry() {
		super(256);
	}
	
	public static void registerArmorMaterial(ArmorMaterial armorMaterial) {
		instance.set(armorMaterial.id, armorMaterial);
	}
}
