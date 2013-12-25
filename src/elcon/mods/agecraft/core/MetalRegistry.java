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
		
		public int oreGenSize;
		public int oreGenPerChunk;
		public int oreGenMinY;
		public int oreGenMaxY;
		
		public Icon ore;
		public Icon[] blocks = new Icon[4];
		public Icon blockPillar;
		public Icon blockPillarTop;
		public Icon ingot;
		public Icon stick;
		public Icon nugget;
		public Icon dust;
		public Icon bucket;
		
		public Metal(int id, String name, OreType type, float hardness, float resistane, int harvestLevel, float blockHardness, float blockResistane, ItemStack drop, int dropMin, int dropMax, boolean fortune, boolean hasOre, boolean hasIngot, boolean hasBlock, boolean hasDoor, boolean hasDust, int redstonePower, int fireSpreadSpeed, int flammability, int metalColor, int oreGenSize, int oreGenPerChunk, int oreGenMinY, int oreGenMaxY) {
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
			
			this.oreGenSize = oreGenSize;
			this.oreGenPerChunk = oreGenPerChunk;
			this.oreGenMinY = oreGenMinY;
			this.oreGenMaxY = oreGenMaxY;
		}
	}
	
	public enum OreType {
		METAL(),
		GEM();
	}
	
	public static Metal[] metals = new Metal[128];
	
	public static void registerMetal(Metal metal) {
		if(metals[metal.id] != null) {
			ACLog.warning("[MetalRegistry] Overriding existing metal (" + metals[metal.id].id + ": " + metals[metal.id].name.toUpperCase() + ") with new metal (" + metal.id + ": " + metal.name.toUpperCase() + ")");
		}
		metals[metal.id]= metal;
	}
}
