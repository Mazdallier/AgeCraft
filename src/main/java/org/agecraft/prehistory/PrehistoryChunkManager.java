package org.agecraft.prehistory;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
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
import org.agecraft.core.biomes.AgeBiome;
import org.agecraft.core.biomes.BiomeJungle;
import org.agecraft.core.biomes.Biomes;
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
		return Arrays.asList(Biomes.plains);
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
		AgeGenLayerAddSpecialIsland genLayerAddSpecialIsland = new AgeGenLayerAddSpecialIsland(5L, genLayerAddIsland, Biomes.timelessIsland);
		AgeGenLayerDeepOcean genLayerDeepOcean = new AgeGenLayerDeepOcean(4L, genLayerAddSpecialIsland, Biomes.deepOcean);
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
		AgeGenLayerRiver genLayerRiver = new AgeGenLayerRiver(1L, genLayerPreRiverInit, Biomes.river);
		GenLayerSmooth genLayerSmooth1 = new GenLayerSmooth(1000L, genLayerRiver);

		genLayerBiome = new AgeGenLayerRareBiome(1001L, genLayerHills, Biomes.plains, ((AgeBiome) Biomes.plains).getMutation());
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
			new BiomeGenBase[]{Biomes.desert, Biomes.desert, Biomes.desert, Biomes.savanna, Biomes.savanna, Biomes.plains}, 
			new BiomeGenBase[]{Biomes.forest1, Biomes.roofedForest, Biomes.extremeHills, Biomes.plains, Biomes.forest2, Biomes.swamp}, 
			new BiomeGenBase[]{Biomes.forest1, Biomes.extremeHills, Biomes.taiga, Biomes.plains},
			new BiomeGenBase[]{Biomes.icePlains, Biomes.icePlains, Biomes.icePlains, Biomes.alpine, Biomes.snowForest}
		};
	}

	@Override
	public int getBiomeInt(AgeGenLayerBiome genLayer, int oldValue1, int oldValue2, BiomeGenBase[][] biomeGroups) {
		if(AgeGenLayerBiome.isBiomeOceanic(oldValue1)) {
			return oldValue1;
		} else if(oldValue1 == Biomes.timelessIsland.biomeID) {
			return oldValue1;
		} else if(oldValue1 == 1) {
			if(oldValue2 > 0) {
				if(genLayer.nextInt(3) == 0) {
					return Biomes.mesaPlateau.biomeID;
				} else {
					return Biomes.mesaPlateauF.biomeID;
				}
			} else {
				return biomeGroups[0][genLayer.nextInt(biomeGroups[0].length)].biomeID;
			}
		} else if(oldValue1 == 2) {
			if(oldValue2 > 0) {
				return Biomes.jungle.biomeID;
			} else {
				return biomeGroups[1][genLayer.nextInt(biomeGroups[1].length)].biomeID;
			}
		} else if(oldValue1 == 3) {
			if(oldValue2 > 0) {
				return Biomes.tundra.biomeID;
			} else {
				return biomeGroups[2][genLayer.nextInt(biomeGroups[2].length)].biomeID;
			}
		} else if(oldValue1 == 4) {
			return biomeGroups[3][genLayer.nextInt(biomeGroups[3].length)].biomeID;
		} else {
			return Biomes.timelessIsland.biomeID;
		}
	}

	@Override
	public int getBiomeEdgeInt(AgeGenLayerBiomeEdge genLayer, int[] oldList, int[] list, int j, int i, int width, int oldValue) {
		if(!genLayer.isBiomeSuitable1(oldList, list, j, i, width, oldValue, Biomes.extremeHills.biomeID, Biomes.extremeHillsEdge.biomeID) && !genLayer.isBiomeSuitable2(oldList, list, j, i, width, oldValue, Biomes.mesaPlateauF.biomeID, Biomes.mesa.biomeID) && !genLayer.isBiomeSuitable2(oldList, list, j, i, width, oldValue, Biomes.mesaPlateau.biomeID, Biomes.mesa.biomeID)
				&& !genLayer.isBiomeSuitable2(oldList, list, j, i, width, oldValue, Biomes.alpine.biomeID, Biomes.taiga.biomeID)) {
			int side1 = oldList[j + 1 + (i + 1 - 1) * (width + 2)];
			int side2 = oldList[j + 1 + 1 + (i + 1) * (width + 2)];
			int side3 = oldList[j + 1 - 1 + (i + 1) * (width + 2)];
			int side4 = oldList[j + 1 + (i + 1 + 1) * (width + 2)];
			if(oldValue == Biomes.desert.biomeID) {
				if(side1 != Biomes.icePlains.biomeID && side2 != Biomes.icePlains.biomeID && side3 != Biomes.icePlains.biomeID && side4 != Biomes.icePlains.biomeID) {
					return oldValue;
				} else {
					return Biomes.extremeHillsPlus.biomeID;
				}
			} else if(oldValue == Biomes.swamp.biomeID) {
				if(side1 != Biomes.desert.biomeID && side2 != Biomes.desert.biomeID && side3 != Biomes.desert.biomeID && side4 != Biomes.desert.biomeID && side1 != Biomes.tundra.biomeID && side2 != Biomes.tundra.biomeID && side3 != Biomes.tundra.biomeID && side4 != Biomes.tundra.biomeID && side1 != Biomes.icePlains.biomeID && side2 != Biomes.icePlains.biomeID && side3 != Biomes.icePlains.biomeID
						&& side4 != Biomes.icePlains.biomeID) {
					if(side1 != Biomes.jungle.biomeID && side4 != Biomes.jungle.biomeID && side2 != Biomes.jungle.biomeID && side3 != Biomes.jungle.biomeID) {
						return oldValue;
					} else {
						return Biomes.jungleEdge.biomeID;
					}
				} else {
					return Biomes.plains.biomeID;
				}
			} else {
				return oldValue;
			}
		}
		return oldValue;
	}

	@Override
	public int getHillsInt(AgeGenLayerHills genLayer, int oldValue) {
		if(oldValue == Biomes.desert.biomeID) {
			return Biomes.desertHills.biomeID;
		} else if(oldValue == Biomes.forest1.biomeID) {
			return Biomes.forestHills1.biomeID;
		} else if(oldValue == Biomes.forest2.biomeID) {
			return Biomes.forestHills2.biomeID;
		} else if(oldValue == Biomes.forest3.biomeID) {
			return Biomes.forestHills3.biomeID;
		} else if(oldValue == Biomes.roofedForest.biomeID) {
			return Biomes.plains.biomeID;
		} else if(oldValue == Biomes.taiga.biomeID) {
			return Biomes.taigaHills.biomeID;
		} else if(oldValue == Biomes.alpine.biomeID) {
			return Biomes.glacier.biomeID;
		} else if(oldValue == Biomes.tundra.biomeID) {
			return Biomes.alpine.biomeID;
		} else if(oldValue == Biomes.plains.biomeID) {
			if(genLayer.nextInt(3) == 0) {
				return Biomes.forestHills1.biomeID;
			} else {
				return Biomes.forest1.biomeID;
			}
		} else if(oldValue == Biomes.icePlains.biomeID) {
			return Biomes.alpine.biomeID;
		} else if(oldValue == Biomes.jungle.biomeID) {
			return Biomes.jungleHills.biomeID;
		} else if(oldValue == Biomes.ocean.biomeID) {
			return Biomes.deepOcean.biomeID;
		} else if(oldValue == Biomes.extremeHills.biomeID) {
			return Biomes.extremeHillsPlus.biomeID;
		} else if(oldValue == Biomes.savanna.biomeID) {
			return Biomes.savannaPlateau.biomeID;
		} else if(AgeGenLayerHills.compareBiomesById(oldValue, Biomes.mesaPlateauF.biomeID)) {
			return Biomes.mesa.biomeID;
		} else if(oldValue == Biomes.deepOcean.biomeID && genLayer.nextInt(3) == 0) {
			if(genLayer.nextInt(2) == 0) {
				return Biomes.plains.biomeID;
			} else {
				return Biomes.forest1.biomeID;
			}
		}
		return oldValue;
	}

	@Override
	public int getRiverMixInt(AgeGenLayerRiverMix genLayer, int biome, int river) {
		if(biome != Biomes.ocean.biomeID && biome != Biomes.deepOcean.biomeID) {
			if(river == Biomes.river.biomeID) {
				if(biome == Biomes.icePlains.biomeID) {
					return Biomes.frozenRiver.biomeID;
				} else if(biome != Biomes.timelessIsland.biomeID && biome != Biomes.timelessIslandShore.biomeID) {
					return river & 255;
				} else {
					return Biomes.timelessIslandShore.biomeID;
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
		if(oldValue == Biomes.timelessIsland.biomeID) {
			if(side1 != Biomes.ocean.biomeID && side2 != Biomes.ocean.biomeID && side3 != Biomes.ocean.biomeID && side4 != Biomes.ocean.biomeID) {
				list[j + i * width] = oldValue;
			} else {
				list[j + i * width] = Biomes.timelessIslandShore.biomeID;
			}
		} else if(oldBiome != null && oldBiome.getBiomeClass() == BiomeJungle.class) {
			if(genLayer.isJunleOrForestOrTaigaOrOcean(side1) && genLayer.isJunleOrForestOrTaigaOrOcean(side2) && genLayer.isJunleOrForestOrTaigaOrOcean(side3) && genLayer.isJunleOrForestOrTaigaOrOcean(side4)) {
				if(!AgeGenLayerBiome.isBiomeOceanic(side1) && !AgeGenLayerBiome.isBiomeOceanic(side2) && !AgeGenLayerBiome.isBiomeOceanic(side3) && !AgeGenLayerBiome.isBiomeOceanic(side4)) {
					list[j + i * width] = oldValue;
				} else {
					list[j + i * width] = Biomes.beach.biomeID;
				}
			} else {
				list[j + i * width] = Biomes.jungleEdge.biomeID;
			}
		} else if(oldValue != Biomes.extremeHills.biomeID && oldValue != Biomes.extremeHillsPlus.biomeID && oldValue != Biomes.extremeHillsEdge.biomeID) {
			if(oldBiome != null && oldBiome.func_150559_j()) {
				genLayer.createBeachIfSuitable(oldList, list, j, i, width, oldValue, Biomes.snowBeach.biomeID);
			} else if(oldValue != Biomes.mesa.biomeID && oldValue != Biomes.mesaPlateauF.biomeID) {
				if(oldValue != Biomes.ocean.biomeID && oldValue != Biomes.deepOcean.biomeID && oldValue != Biomes.river.biomeID && oldValue != Biomes.swamp.biomeID) {
					if(!AgeGenLayerBiome.isBiomeOceanic(side1) && !AgeGenLayerBiome.isBiomeOceanic(side2) && !AgeGenLayerBiome.isBiomeOceanic(side3) && !AgeGenLayerBiome.isBiomeOceanic(side4)) {
						list[j + i * width] = oldValue;
					} else {
						list[j + i * width] = Biomes.beach.biomeID;
					}
				} else {
					list[j + i * width] = oldValue;
				}
			} else {
				if(!AgeGenLayerBiome.isBiomeOceanic(side1) && !AgeGenLayerBiome.isBiomeOceanic(side2) && !AgeGenLayerBiome.isBiomeOceanic(side3) && !AgeGenLayerBiome.isBiomeOceanic(side4)) {
					if(genLayer.isMesaBiome(side1) && genLayer.isMesaBiome(side2) && genLayer.isMesaBiome(side3) && genLayer.isMesaBiome(side4)) {
						list[j + i * width] = oldValue;
					} else {
						list[j + i * width] = Biomes.desert.biomeID;
					}
				} else {
					list[j + i * width] = oldValue;
				}
			}
		} else {
			genLayer.createBeachIfSuitable(oldList, list, j, i, width, oldValue, Biomes.stoneBeach.biomeID);
		}
		return oldValue;
	}
}
