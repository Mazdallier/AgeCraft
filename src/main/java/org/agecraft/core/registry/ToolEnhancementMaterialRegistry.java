package org.agecraft.core.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.registry.ToolEnhancementMaterialRegistry.ToolEnhancementMaterial;

public class ToolEnhancementMaterialRegistry extends Registry<ToolEnhancementMaterial> {

	public static class ToolEnhancementMaterial {
		
		public int id;
		public String name;
		public String localization;
		
		public ItemStack stack;
		
		public IIcon[] icons = new IIcon[128];
		
		public ToolEnhancementMaterial(int id, String name, String localization, ItemStack stack) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static ToolEnhancementMaterialRegistry instance = new ToolEnhancementMaterialRegistry();
	
	public ToolEnhancementMaterialRegistry() {
		super(256);
	}
	
	public static void registerToolEnhancementMaterial(ToolEnhancementMaterial toolEnhancementMaterial) {
		instance.set(toolEnhancementMaterial.id, toolEnhancementMaterial);
	}
}
