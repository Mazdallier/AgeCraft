package org.agecraft.core.dimension;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import org.agecraft.Age;
import org.agecraft.core.biomes.AgeBiome;
import org.agecraft.core.biomes.AgeDecorator;
import org.agecraft.core.world.MapGenCave;
import org.agecraft.core.world.MapGenRavine;

import cpw.mods.fml.common.eventhandler.Event.Result;
import elcon.mods.elconqore.EQUtil;

public abstract class AgeChunkProvider implements IChunkProvider {

	public Age age;
	public AgeDecorator decorator;
	public World world;
	public boolean mapFeaturesEnabled;
	public WorldType worldType;

	public Random random;
	public NoiseGeneratorOctaves noiseGen1;
	public NoiseGeneratorOctaves noiseGen2;
	public NoiseGeneratorOctaves noiseGen3;
	public NoiseGeneratorPerlin noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorOctaves mobSpawnerNoise;

	public final double[] data;
	public final float[] parabolicField;
	public double[] stoneNoise = new double[256];

	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	int[][] field_73219_j = new int[32][32];

	public BiomeGenBase[] biomesForGeneration;

	public MapGenBase caveGenerator = new MapGenCave();
	public MapGenBase ravineGenerator = new MapGenRavine();

	public AgeChunkProvider(Age age, World world, long seed, boolean mapFeaturesEnabled) {
		this.age = age;
		this.world = world;
		this.mapFeaturesEnabled = mapFeaturesEnabled;
		this.worldType = world.getWorldInfo().getTerrainType();

		random = new Random(seed);
		noiseGen1 = new NoiseGeneratorOctaves(random, 16);
		noiseGen2 = new NoiseGeneratorOctaves(random, 16);
		noiseGen3 = new NoiseGeneratorOctaves(random, 8);
		noiseGen4 = new NoiseGeneratorPerlin(random, 4);
		noiseGen5 = new NoiseGeneratorOctaves(random, 10);
		noiseGen6 = new NoiseGeneratorOctaves(random, 16);
		mobSpawnerNoise = new NoiseGeneratorOctaves(random, 8);

		data = new double[825];
		parabolicField = new float[25];

		for(int i = -2; i <= 2; ++i) {
			for(int j = -2; j <= 2; ++j) {
				float f = 10.0F / MathHelper.sqrt_float((float) (i * i + j * j) + 0.2F);
				parabolicField[i + 2 + (j + 2) * 5] = f;
			}
		}

		NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise};
		noiseGens = TerrainGen.getModdedNoiseGenerators(world, random, noiseGens);
		noiseGen1 = (NoiseGeneratorOctaves) noiseGens[0];
		noiseGen2 = (NoiseGeneratorOctaves) noiseGens[1];
		noiseGen3 = (NoiseGeneratorOctaves) noiseGens[2];
		noiseGen4 = (NoiseGeneratorPerlin) noiseGens[3];
		noiseGen5 = (NoiseGeneratorOctaves) noiseGens[4];
		noiseGen6 = (NoiseGeneratorOctaves) noiseGens[5];
		mobSpawnerNoise = (NoiseGeneratorOctaves) noiseGens[6];

		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, CAVE);
		ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, RAVINE);
	}

	public AgeDecorator getDecorator() {
		if(decorator == null) {
			decorator = createDecorator();
		}
		return decorator;
	}
	
	public abstract AgeDecorator createDecorator();

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate progressUpdate) {
		return true;
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public boolean chunkExists(int x, int z) {
		return true;
	}

	@Override
	public Chunk loadChunk(int x, int z) {
		return provideChunk(x, z);
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ) {
		random.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
		Block[] blocks = new Block[65536];
		byte[] meta = new byte[65536];
		generateBiomes(chunkX, chunkZ, blocks);
		biomesForGeneration = world.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		replaceBlocksForBiome(chunkX, chunkZ, blocks, meta, biomesForGeneration);
		caveGenerator.func_151539_a(this, world, chunkX, chunkZ, blocks);
		ravineGenerator.func_151539_a(this, world, chunkX, chunkZ, blocks);

		if(mapFeaturesEnabled) {
			provideMapGenerators(chunkX, chunkZ, blocks, meta);
		}
		Chunk chunk = new Chunk(world, blocks, meta, chunkX, chunkZ);
		byte[] biomes = chunk.getBiomeArray();
		for(int i = 0; i < biomes.length; ++i) {
			biomes[i] = (byte) biomesForGeneration[i].biomeID;
		}
		chunk.generateSkylightMap();
		return chunk;
	}

	public void generateBiomes(int x, int z, Block[] blocks) {
		byte b0 = 63;
		biomesForGeneration = world.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		generateBiomes2(x * 4, 0, z * 4);
		for(int k = 0; k < 4; ++k) {
			int l = k * 5;
			int i1 = (k + 1) * 5;
			for(int j1 = 0; j1 < 4; ++j1) {
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;
				for(int k2 = 0; k2 < 32; ++k2) {
					double d0 = 0.125D;
					double d1 = data[k1 + k2];
					double d2 = data[l1 + k2];
					double d3 = data[i2 + k2];
					double d4 = data[j2 + k2];
					double d5 = (data[k1 + k2 + 1] - d1) * d0;
					double d6 = (data[l1 + k2 + 1] - d2) * d0;
					double d7 = (data[i2 + k2 + 1] - d3) * d0;
					double d8 = (data[j2 + k2 + 1] - d4) * d0;
					for(int l2 = 0; l2 < 8; ++l2) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for(int i3 = 0; i3 < 4; ++i3) {
							int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
							short short1 = 256;
							j3 -= short1;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;
							for(int k3 = 0; k3 < 4; ++k3) {
								if((d15 += d16) > 0.0D) {
									blocks[j3 += short1] = getDefaultBlock();
								} else if(k2 * 8 + l2 < b0) {
									blocks[j3 += short1] = getDefaultFluid();
								} else {
									blocks[j3 += short1] = null;
								}
							}
							d10 += d12;
							d11 += d13;
						}
						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	private void generateBiomes2(int x, int y, int z) {
		// double d0 = 684.412D;
		// double d1 = 684.412D;
		// double d2 = 512.0D;
		// double d3 = 512.0D;
		noiseData4 = noiseGen6.generateNoiseOctaves(noiseData4, x, z, 5, 5, 200.0D, 200.0D, 0.5D);
		noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, x, y, z, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, x, y, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, x, y, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		// boolean flag1 = false;
		// boolean flag = false;
		int l = 0;
		int i1 = 0;
		// double d4 = 8.5D;
		for(int j1 = 0; j1 < 5; ++j1) {
			for(int k1 = 0; k1 < 5; ++k1) {
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biome1 = biomesForGeneration[j1 + 2 + (k1 + 2) * 10];
				for(int l1 = -b0; l1 <= b0; ++l1) {
					for(int i2 = -b0; i2 <= b0; ++i2) {
						BiomeGenBase biome2 = biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f3 = biome2.rootHeight;
						float f4 = biome2.heightVariation;
						if(worldType == WorldType.AMPLIFIED && f3 > 0.0F) {
							f3 = 1.0F + f3 * 2.0F;
							f4 = 1.0F + f4 * 4.0F;
						}
						float f5 = parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);
						if(biome2.rootHeight > biome1.rootHeight) {
							f5 /= 2.0F;
						}
						f += f4 * f5;
						f1 += f3 * f5;
						f2 += f5;
					}
				}
				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				double d13 = noiseData4[i1] / 8000.0D;
				if(d13 < 0.0D) {
					d13 = -d13 * 0.3D;
				}
				d13 = d13 * 3.0D - 2.0D;
				if(d13 < 0.0D) {
					d13 /= 2.0D;
					if(d13 < -1.0D) {
						d13 = -1.0D;
					}
					d13 /= 1.4D;
					d13 /= 2.0D;
				} else {
					if(d13 > 1.0D) {
						d13 = 1.0D;
					}
					d13 /= 8.0D;
				}
				i1++;
				double d12 = (double) f1;
				double d14 = (double) f;
				d12 += d13 * 0.2D;
				d12 = d12 * 8.5D / 8.0D;
				double d5 = 8.5D + d12 * 4.0D;
				for(int j2 = 0; j2 < 33; ++j2) {
					double d6 = ((double) j2 - d5) * 12.0D * 128.0D / 256.0D / d14;
					if(d6 < 0.0D) {
						d6 *= 4.0D;
					}
					double d7 = this.noiseData2[l] / 512.0D;
					double d8 = this.noiseData3[l] / 512.0D;
					double d9 = (this.noiseData1[l] / 10.0D + 1.0D) / 2.0D;
					double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;
					if(j2 > 29) {
						double d11 = (double) ((float) (j2 - 29) / 3.0F);
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}
					data[l] = d10;
					l++;
				}
			}
		}
	}

	public void replaceBlocksForBiome(int x, int y, Block[] blocks, byte[] meta, BiomeGenBase[] biomes) {
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, x, y, blocks, biomes);
		MinecraftForge.EVENT_BUS.post(event);
		if(event.getResult() == Result.DENY) {
			return;
		}
		double d = 0.03125D;
		stoneNoise = noiseGen4.func_151599_a(stoneNoise, (double) (x * 16), (double) (y * 16), 16, 16, d * 2.0D, d * 2.0D, 1.0D);
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				BiomeGenBase biome = biomes[j + i * 16];
				biome.genTerrainBlocks(world, random, blocks, meta, x * 16 + i, y * 16 + j, stoneNoise[j + i * 16]);
			}
		}
	}

	@Override
	public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
		BlockFalling.fallInstantly = true;
		int x = chunkX * 16;
		int z = chunkZ * 16;
		BiomeGenBase biome = world.getBiomeGenForCoords(x + 16, z + 16);
		random.setSeed(world.getSeed());
		long randLong1 = random.nextLong() / 2L * 2L + 1L;
		long randLong2 = random.nextLong() / 2L * 2L + 1L;
		random.setSeed((long) chunkX * randLong1 + (long) chunkZ * randLong2 ^ world.getSeed());
		boolean flag = false;
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, world, random, chunkX, chunkZ, flag));
		if(mapFeaturesEnabled) {
			populateMapGenerators(chunkProvider, chunkX, chunkX);
		}
		int xx;
		int yy;
		int zz;
		if(biome != BiomeGenBase.desert && biome != BiomeGenBase.desertHills && !flag && random.nextInt(4) == 0 && TerrainGen.populate(chunkProvider, world, random, chunkX, chunkZ, flag, LAKE)) {
			xx = x + random.nextInt(16) + 8;
			yy = random.nextInt(256);
			zz = z + random.nextInt(16) + 8;
			(new WorldGenLakes(Blocks.water)).generate(world, random, xx, yy, zz);
		}
		if(TerrainGen.populate(chunkProvider, world, random, chunkX, chunkZ, flag, LAVA) && !flag && this.random.nextInt(8) == 0) {
			xx = x + random.nextInt(16) + 8;
			yy = random.nextInt(random.nextInt(248) + 8);
			zz = z + random.nextInt(16) + 8;
			if(yy < 63 || random.nextInt(10) == 0) {
				(new WorldGenLakes(Blocks.lava)).generate(world, random, xx, yy, zz);
			}
		}
		boolean doGen = TerrainGen.populate(chunkProvider, world, random, chunkX, chunkZ, flag, DUNGEON);
		for(xx = 0; doGen && xx < 8; ++xx) {
			yy = x + random.nextInt(16) + 8;
			zz = random.nextInt(256);
			int j2 = z + random.nextInt(16) + 8;
			(new WorldGenDungeons()).generate(world, random, yy, zz, j2);
		}

		if(biome instanceof AgeBiome) {
			AgeBiome ageBiome = (AgeBiome) biome;
			ageBiome.chunkProvider = this;
			ageBiome.decorate(world, random, x, z);
			ageBiome.chunkProvider = null;
		} else {
			biome.decorate(world, random, x, z);
		}
		SpawnerAnimals.performWorldGenSpawning(world, biome, x + 8, z + 8, 16, 16, random);
		x += 8;
		z += 8;

		doGen = TerrainGen.populate(chunkProvider, world, random, chunkX, chunkZ, flag, ICE);
		for(xx = 0; doGen && xx < 16; ++xx) {
			for(yy = 0; yy < 16; ++yy) {
				zz = world.getPrecipitationHeight(x + xx, z + yy);
				if(world.isBlockFreezable(xx + x, zz - 1, yy + z)) {
					world.setBlock(xx + x, zz - 1, yy + z, Blocks.ice, 0, 2);
				}
				if(world.func_147478_e(xx + x, zz, yy + z, true)) {
					world.setBlock(xx + x, zz, yy + z, Blocks.snow_layer, 0, 2);
				}
			}
		}
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, world, random, chunkX, chunkZ, flag));
		BlockFalling.fallInstantly = false;
	}

	@Override
	public String makeString() {
		return EQUtil.firstUpperCase(age.ageName) + "AgeLevelSource";
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, int var2, int var3, int var4) {
		return null;
	}

	@Override
	public ChunkPosition func_147416_a(World world, String var2, int var3, int var4, int var5) {
		return null;
	}

	@Override
	public void recreateStructures(int x, int z) {
	}

	@Override
	public void saveExtraData() {
	}
	
	public abstract Block getDefaultBlock();
	
	public abstract Block getDefaultFluid();
	
	public abstract void provideMapGenerators(int chunkX, int chunkZ, Block[] blocks, byte[] meta);
	
	public abstract void populateMapGenerators(IChunkProvider chunkProvider, int chunkX, int chunkZ);
}
