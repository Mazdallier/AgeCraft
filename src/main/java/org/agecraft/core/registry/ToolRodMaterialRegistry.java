package org.agecraft.core.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.registry.ToolRodMaterialRegistry.ToolRodMaterial;

public class ToolRodMaterialRegistry extends Registry<ToolRodMaterial> {

	public static class ToolRodMaterial {

		public int id;
		public String name;
		public String localization;

		public ItemStack stack;

		public int durability;
		public float efficiency;
		public int attackStrength;

		public IIcon[] icons = new IIcon[128];

		public ToolRodMaterial(int id, String name, String localization, ItemStack stack, int durability, float efficiency, int attackStrength) {
			this.id = id;
			this.name = name;
			this.localization = localization;

			this.stack = stack;

			this.durability = durability;
			this.efficiency = efficiency;
			this.attackStrength = attackStrength;
		}
	}
	
	public static ToolRodMaterialRegistry instance = new ToolRodMaterialRegistry();
	
	public ToolRodMaterialRegistry() {
		super(256);
	}
	
	public static void registerToolRodMaterial(ToolRodMaterial toolRodMaterial) {
		instance.set(toolRodMaterial.id, toolRodMaterial);
	}
}
