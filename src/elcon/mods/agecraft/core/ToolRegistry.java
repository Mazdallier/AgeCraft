package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.core.items.tool.ItemTool;

public class ToolRegistry {

	public static class Tool {
		
		public int id;
		public String name;
		
		public ItemTool item;
		
		public int damageBlock;
		public int damageEntity;
		
		public boolean hasHead;
		public boolean hasRod;
		public boolean hasEnhancements;
		
		public Block[] blocksEffectiveAgainst;
		
		public Tool(int id, String name, ItemTool item, int damageBlock, int damageEntity, boolean hasHead, boolean hasRod, boolean hasEnhancements, Block[] blocksEffectiveAgainst) {
			this.id = id;
			this.name = name;
			
			this.item = item;
			
			this.damageBlock = damageBlock;
			this.damageEntity = damageEntity;
			
			this.hasHead = hasHead;
			this.hasRod = hasRod;
			this.hasEnhancements = hasEnhancements;
			
			this.blocksEffectiveAgainst = blocksEffectiveAgainst;
		}
	}
	
	public static class ToolMaterial {
		
		public int id;
		public String name;
		
		public ItemStack stack;
		
		public int durability;
		public float efficiency;
		public int attackStrength;
		
		public Icon[] icons = new Icon[128];
		
		public ToolMaterial(int id, String name, ItemStack stack, int durability, float efficiency, int attackStrength) {
			this.id = id;
			this.name = name;
			
			this.stack = stack;
			
			this.durability = durability;
			this.efficiency = efficiency;
			this.attackStrength = attackStrength;
		}
	}
	
	public static class ToolRodMaterial {
		
		public int id;
		public String name;
		
		public ItemStack stack;
		
		public int durability;
		public float efficiency;
		public int attackStrength;
		
		public Icon[] icons = new Icon[128];
		
		public ToolRodMaterial(int id, String name, ItemStack stack, int durability, float efficiency, int attackStrength) {
			this.id = id;
			this.name = name;
			
			this.stack = stack;
			
			this.durability = durability;
			this.efficiency = efficiency;
			this.attackStrength = attackStrength;
		}
	}
	
	public static class ToolEnhancementMaterial {
		
		public int id;
		public String name;
		
		public ItemStack stack;
		
		public Icon[] icons = new Icon[128];
		
		public ToolEnhancementMaterial(int id, String name, ItemStack stack) {
			this.id = id;
			this.name = name;
			
			this.stack = stack;
		}
	}
	
	public static Tool[] tools = new Tool[128];
	public static ToolMaterial[] toolMaterials = new ToolMaterial[256];
	public static ToolRodMaterial[] toolRodMaterials = new ToolRodMaterial[256];
	public static ToolEnhancementMaterial[] toolEnhancementMaterials = new ToolEnhancementMaterial[256];
	
	public static void registerTool(Tool tool) {
		if(tools[tool.id] != null) {
			ACLog.warning("[ToolRegistry] Overriding existing tool (" + tools[tool.id] + ": " + tools[tool.id].name.toUpperCase() + ") with new tool (" + tool.id + ": " + tool.name.toUpperCase() + ")");
		}
		tools[tool.id]= tool;
	}
	
	public static void registerToolMaterial(ToolMaterial toolMaterial) {
		if(toolMaterials[toolMaterial.id] != null) {
			ACLog.warning("[ToolRegistry] Overriding existing tool material (" + tools[toolMaterial.id] + ": " + tools[toolMaterial.id].name.toUpperCase() + ") with new tool material (" + toolMaterial.id + ": " + toolMaterial.name.toUpperCase() + ")");
		}
		toolMaterials[toolMaterial.id]= toolMaterial;
	}
	
	public static void registerToolRodMaterial(ToolRodMaterial toolRodMaterial) {
		if(toolRodMaterials[toolRodMaterial.id] != null) {
			ACLog.warning("[ToolRegistry] Overriding existing tool rod material (" + tools[toolRodMaterial.id] + ": " + tools[toolRodMaterial.id].name.toUpperCase() + ") with new tool rod material (" + toolRodMaterial.id + ": " + toolRodMaterial.name.toUpperCase() + ")");
		}
		toolRodMaterials[toolRodMaterial.id]= toolRodMaterial;
	}
	
	public static void registerToolEnhancementMaterial(ToolEnhancementMaterial toolEnhancementMaterial) {
		if(toolEnhancementMaterials[toolEnhancementMaterial.id] != null) {
			ACLog.warning("[ToolRegistry] Overriding existing tool enhancement material (" + tools[toolEnhancementMaterial.id] + ": " + tools[toolEnhancementMaterial.id].name.toUpperCase() + ") with new tool enhancement material (" + toolEnhancementMaterial.id + ": " + toolEnhancementMaterial.name.toUpperCase() + ")");
		}
		toolEnhancementMaterials[toolEnhancementMaterial.id]= toolEnhancementMaterial;
	}
}
