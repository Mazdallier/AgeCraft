package org.agecraft.core.dimension;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class AgeGenLayerRiverMix extends GenLayer {

	public AgeChunkManager chunkManager;
	public GenLayer biomeChain;
	public GenLayer riverChain;

	public AgeGenLayerRiverMix(AgeChunkManager chunkManager, long seed, GenLayer biomeChain, GenLayer riverChain) {
		super(seed);
		this.chunkManager = chunkManager;
		this.biomeChain = biomeChain;
		this.riverChain = riverChain;
	}

	@Override
	public void initWorldGenSeed(long seed) {
		biomeChain.initWorldGenSeed(seed);
		riverChain.initWorldGenSeed(seed);
		super.initWorldGenSeed(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int[] biomeList = biomeChain.getInts(x, z, width, length);
		int[] riverList = riverChain.getInts(x, z, width, length);
		int[] list = IntCache.getIntCache(width * length);
		for(int i = 0; i < width * length; ++i) {
			list[i] = chunkManager.getRiverMixInt(this, biomeList[i], riverList[i]);
		}
		return list;
	}
}
