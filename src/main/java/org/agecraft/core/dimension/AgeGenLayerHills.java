package org.agecraft.core.dimension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AgeGenLayerHills extends GenLayer {

	private static final Logger logger = LogManager.getLogger();
	
	public AgeChunkManager chunkManager;
	public GenLayer previous;

	public AgeGenLayerHills(AgeChunkManager chunkManager, long seed, GenLayer parent, GenLayer previous) {
		super(seed);
		this.chunkManager = chunkManager;
		this.parent = parent;
		this.previous = previous;
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int[] oldList = parent.getInts(x - 1, z - 1, width + 2, length + 2);
		int[] prevList = previous.getInts(x - 1, z - 1, width + 2, length + 2);
		int[] list = IntCache.getIntCache(width * length);
		for(int i = 0; i < length; ++i) {
			for(int j = 0; j < width; ++j) {
				initChunkSeed((long) (j + x), (long) (i + z));
				int oldValue = oldList[j + 1 + (i + 1) * (width + 2)];
				int prevValue = prevList[j + 1 + (i + 1) * (width + 2)];
				boolean flag = (prevValue - 2) % 29 == 0;
				if(oldValue > 255) {
					logger.debug("old! " + oldValue);
				}
				if(oldValue != 0 && prevValue >= 2 && (prevValue - 2) % 29 == 1 && oldValue < 128) {
					if(BiomeGenBase.getBiome(oldValue + 128) != null) {
						list[j + i * width] = oldValue + 128;
					} else {
						list[j + i * width] = oldValue;
					}
				} else if(nextInt(3) != 0 && !flag) {
					list[j + i * width] = oldValue;
				} else {
					int newValue = chunkManager.getHillsInt(this, oldValue);
					//TODO: create AgeBiome.getMutation() and replace all BiomeGenBase with AgeBiome
					if(flag && newValue != oldValue) {
						if(BiomeGenBase.getBiome(newValue + 128) != null) {
							newValue += 128;
						} else {
							newValue = oldValue;
						}
					}
					if(newValue == oldValue) {
						list[j + i * width] = oldValue;
					} else {
						int side1 = oldList[j + 1 + (i + 1 - 1) * (width + 2)];
						int side2 = oldList[j + 1 + 1 + (i + 1) * (width + 2)];
						int side3 = oldList[j + 1 - 1 + (i + 1) * (width + 2)];
						int side4 = oldList[j + 1 + (i + 1 + 1) * (width + 2)];
						int k = 0;
						if(compareBiomesById(side1, oldValue)) {
							k++;
						}
						if(compareBiomesById(side2, oldValue)) {
							k++;
						}
						if(compareBiomesById(side3, oldValue)) {
							k++;
						}
						if(compareBiomesById(side4, oldValue)) {
							k++;
						}
						if(k >= 3) {
							list[j + i * width] = newValue;
						} else {
							list[j + i * width] = oldValue;
						}
					}
				}
			}
		}
		return list;
	}
	
	public static boolean compareBiomesById(final int biome1, final int biome2) {
		return GenLayer.compareBiomesById(biome1, biome2);
	}
	
	@Override
	public int nextInt(int i) {
		return super.nextInt(i);
	}
}
