package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeSnowBeach extends AgeBiome {

	public BiomeSnowBeach(int id) {
		super(id);
		setColor(0xFAF0C0);
		setTemperatureRainfall(0.05F, 0.3F);
		setHeight(BiomeGenBase.height_Shores);
		setEnableSnow();
	}
}
