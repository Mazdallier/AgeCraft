package elcon.mods.agecraft.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import elcon.mods.agecraft.ACLog;

public class MetalRegistry {

	public static class Metal {
		
		public int id;
		public String name;
		public OreType type;
		
		public float hardness;
		public float resistance;
		public int harvestLevel;
		public float blockHardness;
		public float blockResistance;
		
		public ItemStack drop;
		public int dropMin;
		public int dropMax;
		public boolean fortune;
		
		public boolean hasOre;
		public boolean hasIngot;
		public boolean hasBlock;
		public boolean hasDoor;
		public boolean hasDust;
		
		public int redstonePower;
		public int fireSpreadSpeed;
		public int flammability;
		
		public int metalColor;
		
		public Icon ore;
		public Icon block;
		public Icon ingot;
		public Icon stick;
		public Icon nugget;
		public Icon dust;
		
		public Metal(int id, String name, OreType type, float hardness, float resistane, int harvestLevel, float blockHardness, float blockResistane, ItemStack drop, int dropMin, int dropMax, boolean fortune, boolean hasOre, boolean hasIngot, boolean hasBlock, boolean hasDoor, boolean hasDust, int redstonePower, int fireSpreadSpeed, int flammability, int metalColor) {
			this.id = id;
			this.name = name;
			this.type = type;
			
			this.hardness = hardness;
			this.resistance = resistane;
			this.harvestLevel = harvestLevel;
			this.blockHardness = blockHardness;
			this.blockResistance = blockResistane;
			
			drop.stackSize = 1;
			this.drop = drop;
			this.dropMin = dropMin;
			this.dropMax = dropMax;
			this.fortune = fortune;
			
			this.hasOre = hasOre;
			this.hasIngot = hasIngot;
			this.hasBlock = hasBlock;
			this.hasDoor = hasDoor;
			this.hasDust = hasDust;
			
			this.redstonePower = redstonePower;
			this.fireSpreadSpeed = fireSpreadSpeed;
			this.flammability = flammability;
			
			this.metalColor = metalColor;
		}
	}
	
	public enum OreType {
		METAL(),
		GEM();
	}
	
	public static Metal[] metals = new Metal[128];
	
	public static void registerMetal(Metal ore) {
		if(metals[ore.id] != null) {
			ACLog.warning("[MetalRegistry] Overriding existing ore (" + metals[ore.id].id + ": " + metals[ore.id].name.toUpperCase() + ") with new ore (" + ore.id + ": " + ore.name.toUpperCase() + ")");
		}
		metals[ore.id]= ore;
	}
}
