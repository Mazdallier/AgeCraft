package org.agecraft.core.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import org.agecraft.Age;
import org.agecraft.AgeCraft;
import org.agecraft.core.Building;
import org.agecraft.core.Stone;
import org.agecraft.core.blocks.metal.BlockStoneLayered;
import org.agecraft.core.dimension.AgeChunkProvider;
import org.agecraft.core.entity.animals.EntityChicken;
import org.agecraft.core.entity.animals.EntityCow;
import org.agecraft.core.entity.animals.EntityPig;
import org.agecraft.core.entity.animals.EntitySheep;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AgeBiome extends BiomeGenBase {

	public AgeChunkProvider chunkProvider;
	public AgeChunkProvider currentChunkProvider;
	private AgeBiome mutation;
	
	public int topMeta;
	public int fillerMeta;

	public AgeBiome(int id) {
		this(id, true);
	}

	public AgeBiome(int id, boolean register) {
		super(id, register);
		topMeta = 0;
		fillerMeta = 0;
		
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();

		spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
		spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
		spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
		spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float f) {
		if(currentChunkProvider != null && currentChunkProvider.age.ageID == Age.prehistory.ageID) {
			return 0x336666;
		}
		return super.getSkyColorByTemp(f);
	}

	@Override
	public int getWaterColorMultiplier() {
		if(currentChunkProvider != null && currentChunkProvider.age.ageID == Age.prehistory.ageID) {
			return 0x009933;
		}
		return super.getWaterColorMultiplier();
	}

	public AgeBiome getMutation() {
		return mutation;
	}

	public void setMutation(AgeBiome biome) {
		mutation = biome;
	}

	public AgeBiome setMutation() {
		mutation = new BiomeMutation(getBiomeID(), this);
		return this;
	}

	@Override
	public void decorate(World world, Random random, int chunkX, int chunkZ) {
		if(chunkProvider == null) {
			AgeCraft.log.error("AgeBiome can't decorate without AgeChunkProvider!");
			return;
		}
		for(int i = 0; i < 16; i++) {
			for(int k = 0; k < 16; k++) {
				for(int j = 0; j < 256; j++) {
					if(world.getBlock(chunkX + i, j, chunkZ + k) == Stone.stone) {
						world.setBlock(chunkX + i, j, chunkZ + k, Stone.layeredStone, 0, 0);
						((BlockStoneLayered) Stone.layeredStone).updateHeight(world, chunkX + i, j, chunkZ + k, random);
					}
				}
			}
		}
		chunkProvider.getDecorator().resetOptions();
		setDecoratorOptions(chunkProvider.getDecorator());
		chunkProvider.getDecorator().decorate(world, random, chunkX, chunkZ, this);
	}

	public void setDecoratorOptions(AgeDecorator decorator) {

	}

	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] meta, int x, int y, double noise) {
		genTerrainBiome(world, random, blocks, meta, x, y, noise);
	}

	public void genTerrainBiome(World world, Random random, Block[] blocks, byte[] meta, int x, int y, double noise) {
		Block blockTop = topBlock;
		byte b0 = (byte) (field_150604_aj & 255);
		byte metaTop = (byte) topMeta;
		Block blockFiller = fillerBlock;
		byte metaFiller = (byte) fillerMeta;
		int k = -1;
		int l = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int xx = x & 15;
		int zz = y & 15;
		int size = blocks.length / 256;
		for(int height = 255; height >= 0; --height) {
			int index = (zz * 16 + xx) * size + height;
			if(height <= 0 + random.nextInt(5)) {
				blocks[index] = Blocks.bedrock;
			} else {
				Block block = blocks[index];
				if(block != null && block.getMaterial() != Material.air) {
					if(block == Stone.stone) {
						if(k == -1) {
							if(l <= 0) {
								blockTop = null;
								b0 = 0;
								metaTop = 0;
								blockFiller = Stone.stone;
								metaFiller = 0;
							} else if(height >= 59 && height <= 64) {
								blockTop = topBlock;
								b0 = (byte) (field_150604_aj & 255);
								metaTop = (byte) topMeta;
								blockFiller = fillerBlock;
								metaFiller = (byte) fillerMeta;
							}
							if(height < 63 && (blockTop == null || blockTop.getMaterial() == Material.air)) {
								if(getFloatTemperature(x, height, y) < 0.15F) {
									blockTop = Blocks.ice;
									b0 = 0;
									metaTop = 0;
								} else {
									blockTop = Blocks.water;
									b0 = 0;
									metaTop = 0;
								}
							}
							k = l;
							if(height >= 62) {
								blocks[index] = blockTop;
								meta[index] = b0;
								meta[index] = metaTop;
							} else if(height < 56 - l) {
								blockTop = null;
								metaTop = 0;
								blockFiller = Stone.stone;
								metaFiller = 0;
								blocks[index] = Building.sand;
								meta[index] = 2;
							} else {
								blocks[index] = blockFiller;
								meta[index] = metaFiller;
							}
						} else if(k > 0) {
							k--;
							blocks[index] = blockFiller;
							if(k == 0 && blockFiller == Building.sand) {
								k = random.nextInt(4) + Math.max(0, height - 63);
								blockFiller = Blocks.sandstone;
							}
						}
					}
				} else {
					k = -1;
				}
			}
		}
	}

	public static int getBiomeID() {
		for(int i = 0; i < getBiomeGenArray().length; i++) {
			if(getBiome(i) == null) {
				return i;
			}
		}
		return -1;
	}
}
