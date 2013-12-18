package elcon.mods.agecraft.prehistory.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.terraingen.TerrainGen;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.Metals;
import elcon.mods.agecraft.core.world.WorldGenOre;
import elcon.mods.agecraft.prehistory.world.WorldGenRock;
import elcon.mods.agecraft.prehistory.world.WorldGenTempSmallTree;
import elcon.mods.core.ECUtil;

public class BiomeDecoratorACPrehistory {

	public World worldObj;
	public Random rand;
	public int chunkX;
	public int chunkZ;
	public BiomeGenACPrehistory biome;

	public int rocksPerChunk;
	public int grassPerChunk;
	public int smallTreesPerChunk;

	public WorldGenRock rockGen;
	public WorldGenOre[] oreGens;
	public WorldGenTempSmallTree tempSmallTreeGen;

	public BiomeDecoratorACPrehistory(BiomeGenACPrehistory b) {
		biome = b;

		rocksPerChunk = 1;
		grassPerChunk = 1;
		smallTreesPerChunk = 1;

		rockGen = new WorldGenRock();
		oreGens = new WorldGenOre[MetalRegistry.metals.length];
		tempSmallTreeGen = new WorldGenTempSmallTree();
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasOre) {
				oreGens[i] = new WorldGenOre(Metals.ore.blockID, i, true, MetalRegistry.metals[i].oreGenSize, Block.stone.blockID, MetalRegistry.metals[i].oreGenPerChunk, MetalRegistry.metals[i].oreGenMinY, MetalRegistry.metals[i].oreGenMaxY);
			}
		}
	}

	public void decorate(World world, Random random, int i, int j) {
		worldObj = world;
		rand = random;
		chunkX = i;
		chunkZ = j;
		decorate();
	}

	private void decorate() {
		int x, y, z;
		WorldGenerator gen;
		for(int i = 0; i < rocksPerChunk; i++) {
			x = chunkX + rand.nextInt(16) + 8;
			y = rand.nextInt(128);
			z = chunkZ + rand.nextInt(16) + 8;
			rockGen.generate(worldObj, rand, x, y, z);
		}
		for(int i = 0; i < grassPerChunk; i++) {
			x = chunkX + rand.nextInt(16) + 8;
			y = rand.nextInt(128);
			z = chunkZ + rand.nextInt(16) + 8;
			gen = biome.getRandomWorldGenForGrass(rand);
			gen.generate(worldObj, rand, x, y, z);
		}
		for(int i = 0; i < smallTreesPerChunk; i++) {
			x = chunkX + rand.nextInt(16) + 8;
			z = chunkZ + rand.nextInt(16) + 8;
			y = ECUtil.getFirstUncoveredBlock(worldObj, x, z);
			tempSmallTreeGen.generate(worldObj, rand, x, y, z);
		}

		for(int i = 0; i < oreGens.length; i++) {
			if(oreGens[i] != null) {
				if(TerrainGen.generateOre(worldObj, rand, oreGens[i], chunkX, chunkZ, GenerateMinable.EventType.CUSTOM)) {
					genStandardOre(oreGens[i], oreGens[i].oreGenPerChunk, oreGens[i].oreGenMinY, oreGens[i].oreGenMaxY);
				}
			}
		}
	}

	public void genStandardOre(WorldGenerator worldGen, int oreGenPerChunk, int oreGenMinY, int oreGenMaxY) {
		for(int i = 0; i < oreGenPerChunk; i++) {
			int x = chunkX + rand.nextInt(16);
			int y = rand.nextInt(oreGenMaxY - oreGenMinY) + oreGenMinY;
			int z = chunkZ + rand.nextInt(16);
			worldGen.generate(worldObj, rand, x, y, z);
		}
	}
}
