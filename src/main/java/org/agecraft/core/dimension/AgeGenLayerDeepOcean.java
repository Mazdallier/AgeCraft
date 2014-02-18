package org.agecraft.core.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AgeGenLayerDeepOcean extends GenLayer {

	public BiomeGenBase biome;

	public AgeGenLayerDeepOcean(long seed, GenLayer parent, BiomeGenBase biome) {
		super(seed);
		this.parent = parent;
		this.biome = biome;
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int x1 = x - 1;
		int z1 = z - 1;
		int x2 = width + 2;
		int z2 = length + 2;
		int[] oldList = parent.getInts(x1, z1, x2, z2);
		int[] list = IntCache.getIntCache(width * length);
		for(int i = 0; i < length; ++i) {
			for(int j = 0; j < width; ++j) {
				int side1 = oldList[j + 1 + (i + 1 - 1) * (width + 2)];
				int side2 = oldList[j + 1 + 1 + (i + 1) * (width + 2)];
				int side3 = oldList[j + 1 - 1 + (i + 1) * (width + 2)];
				int side4 = oldList[j + 1 + (i + 1 + 1) * (width + 2)];
				int side5 = oldList[j + 1 + (i + 1) * x2];
				int k = 0;
				if(side1 == 0) {
					k++;
				}
				if(side2 == 0) {
					k++;
				}
				if(side3 == 0) {
					k++;
				}
				if(side4 == 0) {
					k++;
				}
				if(side5 == 0 && k > 3) {
					list[j + i * width] = biome.biomeID;
				} else {
					list[j + i * width] = side5;
				}
			}
		}
		return list;
	}
}
