package elcon.mods.agecraft.prehistory.biomes;

import net.minecraft.block.Block;

public class BiomeGenPrehistoryAlpine extends BiomeGenACPrehistory {

	public BiomeGenPrehistoryAlpine(int i) {
		super(i);
		topBlock = (byte) Block.snow.blockID;
		fillerBlock = (byte) Block.stone.blockID;
	}
}
