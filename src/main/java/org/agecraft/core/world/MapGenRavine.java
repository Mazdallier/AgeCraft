package org.agecraft.core.world;

import org.agecraft.core.Stone;
import org.agecraft.core.biomes.Biomes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenRavine extends net.minecraft.world.gen.MapGenRavine {

	private boolean isExceptionBiome(BiomeGenBase biome) {
		return biome == BiomeGenBase.mushroomIsland || biome == BiomeGenBase.beach || biome == BiomeGenBase.desert || biome == Biomes.timelessIsland || biome == Biomes.beach || biome == Biomes.desert;
	}
	
	@Override
	protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		Block top = (isExceptionBiome(biome) ? Blocks.grass : biome.topBlock);
		Block filler = (isExceptionBiome(biome) ? Blocks.dirt : biome.fillerBlock);
		Block block = data[index];
		if(block == Blocks.stone || block == Stone.stone || block == Stone.layeredStone || block == filler || block == top) {
			if(y < 10) {
				data[index] = Blocks.flowing_lava;
			} else {
				data[index] = null;

				if(foundTop && data[index - 1] == filler) {
					data[index - 1] = top;
				}
			}
		}
	}
}
