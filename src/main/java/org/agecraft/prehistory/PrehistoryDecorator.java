package org.agecraft.prehistory;

import java.util.Random;

import net.minecraft.world.World;

import org.agecraft.core.biomes.AgeBiome;
import org.agecraft.core.biomes.AgeDecorator;
import org.agecraft.prehistory.world.WorldGenRock;

public class PrehistoryDecorator extends AgeDecorator {

	public int rocksPerChunk;
	
	public WorldGenRock rockGen;
	
	public PrehistoryDecorator() {
		super("prehistory");
		rockGen = new WorldGenRock();
	}
	
	@Override
	public void resetOptions() {
		super.resetOptions();
		
		rocksPerChunk = 3;
	}
	
	@Override
	public void decorate(World world, Random random, int chunkX, int chunkZ, AgeBiome biome) {
		super.decorate(world, random, chunkX, chunkZ, biome);
		int x, y, z;
		for(int i = 0; i < rocksPerChunk; i++) {
			x = chunkX + random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = chunkZ + random.nextInt(16) + 8;
			rockGen.generate(world, random, x, y, z);
		}
	}
}
