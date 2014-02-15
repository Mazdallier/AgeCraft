package org.agecraft.core.genlayers;

import org.agecraft.prehistory.biomes.BiomeGenACPrehistory;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerACBiome extends GenLayer {

	public int dimension = 0;
	private BiomeGenBase[] allowedBiomes;

	public GenLayerACBiome(long seed, GenLayer genLayer, int dim) {
		super(seed);
		dimension = dim;
		allowedBiomes = getBiomesForDimension(dimension);
		parent = genLayer;
	}

	@Override
	public int[] getInts(int par1, int par2, int par3, int par4) {
		int[] var5 = parent.getInts(par1, par2, par3, par4);
		int[] var6 = IntCache.getIntCache(par3 * par4);

		for(int var7 = 0; var7 < par4; ++var7) {
			for(int var8 = 0; var8 < par3; ++var8) {
				initChunkSeed((long) (var8 + par1), (long) (var7 + par2));
				int var9 = var5[var8 + var7 * par3];

				if(var9 == 0) {
					var6[var8 + var7 * par3] = getOceanBiomeForDimension(dimension).biomeID;
				} else if(var9 == 1) {
					var6[var8 + var7 * par3] = allowedBiomes[nextInt(allowedBiomes.length)].biomeID;
				} else {
					var6[var8 + var7 * par3] = allowedBiomes[nextInt(allowedBiomes.length)].biomeID;
				}
			}
		}

		return var6;
	}

	private BiomeGenBase getOceanBiomeForDimension(int dim) {
		if(dim == 10) {
			return BiomeGenACPrehistory.prehistoryOcean;
		}
		return BiomeGenBase.ocean;
	}

	private BiomeGenBase[] getBiomesForDimension(int dim) {
		if(dim == 10) {
			return new BiomeGenBase[] {BiomeGenACPrehistory.prehistoryOcean, BiomeGenACPrehistory.prehistoryPlains, BiomeGenACPrehistory.prehistorySnow, BiomeGenACPrehistory.prehistoryAlps};
		}
		return null;
	}
}
