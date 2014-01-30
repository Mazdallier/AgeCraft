package elcon.mods.agecraft.prehistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.genlayers.GenLayerAC;
import elcon.mods.agecraft.prehistory.biomes.BiomeGenACPrehistory;

public class PrehistoryChunkManager extends WorldChunkManager {
	
	private GenLayer genBiomes;
	private GenLayer biomeIndexLayer;
	private BiomeCache biomeCache;

	private List biomesToSpawnIn;

	protected PrehistoryChunkManager() {
		biomeCache = new BiomeCache(this);
		biomesToSpawnIn = new ArrayList();
		biomesToSpawnIn.add(BiomeGenACPrehistory.prehistoryPlains);
	}

	public PrehistoryChunkManager(long seed, WorldType worldType) {
		this();
		GenLayer[] genlayer = GenLayerAC.createPrehistoryAge(seed, worldType);
		genlayer = getModdedBiomeGenerators(worldType, seed, genlayer);
		genBiomes = genlayer[0];
		biomeIndexLayer = genlayer[1];
	}

	public PrehistoryChunkManager(World world) {
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
	}

	public List getBiomesToSpawnIn() {
		return biomesToSpawnIn;
	}

	public BiomeGenBase getBiomeGenAt(int par1, int par2) {
		return biomeCache.getBiomeGenAt(par1, par2);
	}
	
	public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
		IntCache.resetIntCache();
		if(listToReuse == null || listToReuse.length < width * length) {
			listToReuse = new float[width * length];
		}
		int[] aint = biomeIndexLayer.getInts(x, z, width, length);
		for(int i1 = 0; i1 < width * length; ++i1) {
			float f = (float) BiomeGenBase.biomeList[aint[i1]].getIntRainfall() / 65536.0F;

			if(f > 1.0F) {
				f = 1.0F;
			}

			listToReuse[i1] = f;
		}
		return listToReuse;
	}

	@SideOnly(Side.CLIENT)
	public float getTemperatureAtHeight(float temperature, int height) {
		return temperature;
	}

	public float[] getTemperatures(float[] listToReuse, int x, int z, int width, int length) {
		IntCache.resetIntCache();
		if(listToReuse == null || listToReuse.length < width * length) {
			listToReuse = new float[width * length];
		}
		int[] aint = biomeIndexLayer.getInts(x, z, width, length);
		for(int i1 = 0; i1 < width * length; ++i1) {
			float f = (float) BiomeGenBase.biomeList[aint[i1]].getIntTemperature() / 65536.0F;

			if(f > 1.0F) {
				f = 1.0F;
			}
			listToReuse[i1] = f;
		}
		return listToReuse;
	}

	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] oldBiomesList, int x, int z, int width, int length) {
		IntCache.resetIntCache();

		if(oldBiomesList == null || oldBiomesList.length < width * length) {
			oldBiomesList = new BiomeGenBase[width * length];
		}

		int[] aint = genBiomes.getInts(x, z, width, length);

		for(int i1 = 0; i1 < width * length; ++i1) {
			oldBiomesList[i1] = BiomeGenBase.biomeList[aint[i1]];
		}

		return oldBiomesList;
	}

	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomesList, int x, int z, int width, int length) {
		return getBiomeGenAt(oldBiomesList, x, z, width, length, true);
	}

	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] oldBiomesList, int x, int z, int width, int length, boolean cacheFlag) {
		IntCache.resetIntCache();

		if(oldBiomesList == null || oldBiomesList.length < width * length) {
			oldBiomesList = new BiomeGenBase[width * length];
		}
		if(cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
			BiomeGenBase[] abiomegenbase1 = biomeCache.getCachedBiomes(x, z);
			System.arraycopy(abiomegenbase1, 0, oldBiomesList, 0, width * length);
			return oldBiomesList;
		} else {
			int[] aint = biomeIndexLayer.getInts(x, z, width, length);
			for(int i1 = 0; i1 < width * length; ++i1) {
				oldBiomesList[i1] = BiomeGenBase.biomeList[aint[i1]];
			}
			return oldBiomesList;
		}
	}

	public boolean areBiomesViable(int x, int z, int range, List biomes) {
		IntCache.resetIntCache();
		int l = x - range >> 2;
		int i1 = z - range >> 2;
		int j1 = x + range >> 2;
		int k1 = z + range >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = genBiomes.getInts(l, i1, l1, i2);

		for(int j2 = 0; j2 < l1 * i2; ++j2) {
			BiomeGenBase biomegenbase = BiomeGenBase.biomeList[aint[j2]];
			if(!biomes.contains(biomegenbase)) {
				return false;
			}
		}
		return true;
	}

	public ChunkPosition findBiomePosition(int x, int z, int range, List list, Random random) {
		IntCache.resetIntCache();
		int l = x - range >> 2;
		int i1 = z - range >> 2;
		int j1 = x + range >> 2;
		int k1 = z + range >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = genBiomes.getInts(l, i1, l1, i2);
		ChunkPosition chunkposition = null;
		int j2 = 0;

		for(int k2 = 0; k2 < l1 * i2; ++k2) {
			int l2 = l + k2 % l1 << 2;
			int i3 = i1 + k2 / l1 << 2;
			BiomeGenBase biomegenbase = BiomeGenBase.biomeList[aint[k2]];

			if(list.contains(biomegenbase) && (chunkposition == null || random.nextInt(j2 + 1) == 0)) {
				chunkposition = new ChunkPosition(l2, 0, i3);
				++j2;
			}
		}
		return chunkposition;
	}

	public void cleanupCache() {
		biomeCache.cleanupCache();
	}

	public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
		WorldTypeEvent.InitBiomeGens event = new WorldTypeEvent.InitBiomeGens(worldType, seed, original);
		MinecraftForge.TERRAIN_GEN_BUS.post(event);
		return event.newBiomeGens;
	}
}
