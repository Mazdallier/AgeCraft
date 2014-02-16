package org.agecraft.prehistory.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenACPrehistory extends BiomeGenBase {
	
	public BiomeDecoratorACPrehistory decorator;
	
	public static BiomeGenBase prehistoryOcean = new BiomeGenPrehistoryOcean(30).setMinMaxHeight(-1.0F, 0.4F).setColor(112).setBiomeName("Prehistoric Ocean");
	public static BiomeGenBase prehistoryRiver = new BiomeGenPrehistoryRiver(31).setMinMaxHeight(-0.5F, 0.0F).setColor(255).setBiomeName("Prehistoric River");
	public static BiomeGenBase prehistoryBeach = new BiomeGenPrehistoryBeach(32).setMinMaxHeight(0.0F, 0.1F).setColor(255).setBiomeName("Prehistoric Beach");

	public static BiomeGenBase prehistoryAlps = new BiomeGenPrehistoryAlpine(33).setMinMaxHeight(0.5F, 1.5F).setTemperatureRainfall(0.05F, 0.8F).setBiomeName("Prehistoric Alpine").setEnableSnow();
	public static BiomeGenBase prehistoryGlacier = new BiomeGenPrehistoryGlacier(34).setMinMaxHeight(0.3F, 1.2F).setTemperatureRainfall(0.05F, 0.8F).setBiomeName("Prehistoric Glacier").setEnableSnow();
	public static BiomeGenBase prehistorySnow = new BiomeGenPrehistorySnow(35).setMinMaxHeight(0.1F, 0.3F).setTemperatureRainfall(0.05F, 0.8F).setBiomeName("Prehistoric Snowland").setEnableSnow();

	public static BiomeGenBase prehistoryPlains = new BiomeGenPrehistoryPlains(40).setMinMaxHeight(0.1F, 0.25F).setTemperatureRainfall(0.7F, 0.8F).setColor(9231871).setBiomeName("Prehistoric Plains");
	public static BiomeGenBase prehistoryPlainsHills = new BiomeGenPrehistoryPlains(41).setMinMaxHeight(0.3F, 1.5F).setTemperatureRainfall(0.7F, 0.8F).setColor(9231871).setBiomeName("Prehistoric Plains Hills");
	public static BiomeGenBase prehistoryPlainsHillsEdge = new BiomeGenPrehistoryPlains(42).setMinMaxHeight(0.2F, 0.8F).setTemperatureRainfall(0.7F, 0.8F).setColor(9231871).setBiomeName("Prehistoric Plains Hills Edge");
	
	public BiomeGenACPrehistory(int i) {
		super(i);
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		
		decorator = new BiomeDecoratorACPrehistory(this);
	}
	
	public void decorateAC(World world, Random random, int i, int j) {
		decorator.decorate(world, random, i, j);
	}

	@Override
	public int getSkyColorByTemp(float f) {
		return 3368550;
	}

	@Override
	public int getWaterColorMultiplier() {
		return 39219;
	}

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random random) {
		return new WorldGenTallGrass(Block.tallGrass.blockID, 1);
	}

	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random random) {
		return (WorldGenerator) (random.nextInt(10) == 0 ? worldGeneratorBigTree : worldGeneratorTrees);
	}
}
