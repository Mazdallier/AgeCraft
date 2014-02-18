package org.agecraft.core.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AgeGenLayerRiver extends GenLayer {

	public BiomeGenBase biome;

	public AgeGenLayerRiver(long seed, GenLayer parent, BiomeGenBase biome) {
		super(seed);
		this.parent = parent;
		this.biome = biome;
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int i1 = x - 1;
		int j1 = z - 1;
		int k1 = width + 2;
		int l1 = length + 2;
		int[] oldList = parent.getInts(i1, j1, k1, l1);
		int[] list = IntCache.getIntCache(width * length);
		for(int i2 = 0; i2 < length; ++i2) {
			for(int j2 = 0; j2 < width; ++j2) {
				int side1 = getValue(oldList[j2 + 0 + (i2 + 1) * k1]);
				int side2 = getValue(oldList[j2 + 2 + (i2 + 1) * k1]);
				int side3 = getValue(oldList[j2 + 1 + (i2 + 0) * k1]);
				int side4 = getValue(oldList[j2 + 1 + (i2 + 2) * k1]);
				int side5 = getValue(oldList[j2 + 1 + (i2 + 1) * k1]);
				if(side5 == side1 && side5 == side3 && side5 == side2 && side5 == side4) {
					list[j2 + i2 * width] = -1;
				} else {
					list[j2 + i2 * width] = biome.biomeID;
				}
			}
		}

		return list;
	}

	public int getValue(int value) {
		return value >= 2 ? 2 + (value & 1) : value;
	}
}
