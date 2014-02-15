package org.agecraft.prehistory.biomes;

import net.minecraft.block.Block;

public class BiomeGenPrehistorySnow extends BiomeGenACPrehistory {

	public BiomeGenPrehistorySnow(int i) {
		super(i);
		topBlock = (byte) Block.blockSnow.blockID;
		fillerBlock = (byte) Block.blockSnow.blockID;
	}

}
