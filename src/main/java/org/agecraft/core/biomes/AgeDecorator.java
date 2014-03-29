package org.agecraft.core.biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.terraingen.TerrainGen;

import org.agecraft.core.Metals;
import org.agecraft.core.registry.MetalRegistry;
import org.agecraft.core.worldgen.WorldGenOre;
import org.agecraft.prehistory.world.WorldGenTempSmallTree;

import elcon.mods.elconqore.EQUtil;

public class AgeDecorator {
	
	public String name;
	
	public int grassPerChunk;
	public int smallTreesPerChunk;
	
	public WorldGenTempSmallTree tempSmallTreeGen;
	public WorldGenOre[] oreGens;
	
	public AgeDecorator(String name) {
		this.name = name;
		resetOptions();
		
		tempSmallTreeGen = new WorldGenTempSmallTree();
		oreGens = new WorldGenOre[MetalRegistry.instance.getAll().length];
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null && MetalRegistry.instance.get(i).hasOre) {
				oreGens[i] = new WorldGenOre(Metals.ore, i, true, MetalRegistry.instance.get(i).oreGenSize, Blocks.stone, MetalRegistry.instance.get(i).oreGenPerChunk, MetalRegistry.instance.get(i).oreGenMinY, MetalRegistry.instance.get(i).oreGenMaxY);
			}
		}
	}
	
	public void resetOptions() {
		grassPerChunk = 1;
		smallTreesPerChunk = 1;
	}

	public void decorate(World world, Random random, int chunkX, int chunkZ, AgeBiome biome) {
		int x, y, z;
		WorldGenerator gen;
		for(int i = 0; i < grassPerChunk; i++) {
			x = chunkX + random.nextInt(16) + 8;
			y = random.nextInt(128);
			z = chunkZ + random.nextInt(16) + 8;
			gen = biome.getRandomWorldGenForGrass(random);
			gen.generate(world, random, x, y, z);
		}
		for(int i = 0; i < smallTreesPerChunk; i++) {
			x = chunkX + random.nextInt(16) + 8;
			z = chunkZ + random.nextInt(16) + 8;
			y = EQUtil.getFirstUncoveredBlock(world, x, z);
			tempSmallTreeGen.generate(world, random, x, y, z);
		}
		for(int i = 0; i < oreGens.length; i++) {
			if(oreGens[i] != null) {
				if(TerrainGen.generateOre(world, random, oreGens[i], chunkX, chunkZ, GenerateMinable.EventType.CUSTOM)) {
					genStandardOre(world, random, chunkX, chunkZ, oreGens[i], oreGens[i].oreGenPerChunk, oreGens[i].oreGenMinY, oreGens[i].oreGenMaxY);
				}
			}
		}
	}

	public void genStandardOre(World world, Random random, int chunkX, int chunkZ, WorldGenerator worldGen, int oreGenPerChunk, int oreGenMinY, int oreGenMaxY) {
		for(int i = 0; i < oreGenPerChunk; i++) {
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(oreGenMaxY - oreGenMinY) + oreGenMinY;
			int z = chunkZ + random.nextInt(16);
			worldGen.generate(world, random, x, y, z);
		}
	}
}
