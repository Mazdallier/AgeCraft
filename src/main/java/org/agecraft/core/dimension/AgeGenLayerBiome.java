package org.agecraft.core.dimension;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import org.agecraft.core.biomes.Biomes;

public class AgeGenLayerBiome extends GenLayer {

	public AgeChunkManager chunkManager;

	public BiomeGenBase[][] biomeGroups;

	public AgeGenLayerBiome(AgeChunkManager chunkManager, long seed, GenLayer parent, WorldType worldType) {
		super(seed);
		this.chunkManager = chunkManager;
		this.parent = parent;

		biomeGroups = chunkManager.getBiomeGroups();
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int[] listToUse = parent.getInts(x, z, width, length);
		int[] list = IntCache.getIntCache(width * length);
		for(int i = 0; i < length; ++i) {
			for(int j = 0; j < width; ++j) {
				initChunkSeed((long) (j + x), (long) (i + z));
				int oldValue1 = listToUse[j + i * width];
				int oldValue2 = (oldValue1 & 3840) >> 8;
				oldValue1 &= -3841;
				list[j + i * width] = chunkManager.getBiomeInt(this, oldValue1, oldValue2, biomeGroups);
			}
		}
		return list;
	}

	public static boolean isBiomeOceanic(int biomeID) {
		return biomeID == Biomes.ocean.biomeID || biomeID == Biomes.deepOcean.biomeID || biomeID == Biomes.frozenOcean.biomeID;
	}

	@Override
	public int nextInt(int i) {
		return super.nextInt(i);
	}
}
