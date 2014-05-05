package org.agecraft.core.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import org.agecraft.core.AgeCraftCore;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenSpawnCage implements IWorldGenerator {

	public static final int SIZE = 16;
	public static final int HEIGHT = 4;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == 0 && chunkX == (world.getSpawnPoint().posX / 16) && chunkZ == (world.getSpawnPoint().posZ / 16)) {
			for(int i = world.getSpawnPoint().posX - SIZE; i <= world.getSpawnPoint().posX + SIZE; i++) {
				for(int k = world.getSpawnPoint().posZ - SIZE; k <= world.getSpawnPoint().posZ + SIZE; k++) {
					for(int j = world.getSpawnPoint().posY - HEIGHT; j < world.getHeight(); j++) {
						if(j <= (world.getHeight() / 2) && (i == world.getSpawnPoint().posX - SIZE || i == world.getSpawnPoint().posX + SIZE || j == world.getSpawnPoint().posY - HEIGHT || j == world.getHeight() || k == world.getSpawnPoint().posZ - SIZE || k == world.getSpawnPoint().posZ + SIZE)) {
							world.setBlock(i, j, k, AgeCraftCore.ageTeleporterBlock, 0, 0);
						} else {
							world.setBlock(i, j, k, Blocks.air, 0, 0);
						}
					}
				}
			}
			for(int i = world.getSpawnPoint().posY - HEIGHT + 1; i <= world.getSpawnPoint().posY; i++) {
				world.setBlock(world.getSpawnPoint().posX, i, world.getSpawnPoint().posZ, i == world.getSpawnPoint().posY ? AgeCraftCore.ageTeleporter : AgeCraftCore.ageTeleporterBlock, 0, 0);
			}
		}
	}

}
