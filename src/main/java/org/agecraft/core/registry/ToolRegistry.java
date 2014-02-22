package org.agecraft.core.registry;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.registry.ToolRegistry.Tool;

public class ToolRegistry extends Registry<Tool> {

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
		
		public IIcon outline;
		
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
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static class ToolCreativeEntry {
		
		public int tool;
		public int toolMaterial;
		public int toolRodMaterial;
		public int toolEnhancementMaterial;
		
		public ToolCreativeEntry(int tool, int toolMaterial, int toolRodMaterial, int toolEnhancementMaterial) {
			this.tool = tool;
			this.toolMaterial = toolMaterial;
			this.toolRodMaterial = toolRodMaterial;
			this.toolEnhancementMaterial = toolEnhancementMaterial;
		}
		
		@Override
		public String toString() {
			return "[tool=" + tool + ", toolMaterial=" + toolMaterial + ", toolRodMaterial=" + toolRodMaterial + ", toolEnhancementMaterial=" + toolEnhancementMaterial + "]";
		}
	}

	public static ToolRegistry instance = new ToolRegistry();

	public static HashMap<Integer, ArrayList<ToolCreativeEntry>> toolCreativeEntries = new HashMap<Integer, ArrayList<ToolCreativeEntry>>();
	
	public ToolRegistry() {
		super(128);
	}
	
	public static int getToolID(Item item) {
		int itemID = Item.getIdFromItem(item);
		for(int i = 0; i < instance.getAll().length; i++) {
			if(instance.get(i) != null && Item.getIdFromItem(instance.get(i).item) == itemID) {
				return i;
			}
		}
		return -1;
	}
	
	public static void registerTool(Tool tool) {
		instance.set(tool.id, tool);
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
