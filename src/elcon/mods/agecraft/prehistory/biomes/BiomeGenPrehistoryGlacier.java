package elcon.mods.agecraft.prehistory.biomes;

import net.minecraft.block.Block;

public class BiomeGenPrehistoryGlacier extends BiomeGenACPrehistory {

	public BiomeGenPrehistoryGlacier(int i) {
		super(i);
		topBlock = (byte) Block.blockSnow.blockID;
		fillerBlock = (byte) Block.ice.blockID;
	}
}
