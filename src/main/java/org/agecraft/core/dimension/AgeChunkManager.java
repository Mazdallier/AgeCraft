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
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
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
	
	public static GenLayer[] initVanillaGenLayers(AgeChunkManager ageManager, long seed, WorldType worldType) {
		boolean flag = false;
		GenLayerIsland genLayerIsland = new GenLayerIsland(1L);
		GenLayerFuzzyZoom genLayerFuzzyZoom = new GenLayerFuzzyZoom(2000L, genLayerIsland);
		GenLayerAddIsland genLayerAddIsland = new GenLayerAddIsland(1L, genLayerFuzzyZoom);
		GenLayerZoom genLayerZoom = new GenLayerZoom(2001L, genLayerAddIsland);
		genLayerAddIsland = new GenLayerAddIsland(2L, genLayerZoom);
		genLayerAddIsland = new GenLayerAddIsland(50L, genLayerAddIsland);
		genLayerAddIsland = new GenLayerAddIsland(70L, genLayerAddIsland);
		GenLayerRemoveTooMuchOcean genLayerRemoveTooMuchOcean = new GenLayerRemoveTooMuchOcean(2L, genLayerAddIsland);
		GenLayerAddSnow genLayerAddSnow = new GenLayerAddSnow(2L, genLayerRemoveTooMuchOcean);
		genLayerAddIsland = new GenLayerAddIsland(3L, genLayerAddSnow);
		GenLayerEdge genLayerEdge = new GenLayerEdge(2L, genLayerAddIsland, GenLayerEdge.Mode.COOL_WARM);
		genLayerEdge = new GenLayerEdge(2L, genLayerEdge, GenLayerEdge.Mode.HEAT_ICE);
		genLayerEdge = new GenLayerEdge(3L, genLayerEdge, GenLayerEdge.Mode.SPECIAL);
		genLayerZoom = new GenLayerZoom(2002L, genLayerEdge);
		genLayerZoom = new GenLayerZoom(2003L, genLayerZoom);
		genLayerAddIsland = new GenLayerAddIsland(4L, genLayerZoom);
		AgeGenLayerAddSpecialIsland genLayerAddSpecialIsland = new AgeGenLayerAddSpecialIsland(5L, genLayerAddIsland, BiomeGenBase.mushroomIsland);
		AgeGenLayerDeepOcean genLayerDeepOcean = new AgeGenLayerDeepOcean(4L, genLayerAddSpecialIsland, BiomeGenBase.deepOcean);
		GenLayer genLayer1 = GenLayerZoom.magnify(1000L, genLayerDeepOcean, 0);
		byte biomeSize = 4;
		if(worldType == WorldType.LARGE_BIOMES) {
			biomeSize = 6;
		}
		if(flag) {
			biomeSize = 4;
		}
		biomeSize = GenLayer.getModdedBiomeSize(worldType, biomeSize);

		GenLayer genLayerPreRiverInit = GenLayerZoom.magnify(1000L, genLayer1, 0);
		GenLayerRiverInit genLayerRiverInit = new GenLayerRiverInit(100L, genLayerPreRiverInit);

		GenLayer genLayerBiome = new AgeGenLayerBiome(ageManager, 200L, genLayer1, worldType);
		genLayerBiome = GenLayerZoom.magnify(1000L, genLayerBiome, 2);
		genLayerBiome = new AgeGenLayerBiomeEdge(ageManager, 1000L, genLayerBiome);

		GenLayer genLayer2 = GenLayerZoom.magnify(1000L, genLayerRiverInit, 2);
		AgeGenLayerHills genLayerHills = new AgeGenLayerHills(ageManager, 1000L, genLayerBiome, genLayer2);
		genLayerPreRiverInit = GenLayerZoom.magnify(1000L, genLayerRiverInit, 2);
		genLayerPreRiverInit = GenLayerZoom.magnify(1000L, genLayerPreRiverInit, biomeSize);
		AgeGenLayerRiver genLayerRiver = new AgeGenLayerRiver(1L, genLayerPreRiverInit, BiomeGenBase.river);
		GenLayerSmooth genLayerSmooth1 = new GenLayerSmooth(1000L, genLayerRiver);

		genLayerBiome = new AgeGenLayerRareBiome(1001L, genLayerHills, BiomeGenBase.plains, BiomeGenBase.getBiome(BiomeGenBase.plains.biomeID + 128));
		for(int i = 0; i < biomeSize; ++i) {
			genLayerBiome = new GenLayerZoom((long) (1000 + i), genLayerBiome);
			if(i == 0) {
				genLayerBiome = new GenLayerAddIsland(3L, genLayerBiome);
			}
			if(i == 1) {
				genLayerBiome = new AgeGenLayerShore(ageManager, 1000L, genLayerBiome);
			}
		}
		GenLayerSmooth genLayerSmooth2 = new GenLayerSmooth(1000L, genLayerBiome);
		AgeGenLayerRiverMix genLayerRiverMix = new AgeGenLayerRiverMix(ageManager, 100L, genLayerSmooth2, genLayerSmooth1);
		GenLayerVoronoiZoom genLayerVoronoiZoom = new GenLayerVoronoiZoom(10L, genLayerRiverMix);
		genLayerRiverMix.initWorldGenSeed(seed);
		genLayerVoronoiZoom.initWorldGenSeed(seed);
		return new GenLayer[]{genLayerRiverMix, genLayerVoronoiZoom, genLayerRiverMix};
	}

	public abstract List<BiomeGenBase> getSpawnBiomes();

	public abstract BiomeGenBase[][] getBiomeGroups();
	
	public abstract GenLayer[] initGenLayers(long seed, WorldType worldType);
	
	public abstract int getBiomeInt(AgeGenLayerBiome genLayer, int oldValue1, int oldValue2, BiomeGenBase[][] biomeGroups);
	
	public abstract int getBiomeEdgeInt(AgeGenLayerBiomeEdge genLayer, int[] oldList, int[] list, int j, int i, int width, int oldValue);
	
	public abstract int getHillsInt(AgeGenLayerHills genLayer, int oldValue);
	
	public abstract int getRiverMixInt(AgeGenLayerRiverMix genLayer, int biome, int river);
	
	public abstract int getShoreInt(AgeGenLayerShore genLayer, int[] oldList, int[] list, int j, int i, int width, int oldValue, BiomeGenBase oldBiome);
}
