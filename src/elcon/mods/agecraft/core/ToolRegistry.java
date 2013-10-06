package elcon.mods.agecraft.core;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.core.items.tools.ItemTool;

public class ToolRegistry {

	public static class Armor {
		
		public int id;
		public String name;
		
		public ItemTool item;
		
		public int damageBlock;
		public int damageEntity;
		
		public boolean hasHead;
		public boolean hasRod;
		public boolean hasEnhancements;
		
		public Block[] blocksEffectiveAgainst;
		
		public Armor(int id, String name, ItemTool item, int damageBlock, int damageEntity, boolean hasHead, boolean hasRod, boolean hasEnhancements, Block[] blocksEffectiveAgainst) {
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
		public String localization;
		
		public ItemStack stack;
		
		public int durability;
		public float efficiency;
		public int attackStrength;
		public int harvestLevel;
		
		public Icon[] icons = new Icon[128];
		
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
	}
	
	public static class ToolRodMaterial {
		
		public int id;
		public String name;
		public String localization;
		
		public ItemStack stack;
		
		public int durability;
		public float efficiency;
		public int attackStrength;
		
		public Icon[] icons = new Icon[128];
		
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
	
	public static class ToolEnhancementMaterial {
		
		public int id;
		public String name;
		public String localization;
		
		public ItemStack stack;
		
		public Icon[] icons = new Icon[128];
		
		public ToolEnhancementMaterial(int id, String name, String localization, ItemStack stack) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
		}
	}
	
	public static class ToolCreativeEntry {
		
		public int tool;
		public int toolMaterial;
		public int toolRodMaterial;
		public int toolEnhancement;
		
		public ToolCreativeEntry(int tool, int toolMaterial, int toolRodMaterial, int toolEnhancement) {
			this.tool = tool;
			this.toolMaterial = toolMaterial;
			this.toolRodMaterial = toolRodMaterial;
			this.toolEnhancement = toolEnhancement;
		}
	}
	
	public static Armor[] tools = new Armor[128];
	public static ToolMaterial[] toolMaterials = new ToolMaterial[256];
	public static ToolRodMaterial[] toolRodMaterials = new ToolRodMaterial[256];
	public static ToolEnhancementMaterial[] toolEnhancementMaterials = new ToolEnhancementMaterial[256];
	public static HashMap<Integer, ArrayList<ToolCreativeEntry>> toolCreativeEntries = new HashMap<Integer, ArrayList<ToolCreativeEntry>>();
	
	public static void registerTool(Armor tool) {
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
	
	public static void registerToolCreativeEntry(ToolCreativeEntry toolCreativeEntry) {
		ArrayList<ToolCreativeEntry> entries = new ArrayList<ToolCreativeEntry>();
		if(toolCreativeEntries.containsKey(toolCreativeEntry.tool)) {
			entries = toolCreativeEntries.get(toolCreativeEntry.tool);
		}
		entries.add(toolCreativeEntry);
		toolCreativeEntries.put(toolCreativeEntry.tool, entries);
	}
}
