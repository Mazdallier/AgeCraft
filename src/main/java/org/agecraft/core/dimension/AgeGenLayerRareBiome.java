package org.agecraft.core.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AgeGenLayerRareBiome extends GenLayer {

	public BiomeGenBase biomeDetect;
	public BiomeGenBase biome;

	public AgeGenLayerRareBiome(long seed, GenLayer parent, BiomeGenBase biomeDetect, BiomeGenBase biome) {
		super(seed);
		this.parent = parent;
		this.biomeDetect = biomeDetect;
		this.biome = biome;
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int[] oldList = this.parent.getInts(x - 1, z - 1, width + 2, length + 2);
		int[] list = IntCache.getIntCache(width * length);
		for(int i1 = 0; i1 < length; ++i1) {
			for(int j1 = 0; j1 < width; ++j1) {
				initChunkSeed((long) (j1 + x), (long) (i1 + z));
				int side = oldList[j1 + 1 + (i1 + 1) * (width + 2)];
				if(nextInt(57) == 0) {
					if(side == biomeDetect.biomeID) {
						list[j1 + i1 * width] = biome.biomeID;
					} else {
						list[j1 + i1 * width] = side;
					}
				} else {
					list[j1 + i1 * width] = side;
				}
			}
		}
		return list;
	}
}
