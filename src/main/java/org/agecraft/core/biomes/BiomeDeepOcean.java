package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeDeepOcean extends AgeBiome {

	public BiomeDeepOcean(int id) {
		super(id);
		setColor(0x000030);
		setHeight(BiomeGenBase.height_DeepOceans);
	}
	
	@Override
	public TempCategory getTempCategory() {
		return TempCategory.OCEAN;
	}

	@Override
	public BiomeGenBase getMutation() {
		return null;
	}
}
