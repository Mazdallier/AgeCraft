package elcon.mods.agecraft.prehistory.biomes;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;
import elcon.mods.agecraft.prehistory.PrehistoryAge;

public class BiomeDecoratorACPrehistory {

	public World worldObj;
	public Random rand;
	public int chunkX;
	public int chunkZ;
	public BiomeGenACPrehistory biome;

	public int rocksPerChunk;
	public int grassPerChunk;

	public WorldGenFlowers rockGen;
	
	public BiomeDecoratorACPrehistory(BiomeGenACPrehistory b) {
		biome = b;

		rocksPerChunk = 1;
		grassPerChunk = 1;

		rockGen = new WorldGenFlowers(PrehistoryAge.rockBlock.blockID);
	}

	public void decorate(World world, Random random, int i, int j) {
		worldObj = world;
		rand = random;
		chunkX = i;
		chunkZ = j;
		decorate();
	}

	private void decorate() {
		int i, j;
		int x, y, z;
		WorldGenerator gen;
		for(i = 0; i < rocksPerChunk; i++) {
			x = chunkX + rand.nextInt(16) + 8;
			y = rand.nextInt(128);
			z = chunkZ + rand.nextInt(16) + 8;
			rockGen.generate(worldObj, rand, x, y, z);
		}
		for(i = 0; i < grassPerChunk; i++) {
			x = chunkX + rand.nextInt(16) + 8;
			y = rand.nextInt(128);
			z = chunkZ + rand.nextInt(16) + 8;
			gen = biome.getRandomWorldGenForGrass(rand);
			gen.generate(worldObj, rand, x, y, z);
		}
	}
}

