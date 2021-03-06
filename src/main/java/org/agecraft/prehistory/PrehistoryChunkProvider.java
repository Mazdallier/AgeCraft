package org.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import org.agecraft.Age;
import org.agecraft.core.Stone;
import org.agecraft.core.biomes.AgeDecorator;
import org.agecraft.core.dimension.AgeChunkProvider;

public class PrehistoryChunkProvider extends AgeChunkProvider {

	public PrehistoryChunkProvider(World world, long seed, boolean mapFeaturesEnabled) {
		super(Age.prehistory, world, seed, mapFeaturesEnabled);
	}

	@Override
	public Block getDefaultBlock() {
		return Stone.stone;
	}
	
	@Override
	public Block getDefaultFluid() {
		return Blocks.water;
	}
	
	@Override
	public AgeDecorator createDecorator() {
		return new PrehistoryDecorator();
	}
	
	@Override
	public void provideMapGenerators(int chunkX, int chunkZ, Block[] blocks, byte[] meta) {
	}

	@Override
	public void populateMapGenerators(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
	}
}
