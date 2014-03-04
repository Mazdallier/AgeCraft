package org.agecraft.core.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.registry.MetalRegistry.Metal;

import elcon.mods.elconqore.fluids.FluidMetadata;

public class MetalRegistry extends Registry<Metal> {

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
		
		public IIcon ore;
		public IIcon[] blocks = new IIcon[4];
		public IIcon blockPillar;
		public IIcon blockPillarTop;
		public IIcon ingot;
		public IIcon stick;
		public IIcon nugget;
		public IIcon dust;
		public IIcon bucket;
		
		public FluidMetadata fluid;
		
		public Metal(int id, String name, OreType type, float hardness, float resistane, int harvestLevel, float blockHardness, float blockResistane, ItemStack drop, int dropMin, int dropMax, boolean fortune, boolean hasOre, boolean hasIngot, boolean hasBlock, boolean hasDoor, boolean hasDust, int redstonePower, int fireSpreadSpeed, int flammability, int metalColor, int oreGenSize, int oreGenPerChunk, int oreGenMinY, int oreGenMaxY) {
			this.id = id;
			this.name = name;
			this.type = type;
			
			this.hardness = hardness;
			this.resistance = resistane;
			this.harvestLevel = harvestLevel;
			this.blockHardness = blockHardness;
			this.blockResistance = blockResistane * 3.0F;
			
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
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public enum OreType {
		METAL(),
		GEM();
	}
	
	public static MetalRegistry instance = new MetalRegistry();
	
	public MetalRegistry() {
		super(128);
	}
	
	public static void registerMetal(Metal metal) {
		instance.set(metal.id, metal);
	}
}
