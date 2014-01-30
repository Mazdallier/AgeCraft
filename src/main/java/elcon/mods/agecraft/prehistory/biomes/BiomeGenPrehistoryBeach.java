package elcon.mods.agecraft.prehistory.biomes;

import net.minecraft.block.Block;

public class BiomeGenPrehistoryBeach extends BiomeGenACPrehistory {

	public BiomeGenPrehistoryBeach(int i) {
		super(i);
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		topBlock = (byte)Block.sand.blockID;
        fillerBlock = (byte)Block.sand.blockID;
	}
}
