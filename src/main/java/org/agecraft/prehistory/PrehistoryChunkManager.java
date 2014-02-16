package org.agecraft.prehistory;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

import org.agecraft.Age;
import org.agecraft.core.dimension.AgeChunkManager;

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
		//TODO: change: GenLayerAddMushroomIsland, GenLayerDeepOcean, GenLayerHills, GenLayerRareBiome, GenLayerRiver, GenLayerShore, GenLayerRiverMix, GenLayerBiome, GenLayerBiomeEdge
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
		GenLayerAddMushroomIsland genLayerAddMushroomIsland = new GenLayerAddMushroomIsland(5L, genLayerAddIsland);
		GenLayerDeepOcean genLayerDeepOcean = new GenLayerDeepOcean(4L, genLayerAddMushroomIsland);
		GenLayer genLayer1 = GenLayerZoom.magnify(1000L, genLayerDeepOcean, 0);
		byte b0 = 4;
		if(worldType == WorldType.LARGE_BIOMES) {
			b0 = 6;
		}
		if(flag) {
			b0 = 4;
		}
		b0 = GenLayer.getModdedBiomeSize(worldType, b0);

		GenLayer genLayer2 = GenLayerZoom.magnify(1000L, genLayer1, 0);
		GenLayerRiverInit genLayerRiverInit = new GenLayerRiverInit(100L, genLayer2);
		Object genLayer4 = worldType.getBiomeLayer(seed, genLayer1);

		GenLayer genLayer3 = GenLayerZoom.magnify(1000L, genLayerRiverInit, 2);
		GenLayerHills genLayerHills = new GenLayerHills(1000L, (GenLayer) genLayer4, genLayer3);
		genLayer2 = GenLayerZoom.magnify(1000L, genLayerRiverInit, 2);
		genLayer2 = GenLayerZoom.magnify(1000L, genLayer2, b0);
		GenLayerRiver genLayerRiver = new GenLayerRiver(1L, genLayer2);
		GenLayerSmooth genLayerSmooth1 = new GenLayerSmooth(1000L, genLayerRiver);

		genLayer4 = new GenLayerRareBiome(1001L, genLayerHills);
		for(int i = 0; i < b0; ++i) {
			genLayer4 = new GenLayerZoom((long) (1000 + i), (GenLayer) genLayer4);
			if(i == 0) {
				genLayer4 = new GenLayerAddIsland(3L, (GenLayer) genLayer4);
			}
			if(i == 1) {
				genLayer4 = new GenLayerShore(1000L, (GenLayer) genLayer4);
			}
		}
		GenLayerSmooth genLayerSmooth2 = new GenLayerSmooth(1000L, (GenLayer) genLayer4);
		GenLayerRiverMix genLayerRiverMix = new GenLayerRiverMix(100L, genLayerSmooth2, genLayerSmooth1);
		GenLayerVoronoiZoom genLayerVoronoiZoom = new GenLayerVoronoiZoom(10L, genLayerRiverMix);
		genLayerRiverMix.initWorldGenSeed(seed);
		genLayerVoronoiZoom.initWorldGenSeed(seed);
		return new GenLayer[]{genLayerRiverMix, genLayerVoronoiZoom, genLayerRiverMix};
	}
}
