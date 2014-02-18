package org.agecraft.core.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AgeGenLayerShore extends GenLayer {

	public AgeChunkManager chunkManager;

	public AgeGenLayerShore(AgeChunkManager chunkManager, long seed, GenLayer parent) {
		super(seed);
		this.chunkManager = chunkManager;
		this.parent = parent;
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int[] oldList = parent.getInts(x - 1, z - 1, width + 2, length + 2);
		int[] list = IntCache.getIntCache(width * length);
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				initChunkSeed((long) (j + x), (long) (i + z));
				int oldValue = oldList[j + 1 + (i + 1) * (width + 2)];
				list[j + i * width] = chunkManager.getShoreInt(this, oldList, list, j, i, width, oldValue, BiomeGenBase.getBiome(oldValue));
			}
		}
		return list;
	}

	public void createBeachIfSuitable(int[] oldList, int[] list, int j, int i, int width, int oldValue, int newValue) {
		if(isBiomeOceanic(oldValue)) {
			list[j + i * width] = oldValue;
		} else {
			int side1 = oldList[j + 1 + (i + 1 - 1) * (width + 2)];
			int side2 = oldList[j + 1 + 1 + (i + 1) * (width + 2)];
			int side3 = oldList[j + 1 - 1 + (i + 1) * (width + 2)];
			int side4 = oldList[j + 1 + (i + 1 + 1) * (width + 2)];
			if(!isBiomeOceanic(side1) && !isBiomeOceanic(side2) && !isBiomeOceanic(side3) && !isBiomeOceanic(side4)) {
				list[j + i * width] = oldValue;
			} else {
				list[j + i * width] = newValue;
			}
		}
	}

	public boolean isJunleOrForestOrTaigaOrOcean(int biomeID) {
		return BiomeGenBase.getBiome(biomeID) != null && BiomeGenBase.getBiome(biomeID).getBiomeClass() == BiomeGenJungle.class ? true : biomeID == BiomeGenBase.jungleEdge.biomeID || biomeID == BiomeGenBase.jungle.biomeID || biomeID == BiomeGenBase.jungleHills.biomeID || biomeID == BiomeGenBase.forest.biomeID || biomeID == BiomeGenBase.taiga.biomeID || isBiomeOceanic(biomeID);
	}

	public boolean isMesaBiome(int biomeID) {
		return BiomeGenBase.getBiome(biomeID) != null && BiomeGenBase.getBiome(biomeID) instanceof BiomeGenMesa;
	}
}
