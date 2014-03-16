package org.agecraft.core.biomes;

public class BiomeIcePlains extends AgeBiome {

	public BiomeIcePlains(int id) {
		super(id);
		setEnableSnow();
		setColor(0xFFFFFF);
		setTemperatureRainfall(0.0F, 0.5F);
		setHeight(height_LowPlains);
	}
}
