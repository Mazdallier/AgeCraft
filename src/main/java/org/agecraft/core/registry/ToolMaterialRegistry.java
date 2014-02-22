package org.agecraft.core.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.registry.ToolMaterialRegistry.ToolMaterial;

public class ToolMaterialRegistry extends Registry<ToolMaterial> {

	public static class ToolMaterial {
		
		public int id;
		public String name;
		public String localization;
		
		public ItemStack stack;
		
		public int durability;
		public float efficiency;
		public int attackStrength;
		public int harvestLevel;
		
		public IIcon[] icons = new IIcon[128];
		
		public ToolMaterial(int id, String name, String localization, ItemStack stack, int durability, float efficiency, int attackStrength, int harvestLevel) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
			
			this.durability = durability;
			this.efficiency = efficiency;
			this.attackStrength = attackStrength;
			this.harvestLevel = harvestLevel;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static ToolMaterialRegistry instance = new ToolMaterialRegistry();
	
	public ToolMaterialRegistry() {
		super(256);
	}
	
	public static void registerToolMaterial(ToolMaterial toolMaterial) {
		instance.set(toolMaterial.id, toolMaterial);
	}
}
