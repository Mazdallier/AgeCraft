package org.agecraft.core.dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import org.agecraft.Age;

public abstract class AgeChunkManager extends WorldChunkManager {

	public Age age;

	public GenLayer genBiomes;
	public GenLayer biomeIndexLayer;
	public BiomeCache biomeCache;
	public ArrayList<BiomeGenBase> biomesToSpawnIn;

	private AgeChunkManager(Age age) {
		this.age = age;
		biomeCache = new BiomeCache(this);
		biomesToSpawnIn = new ArrayList<BiomeGenBase>();
		biomesToSpawnIn.addAll(getSpawnBiomes());
	}

	public AgeChunkManager(Age age, long seed, WorldType worldType) {
		this(age);
		GenLayer[] genLayers = initGenLayers(seed, worldType);
		genLayers = getModdedBiomeGenerators(worldType, seed, genLayers);
		this.genBiomes = genLayers[0];
		this.biomeIndexLayer = genLayers[1];
	}

	public AgeChunkManager(Age age, World world) {
		this(age, world.getSeed(), world.getWorldInfo().getTerrainType());
	}

	@Override
	public List getBiomesToSpawnIn() {
		return biomesToSpawnIn;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int x, int z) {
		return biomeCache.getBiomeGenAt(x, z);
	}

	@Override
	public float[] getRainfall(float[] listToUse, int x, int z, int width, int length) {
		IntCache.resetIntCache();
		if(listToUse == null || listToUse.length < width * length) {
			listToUse = new float[width * length];
		}
		int[] rainfall = biomeIndexLayer.getInts(x, z, width, length);
		for(int i = 0; i < width * length; ++i) {
			try {
				float f = (float) BiomeGenBase.getBiome(rainfall[i]).getIntRainfall() / 65536.0F;
				if(f > 1.0F) {
					f = 1.0F;
				}
				listToUse[i] = f;
			} catch(Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("DownfallBlock");
				crashreportcategory.addCrashSection("biome id", Integer.valueOf(i));
				crashreportcategory.addCrashSection("downfalls[] size", Integer.valueOf(listToUse.length));
				crashreportcategory.addCrashSection("x", Integer.valueOf(x));
				crashreportcategory.addCrashSection("z", Integer.valueOf(z));
				crashreportcategory.addCrashSection("w", Integer.valueOf(width));
				crashreportcategory.addCrashSection("h", Integer.valueOf(length));
				throw new ReportedException(crashreport);
			}
		}
		return listToUse;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomeList, int x, int y, int width, int length) {
		IntCache.resetIntCache();
		if(biomeList == null || biomeList.length < width * length) {
			biomeList = new BiomeGenBase[width * length];
		}
		int[] biomes = genBiomes.getInts(x, y, width, length);
		try {
			for(int i = 0; i < width * length; ++i) {
				biomeList[i] = BiomeGenBase.getBiome(biomes[i]);
			}
			return biomeList;
		} catch(Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
			crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomeList.length));
			crashreportcategory.addCrashSection("x", Integer.valueOf(x));
			crashreportcategory.addCrashSection("z", Integer.valueOf(y));
			crashreportcategory.addCrashSection("w", Integer.valueOf(width));
			crashreportcategory.addCrashSection("h", Integer.valueOf(length));
			throw new ReportedException(crashreport);
		}
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomeList, int x, int z, int width, int length, boolean cacheFlag) {
		IntCache.resetIntCache();
		if(biomeList == null || biomeList.length < width * length) {
			biomeList = new BiomeGenBase[width * length];
		}
		if(cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
			BiomeGenBase[] abiomegenbase1 = biomeCache.getCachedBiomes(x, z);
			System.arraycopy(abiomegenbase1, 0, biomeList, 0, width * length);
			return biomeList;
		} else {
			int[] biomes = biomeIndexLayer.getInts(x, z, width, length);
			for(int i = 0; i < width * length; ++i) {
				biomeList[i] = BiomeGenBase.getBiome(biomes[i]);
			}
			return biomeList;
		}
	}

	@Override
	public boolean areBiomesViable(int x, int y, int radius, List list) {
		IntCache.resetIntCache();
		int x1 = x - radius >> 2;
		int z1 = y - radius >> 2;
		int x2 = x + radius >> 2;
		int z2 = y + radius >> 2;
		int width = x2 - x1 + 1;
		int length = z2 - z1 + 1;
		int[] biomes = genBiomes.getInts(x1, z1, width, length);
		try {
			for(int i = 0; i < width * length; ++i) {
				BiomeGenBase biome = BiomeGenBase.getBiome(biomes[i]);
				if(!list.contains(biome)) {
					return false;
				}
			}
			return true;
		} catch(Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
			crashreportcategory.addCrashSection("Layer", genBiomes.toString());
			crashreportcategory.addCrashSection("x", Integer.valueOf(x));
			crashreportcategory.addCrashSection("z", Integer.valueOf(y));
			crashreportcategory.addCrashSection("radius", Integer.valueOf(radius));
			crashreportcategory.addCrashSection("allowed", list);
			throw new ReportedException(crashreport);
		}
	}

	@Override
	public ChunkPosition findBiomePosition(int x, int z, int radius, List list, Random random) {
		IntCache.resetIntCache();
		int x1 = x - radius >> 2;
		int z1 = z - radius >> 2;
		int x2 = x + radius >> 2;
		int z2 = z + radius >> 2;
		int width = x2 - x1 + 1;
		int length = z2 - z1 + 1;
		int[] biomes = genBiomes.getInts(x1, z1, width, length);
		ChunkPosition chunkposition = null;
		int index = 0;
		for(int i = 0; i < width * length; ++i) {
			int xx = x1 + i % width << 2;
			int zz = z1 + i / width << 2;
			BiomeGenBase biome = BiomeGenBase.getBiome(biomes[i]);
			if(list.contains(biome) && (chunkposition == null || random.nextInt(index + 1) == 0)) {
				chunkposition = new ChunkPosition(xx, 0, zz);
				index++;
			}
		}
		return chunkposition;
	}

	@Override
	public void cleanupCache() {
		biomeCache.cleanupCache();
	}

	public abstract List<BiomeGenBase> getSpawnBiomes();

	public abstract GenLayer[] initGenLayers(long seed, WorldType worldType);
}
