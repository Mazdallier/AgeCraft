package org.agecraft.core.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import org.agecraft.AgeCraft;
import org.agecraft.core.dimension.AgeChunkProvider;

public abstract class AgeBiome extends BiomeGenBase {

	public AgeChunkProvider chunkProvider;
	
	public AgeBiome(int id) {
		this(id, true);
	}
	
	public AgeBiome(int id, boolean register) {
		super(id, register);
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
	}
	
	public abstract BiomeGenBase getMutation();

	@Override
	public void decorate(World world, Random random, int chunkX, int chunkZ) {
		if(chunkProvider == null) {
			AgeCraft.log.error("AgeBiome can't decorate without AgeChunkProvider!");
			return;
		}
	}
	
	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] meta, int x, int y, double noise) {
		genBiomeTerrain(world, random, blocks, meta, x, y, noise);
	}
}
