package org.agecraft.prehistory;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
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

import org.agecraft.Age;
import org.agecraft.core.dimension.AgeChunkManager;
import org.agecraft.core.dimension.AgeGenLayerAddSpecialIsland;
import org.agecraft.core.dimension.AgeGenLayerBiome;
import org.agecraft.core.dimension.AgeGenLayerBiomeEdge;
import org.agecraft.core.dimension.AgeGenLayerDeepOcean;
import org.agecraft.core.dimension.AgeGenLayerHills;
import org.agecraft.core.dimension.AgeGenLayerRareBiome;
import org.agecraft.core.dimension.AgeGenLayerRiver;
import org.agecraft.core.dimension.AgeGenLayerRiverMix;
import org.agecraft.core.dimension.AgeGenLayerShore;

public class PrehistoryChunkManager extends AgeChunkManager {

	public PrehistoryChunkManager(long seed, WorldType worldType) {
		super(Age.prehistory, seed, worldType);
	}

	public PrehistoryChunkManager(World world) {
		super(Age.prehistory, world);
	}

	@Override
	public List<BiomeGenBase> getSpawnBiomes() {
		return Arrays.asList(BiomeGenBase.plains);
	}

	@Override
	public GenLayer[] initGenLayers(long seed, WorldType worldType) {
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

		GenLayer genLayerBiome = new AgeGenLayerBiome(this, 200L, genLayer1, worldType);
		genLayerBiome = GenLayerZoom.magnify(1000L, genLayerBiome, 2);
		genLayerBiome = new AgeGenLayerBiomeEdge(this, 1000L, genLayerBiome);

		GenLayer genLayer2 = GenLayerZoom.magnify(1000L, genLayerRiverInit, 2);
		AgeGenLayerHills genLayerHills = new AgeGenLayerHills(this, 1000L, genLayerBiome, genLayer2);
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
				genLayerBiome = new AgeGenLayerShore(this, 1000L, genLayerBiome);
			}
		}
		GenLayerSmooth genLayerSmooth2 = new GenLayerSmooth(1000L, genLayerBiome);
		AgeGenLayerRiverMix genLayerRiverMix = new AgeGenLayerRiverMix(this, 100L, genLayerSmooth2, genLayerSmooth1);
		GenLayerVoronoiZoom genLayerVoronoiZoom = new GenLayerVoronoiZoom(10L, genLayerRiverMix);
		genLayerRiverMix.initWorldGenSeed(seed);
		genLayerVoronoiZoom.initWorldGenSeed(seed);
		return new GenLayer[]{genLayerRiverMix, genLayerVoronoiZoom, genLayerRiverMix};
	}

	@Override
	public BiomeGenBase[][] getBiomeGroups() {
		return new BiomeGenBase[][]{
			new BiomeGenBase[]{BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.savanna, BiomeGenBase.savanna, BiomeGenBase.plains}, 
			new BiomeGenBase[]{BiomeGenBase.forest, BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.birchForest, BiomeGenBase.swampland}, 
			new BiomeGenBase[]{BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains},
			new BiomeGenBase[]{BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.coldTaiga}
		};
	}

	@Override
	public int getBiomeInt(AgeGenLayerBiome genLayer, int oldValue1, int oldValue2, BiomeGenBase[][] biomeGroups) {
		if(AgeGenLayerBiome.isBiomeOceanic(oldValue1)) {
			return oldValue1;
		} else if(oldValue1 == BiomeGenBase.mushroomIsland.biomeID) {
			return oldValue1;
		} else if(oldValue1 == 1) {
			if(oldValue2 > 0) {
				if(genLayer.nextInt(3) == 0) {
					return BiomeGenBase.mesaPlateau.biomeID;
				} else {
					return BiomeGenBase.mesaPlateau_F.biomeID;
				}
			} else {
				return biomeGroups[0][genLayer.nextInt(biomeGroups[0].length)].biomeID;
			}
		} else if(oldValue1 == 2) {
			if(oldValue2 > 0) {
				return BiomeGenBase.jungle.biomeID;
			} else {
				return biomeGroups[1][genLayer.nextInt(biomeGroups[1].length)].biomeID;
			}
		} else if(oldValue1 == 3) {
			if(oldValue2 > 0) {
				return BiomeGenBase.megaTaiga.biomeID;
			} else {
				return biomeGroups[2][genLayer.nextInt(biomeGroups[2].length)].biomeID;
			}
		} else if(oldValue1 == 4) {
			return biomeGroups[3][genLayer.nextInt(biomeGroups[3].length)].biomeID;
		} else {
			return BiomeGenBase.mushroomIsland.biomeID;
		}
	}

	@Override
	public int getBiomeEdgeInt(AgeGenLayerBiomeEdge genLayer, int[] oldList, int[] list, int j, int i, int width, int oldValue) {
		if(!genLayer.isBiomeSuitable1(oldList, list, j, i, width, oldValue, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID) && !genLayer.isBiomeSuitable2(oldList, list, j, i, width, oldValue, BiomeGenBase.mesaPlateau_F.biomeID, BiomeGenBase.mesa.biomeID) && !genLayer.isBiomeSuitable2(oldList, list, j, i, width, oldValue, BiomeGenBase.mesaPlateau.biomeID, BiomeGenBase.mesa.biomeID)
				&& !genLayer.isBiomeSuitable2(oldList, list, j, i, width, oldValue, BiomeGenBase.megaTaiga.biomeID, BiomeGenBase.taiga.biomeID)) {
			int side1 = oldList[j + 1 + (i + 1 - 1) * (width + 2)];
			int side2 = oldList[j + 1 + 1 + (i + 1) * (width + 2)];
			int side3 = oldList[j + 1 - 1 + (i + 1) * (width + 2)];
			int side4 = oldList[j + 1 + (i + 1 + 1) * (width + 2)];
			if(oldValue == BiomeGenBase.desert.biomeID) {
				if(side1 != BiomeGenBase.icePlains.biomeID && side2 != BiomeGenBase.icePlains.biomeID && side3 != BiomeGenBase.icePlains.biomeID && side4 != BiomeGenBase.icePlains.biomeID) {
					return oldValue;
				} else {
					return BiomeGenBase.extremeHillsPlus.biomeID;
				}
			} else if(oldValue == BiomeGenBase.swampland.biomeID) {
				if(side1 != BiomeGenBase.desert.biomeID && side2 != BiomeGenBase.desert.biomeID && side3 != BiomeGenBase.desert.biomeID && side4 != BiomeGenBase.desert.biomeID && side1 != BiomeGenBase.coldTaiga.biomeID && side2 != BiomeGenBase.coldTaiga.biomeID && side3 != BiomeGenBase.coldTaiga.biomeID && side4 != BiomeGenBase.coldTaiga.biomeID && side1 != BiomeGenBase.icePlains.biomeID && side2 != BiomeGenBase.icePlains.biomeID && side3 != BiomeGenBase.icePlains.biomeID
						&& side4 != BiomeGenBase.icePlains.biomeID) {
					if(side1 != BiomeGenBase.jungle.biomeID && side4 != BiomeGenBase.jungle.biomeID && side2 != BiomeGenBase.jungle.biomeID && side3 != BiomeGenBase.jungle.biomeID) {
						return oldValue;
					} else {
						return BiomeGenBase.jungleEdge.biomeID;
					}
				} else {
					return BiomeGenBase.plains.biomeID;
				}
			} else {
				return oldValue;
			}
		}
		return oldValue;
	}

	@Override
	public int getHillsInt(AgeGenLayerHills genLayer, int oldValue) {
		if(oldValue == BiomeGenBase.desert.biomeID) {
			return BiomeGenBase.desertHills.biomeID;
		} else if(oldValue == BiomeGenBase.forest.biomeID) {
			return BiomeGenBase.forestHills.biomeID;
		} else if(oldValue == BiomeGenBase.birchForest.biomeID) {
			return BiomeGenBase.birchForestHills.biomeID;
		} else if(oldValue == BiomeGenBase.roofedForest.biomeID) {
			return BiomeGenBase.plains.biomeID;
		} else if(oldValue == BiomeGenBase.taiga.biomeID) {
			return BiomeGenBase.taigaHills.biomeID;
		} else if(oldValue == BiomeGenBase.megaTaiga.biomeID) {
			return BiomeGenBase.megaTaigaHills.biomeID;
		} else if(oldValue == BiomeGenBase.coldTaiga.biomeID) {
			return BiomeGenBase.coldTaigaHills.biomeID;
		} else if(oldValue == BiomeGenBase.plains.biomeID) {
			if(genLayer.nextInt(3) == 0) {
				return BiomeGenBase.forestHills.biomeID;
			} else {
				return BiomeGenBase.forest.biomeID;
			}
		} else if(oldValue == BiomeGenBase.icePlains.biomeID) {
			return BiomeGenBase.iceMountains.biomeID;
		} else if(oldValue == BiomeGenBase.jungle.biomeID) {
			return BiomeGenBase.jungleHills.biomeID;
		} else if(oldValue == BiomeGenBase.ocean.biomeID) {
			return BiomeGenBase.deepOcean.biomeID;
		} else if(oldValue == BiomeGenBase.extremeHills.biomeID) {
			return BiomeGenBase.extremeHillsPlus.biomeID;
		} else if(oldValue == BiomeGenBase.savanna.biomeID) {
			return BiomeGenBase.savannaPlateau.biomeID;
		} else if(AgeGenLayerHills.compareBiomesById(oldValue, BiomeGenBase.mesaPlateau_F.biomeID)) {
			return BiomeGenBase.mesa.biomeID;
		} else if(oldValue == BiomeGenBase.deepOcean.biomeID && genLayer.nextInt(3) == 0) {
			if(genLayer.nextInt(2) == 0) {
				return BiomeGenBase.plains.biomeID;
			} else {
				return BiomeGenBase.forest.biomeID;
			}
		}
		return oldValue;
	}

	@Override
	public int getRiverMixInt(AgeGenLayerRiverMix genLayer, int biome, int river) {
		if(biome != BiomeGenBase.ocean.biomeID && biome != BiomeGenBase.deepOcean.biomeID) {
			if(river == BiomeGenBase.river.biomeID) {
				if(biome == BiomeGenBase.icePlains.biomeID) {
					return BiomeGenBase.frozenRiver.biomeID;
				} else if(biome != BiomeGenBase.mushroomIsland.biomeID && biome != BiomeGenBase.mushroomIslandShore.biomeID) {
					return river & 255;
				} else {
					return BiomeGenBase.mushroomIslandShore.biomeID;
				}
			} else {
				return biome;
			}
		} else {
			return biome;
		}
	}
	
	@Override
	public int getShoreInt(AgeGenLayerShore genLayer, int[] oldList, int[] list, int j, int i, int width, int oldValue, BiomeGenBase oldBiome) {
		int side1 = oldList[j + 1 + (i + 1 - 1) * (width + 2)];
		int side2 = oldList[j + 1 + 1 + (i + 1) * (width + 2)];
		int side3 = oldList[j + 1 - 1 + (i + 1) * (width + 2)];
		int side4 = oldList[j + 1 + (i + 1 + 1) * (width + 2)];
		if(oldValue == BiomeGenBase.mushroomIsland.biomeID) {
			if(side1 != BiomeGenBase.ocean.biomeID && side2 != BiomeGenBase.ocean.biomeID && side3 != BiomeGenBase.ocean.biomeID && side4 != BiomeGenBase.ocean.biomeID) {
				list[j + i * width] = oldValue;
			} else {
				list[j + i * width] = BiomeGenBase.mushroomIslandShore.biomeID;
			}
		} else if(oldBiome != null && oldBiome.getBiomeClass() == BiomeGenJungle.class) {
			if(genLayer.isJunleOrForestOrTaigaOrOcean(side1) && genLayer.isJunleOrForestOrTaigaOrOcean(side2) && genLayer.isJunleOrForestOrTaigaOrOcean(side3) && genLayer.isJunleOrForestOrTaigaOrOcean(side4)) {
				if(!AgeGenLayerBiome.isBiomeOceanic(side1) && !AgeGenLayerBiome.isBiomeOceanic(side2) && !AgeGenLayerBiome.isBiomeOceanic(side3) && !AgeGenLayerBiome.isBiomeOceanic(side4)) {
					list[j + i * width] = oldValue;
				} else {
					list[j + i * width] = BiomeGenBase.beach.biomeID;
				}
			} else {
				list[j + i * width] = BiomeGenBase.jungleEdge.biomeID;
			}
		} else if(oldValue != BiomeGenBase.extremeHills.biomeID && oldValue != BiomeGenBase.extremeHillsPlus.biomeID && oldValue != BiomeGenBase.extremeHillsEdge.biomeID) {
			if(oldBiome != null && oldBiome.func_150559_j()) {
				genLayer.createBeachIfSuitable(oldList, list, j, i, width, oldValue, BiomeGenBase.coldBeach.biomeID);
			} else if(oldValue != BiomeGenBase.mesa.biomeID && oldValue != BiomeGenBase.mesaPlateau_F.biomeID) {
				if(oldValue != BiomeGenBase.ocean.biomeID && oldValue != BiomeGenBase.deepOcean.biomeID && oldValue != BiomeGenBase.river.biomeID && oldValue != BiomeGenBase.swampland.biomeID) {
					if(!AgeGenLayerBiome.isBiomeOceanic(side1) && !AgeGenLayerBiome.isBiomeOceanic(side2) && !AgeGenLayerBiome.isBiomeOceanic(side3) && !AgeGenLayerBiome.isBiomeOceanic(side4)) {
						list[j + i * width] = oldValue;
					} else {
						list[j + i * width] = BiomeGenBase.beach.biomeID;
					}
				} else {
					list[j + i * width] = oldValue;
				}
			} else {
				if(!AgeGenLayerBiome.isBiomeOceanic(side1) && !AgeGenLayerBiome.isBiomeOceanic(side2) && !AgeGenLayerBiome.isBiomeOceanic(side3) && !AgeGenLayerBiome.isBiomeOceanic(side4)) {
					if(genLayer.isMesaBiome(side1) && genLayer.isMesaBiome(side2) && genLayer.isMesaBiome(side3) && genLayer.isMesaBiome(side4)) {
						list[j + i * width] = oldValue;
					} else {
						list[j + i * width] = BiomeGenBase.desert.biomeID;
					}
				} else {
					list[j + i * width] = oldValue;
				}
			}
		} else {
			genLayer.createBeachIfSuitable(oldList, list, j, i, width, oldValue, BiomeGenBase.stoneBeach.biomeID);
		}
		return oldValue;
	}
}
