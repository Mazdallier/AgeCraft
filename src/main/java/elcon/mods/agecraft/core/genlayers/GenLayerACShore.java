package elcon.mods.agecraft.core.genlayers;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import elcon.mods.agecraft.prehistory.biomes.BiomeGenACPrehistory;

public class GenLayerACShore extends GenLayer {
	
	public GenLayerACShore(long seed, GenLayer genLayer) {
		super(seed);
		parent = genLayer;
	}

	@Override
	public int[] getInts(int par1, int par2, int par3, int par4) {
		int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
		int[] var6 = IntCache.getIntCache(par3 * par4);

		for(int var7 = 0; var7 < par4; ++var7) {
			for(int var8 = 0; var8 < par3; ++var8) {
				this.initChunkSeed((long) (var8 + par1), (long) (var7 + par2));
				int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];
				int var10;
				int var11;
				int var12;
				int var13;

				if(var9 != BiomeGenACPrehistory.prehistoryOcean.biomeID && var9 != BiomeGenACPrehistory.prehistoryRiver.biomeID && var9 != BiomeGenACPrehistory.prehistoryPlainsHills.biomeID) {
					var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
					var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
					var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
					var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];

					if(var10 != BiomeGenACPrehistory.prehistoryOcean.biomeID && var11 != BiomeGenACPrehistory.prehistoryOcean.biomeID && var12 != BiomeGenACPrehistory.prehistoryOcean.biomeID && var13 != BiomeGenACPrehistory.prehistoryOcean.biomeID) {
						var6[var8 + var7 * par3] = var9;
					} else {
						var6[var8 + var7 * par3] = BiomeGenACPrehistory.prehistoryBeach.biomeID;
					}
				} else if(var9 == BiomeGenACPrehistory.prehistoryPlainsHills.biomeID) {
					var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
					var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
					var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
					var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];

					if(var10 == BiomeGenACPrehistory.prehistoryPlainsHills.biomeID && var11 == BiomeGenACPrehistory.prehistoryPlainsHills.biomeID && var12 == BiomeGenACPrehistory.prehistoryPlainsHills.biomeID && var13 == BiomeGenACPrehistory.prehistoryPlainsHills.biomeID) {
						var6[var8 + var7 * par3] = var9;
					} else {
						var6[var8 + var7 * par3] = BiomeGenACPrehistory.prehistoryPlainsHillsEdge.biomeID;
					}
				} else {
					var6[var8 + var7 * par3] = var9;
				}
			}
		}

		return var6;
	}
}
