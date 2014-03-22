package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeBeach extends AgeBiome {

	public BiomeBeach(int id) {
		super(id);
		setColor(0xFADE55);
		setTemperatureRainfall(0.8F, 0.4F);
		setHeight(BiomeGenBase.height_Shores);
	}
}
