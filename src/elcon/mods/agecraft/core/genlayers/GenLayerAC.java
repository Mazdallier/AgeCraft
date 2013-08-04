package elcon.mods.agecraft.core.genlayers;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public class GenLayerAC {

	public static GenLayer[] createPrehistoryAge(long seed, WorldType worldType) {
		int dimension = 10;
		
		GenLayerIsland var1 = new GenLayerIsland(1L);
		GenLayerFuzzyZoom var2 = new GenLayerFuzzyZoom(2000L, var1);
		GenLayerAddIsland var3 = new GenLayerAddIsland(1L, var2);
		GenLayerZoom var4 = new GenLayerZoom(2001L, var3);
		var3 = new GenLayerAddIsland(2L, var4);
		
		GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, var3);
		var4 = new GenLayerZoom(2002L, genlayeraddsnow);
		var3 = new GenLayerAddIsland(3L, var4);
		var4 = new GenLayerZoom(2003L, var3);
		var3 = new GenLayerAddIsland(4L, var4);
		//GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, var4);
	
		byte biomeSize = 4;
		if(worldType == WorldType.LARGE_BIOMES) {
			biomeSize = 6;
		}
		biomeSize = getModdedBiomeSize(worldType, biomeSize);

		GenLayer var5 = GenLayerZoom.magnify(1000L, var4, 0);
		GenLayerRiverInit var6 = new GenLayerRiverInit(100L, var5);
		var5 = GenLayerZoom.magnify(1000L, var6, biomeSize + 2);
		GenLayerACRiver var7 = new GenLayerACRiver(1L, var5, dimension);
		GenLayerSmooth var8 = new GenLayerSmooth(1000L, var7);
		GenLayer var9 = GenLayerZoom.magnify(1000L, var4, 0); 
		GenLayerACBiome var10 = new GenLayerACBiome(200L, var9, dimension);
		var9 = GenLayerZoom.magnify(1000L, var10, 2);
		GenLayer var11 = new GenLayerACHills(1000L, var9);

		for(int j = 0; j < biomeSize; ++j) {
			var11 = new GenLayerZoom((long) (1000 + j), (GenLayer) var11);
			if(j == 0) {
				var11 = new GenLayerAddIsland(3L, (GenLayer) var11);
			}
			if(j == 1) {
				var11 = new GenLayerACShore(1000L, (GenLayer) var11);
			}
			//if(j == 1) {
			//	object = new GenLayerSwampRivers(1000L, (GenLayer) object);
			//}
		}

		GenLayerSmooth var12 = new GenLayerSmooth(1000L, var11);
		GenLayerRiverMix var13 = new GenLayerRiverMix(100L, var12, var8);
		GenLayerVoronoiZoom var14 = new GenLayerVoronoiZoom(10L, var13);
		var13.initWorldGenSeed(seed);
		var14.initWorldGenSeed(seed);
		return new GenLayer[]{var13, var14, var13};
	}

	public static GenLayer[] createAgricultureAge() {
		int dimension = 11;

		return new GenLayer[]{};
	}

	public static byte getModdedBiomeSize(WorldType worldType, byte original) {
		WorldTypeEvent.BiomeSize event = new WorldTypeEvent.BiomeSize(worldType, original);
		MinecraftForge.TERRAIN_GEN_BUS.post(event);
		return event.newSize;
	}
}
