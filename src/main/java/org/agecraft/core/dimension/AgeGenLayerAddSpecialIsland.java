package org.agecraft.core.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AgeGenLayerAddSpecialIsland extends GenLayer {

	public BiomeGenBase biome;

	public AgeGenLayerAddSpecialIsland(long seed, GenLayer parent, BiomeGenBase biome) {
		super(seed);
		this.parent = parent;
		this.biome = biome;
	}

	@Override
	public int[] getInts(int x, int z, int length, int width) {
		int x1 = x - 1;
		int z1 = z - 1;
		int x2 = length + 2;
		int z2 = width + 2;
		int[] oldList = parent.getInts(x1, z1, x2, z2);
		int[] list = IntCache.getIntCache(length * width);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < length; j++) {
				int side1 = oldList[j + 0 + (i + 0) * x2];
				int side2 = oldList[j + 2 + (i + 0) * x2];
				int side3 = oldList[j + 0 + (i + 2) * x2];
				int side4 = oldList[j + 2 + (i + 2) * x2];
				int side5 = oldList[j + 1 + (i + 1) * x2];
				initChunkSeed((long) (j + x), (long) (i + z));
				if(side5 == 0 && side1 == 0 && side2 == 0 && side3 == 0 && side4 == 0 && nextInt(100) == 0) {
					list[j + i * length] = biome.biomeID;
				} else {
					list[j + i * length] = side5;
				}
			}
		}
		return list;
	}
}
