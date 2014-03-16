package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeOcean extends AgeBiome {

	public BiomeOcean(int id) {
		super(id);
		setColor(0x000070);
		setHeight(BiomeGenBase.height_Oceans);
	}
	
	@Override
	public TempCategory getTempCategory() {
		return TempCategory.OCEAN;
	}
}
