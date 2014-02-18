package org.agecraft.core.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AgeGenLayerBiomeEdge extends GenLayer {

	public AgeChunkManager chunkManager;

	public AgeGenLayerBiomeEdge(AgeChunkManager chunkManager, long seed, GenLayer parent) {
		super(seed);
		this.chunkManager = chunkManager;
		this.parent = parent;
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int[] oldList = parent.getInts(x - 1, z - 1, width + 2, length + 2);
		int[] list = IntCache.getIntCache(width * length);
		for(int i = 0; i < length; ++i) {
			for(int j = 0; j < width; ++j) {
				initChunkSeed((long) (j + x), (long) (i + z));
				int oldValue = oldList[j + 1 + (i + 1) * (width + 2)];
				list[j + i * width] = chunkManager.getBiomeEdgeInt(this, oldList, list, j, i, width, oldValue);
			}
		}
		return list;
	}

	public boolean isBiomeSuitable1(int[] oldList, int[] list, int i, int j, int width, int oldValue, int newValue1, int newValue2) {
		if(!compareBiomesById(oldValue, newValue1)) {
			return false;
		} else {
			int side1 = oldList[i + 1 + (j + 1 - 1) * (width + 2)];
			int side2 = oldList[i + 1 + 1 + (j + 1) * (width + 2)];
			int side3 = oldList[i + 1 - 1 + (j + 1) * (width + 2)];
			int side4 = oldList[i + 1 + (j + 1 + 1) * (width + 2)];
			if(areBiomeTemperaturesSuitable(side1, newValue1) && areBiomeTemperaturesSuitable(side2, newValue1) && areBiomeTemperaturesSuitable(side3, newValue1) && areBiomeTemperaturesSuitable(side4, newValue1)) {
				list[i + j * width] = oldValue;
			} else {
				list[i + j * width] = newValue2;
			}
			return true;
		}
	}

	public boolean isBiomeSuitable2(int[] oldList, int[] list, int i, int j, int width, int oldValue, int newValue1, int newValue2) {
		if(oldValue != newValue1) {
			return false;
		} else {
			int side1 = oldList[i + 1 + (j + 1 - 1) * (width + 2)];
			int side2 = oldList[i + 1 + 1 + (j + 1) * (width + 2)];
			int side3 = oldList[i + 1 - 1 + (j + 1) * (width + 2)];
			int side4 = oldList[i + 1 + (j + 1 + 1) * (width + 2)];
			if(compareBiomesById(side1, newValue1) && compareBiomesById(side2, newValue1) && compareBiomesById(side3, newValue1) && compareBiomesById(side4, newValue1)) {
				list[i + j * width] = oldValue;
			} else {
				list[i + j * width] = newValue2;
			}
			return true;
		}
	}

	public boolean areBiomeTemperaturesSuitable(int biome1, int biome2) {
		if(compareBiomesById(biome1, biome2)) {
			return true;
		} else if(BiomeGenBase.getBiome(biome1) != null && BiomeGenBase.getBiome(biome2) != null) {
			BiomeGenBase.TempCategory category1 = BiomeGenBase.getBiome(biome1).getTempCategory();
			BiomeGenBase.TempCategory category2 = BiomeGenBase.getBiome(biome2).getTempCategory();
			return category1 == category2 || category1 == BiomeGenBase.TempCategory.MEDIUM || category2 == BiomeGenBase.TempCategory.MEDIUM;
		} else {
			return false;
		}
	}
}
