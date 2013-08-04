package elcon.mods.agecraft.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import elcon.mods.agecraft.ACLog;

public class MetalRegistry {

	public static class Metal {
		
		public int id;
		public String name;
		public OreType type;
		
		public ItemStack drop;
		public int dropMin;
		public int dropMax;
		public boolean fortune;
		
		public boolean hasIngot;
		public boolean hasBlock;
		
		public Metal(int id, String name, OreType type, ItemStack drop, int dropMin, int dropMax, boolean fortune, boolean hasIngot, boolean hasBlock) {
			this.id = id;
			this.name = name;
			this.type = type;
			
			drop.stackSize = 1;
			this.drop = drop;
			this.dropMin = dropMin;
			this.dropMax = dropMax;
			this.fortune = fortune;
			
			this.hasIngot = hasIngot;
			this.hasBlock = hasBlock;
		}
	}
	
	public enum OreType {
		METAL(),
		GEM();
	}
	
	public static Metal[] ores = new Metal[64];
	
	public static void registerMetal(Metal ore) {
		if(ores[ore.id] != null) {
			ACLog.warning("[OreRegistry] Overriding existing ore (" + ores[ore.id] + ": " + ores[ore.id].name.toUpperCase() + ") with new ore (" + ore.id + ": " + ore.name.toUpperCase() + ")");
		}
		ores[ore.id]= ore;
	}
}
