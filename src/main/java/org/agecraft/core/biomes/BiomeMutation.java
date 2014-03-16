package org.agecraft.core.biomes;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeMutation extends AgeBiome {

	public AgeBiome baseBiome;
	
	public BiomeMutation(int id) {
		super(id);
	}

	public BiomeMutation(int id, AgeBiome biome) {
		super(id);
		baseBiome = biome;
		func_150557_a(biome.color, true);
		biomeName = biome.biomeName + " M";
		topBlock = biome.topBlock;
		fillerBlock = biome.fillerBlock;
		field_76754_C = biome.field_76754_C;
		rootHeight = biome.rootHeight;
		heightVariation = biome.heightVariation;
		temperature = biome.temperature;
		rainfall = biome.rainfall;
		waterColorMultiplier = biome.waterColorMultiplier;
		enableSnow = biome.getEnableSnow();
		enableRain = biome.canSpawnLightningBolt();
		spawnableCreatureList = new ArrayList(biome.getSpawnableList(EnumCreatureType.creature));
		spawnableMonsterList = new ArrayList(biome.getSpawnableList(EnumCreatureType.monster));
		spawnableCaveCreatureList = new ArrayList(biome.getSpawnableList(EnumCreatureType.ambient));
		spawnableWaterCreatureList = new ArrayList(biome.getSpawnableList(EnumCreatureType.waterCreature));
		temperature = biome.temperature;
		rainfall = biome.rainfall;
		rootHeight = biome.rootHeight + 0.1F;
		heightVariation = biome.heightVariation + 0.2F;
	}

	@Override
	public void decorate(World world, Random random, int x, int y) {
		baseBiome.theBiomeDecorator.decorateChunk(world, random, this, x, y);
	}

	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] meta, int x, int y, double noise) {
		baseBiome.genTerrainBlocks(world, random, blocks, meta, x, y, noise);
	}

	@Override
	public float getSpawningChance() {
		return baseBiome.getSpawningChance();
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random random) {
		return baseBiome.func_150567_a(random);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int x, int y, int z) {
		return baseBiome.getBiomeFoliageColor(x, y, y);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int x, int y, int z) {
		return baseBiome.getBiomeGrassColor(x, y, y);
	}

	@Override
	public Class getBiomeClass() {
		return baseBiome.getBiomeClass();
	}

	@Override
	public boolean isEqualTo(BiomeGenBase biome) {
		return baseBiome.isEqualTo(biome);
	}

	@Override
	public BiomeGenBase.TempCategory getTempCategory() {
		return baseBiome.getTempCategory();
	}
}
