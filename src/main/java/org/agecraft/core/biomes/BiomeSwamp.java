package org.agecraft.core.biomes;

public class BiomeSwamp extends AgeBiome {

	public BiomeSwamp(int id) {
		super(id);
		setColor(0x07F9B2);
		func_76733_a(0x8BAF48);
		setTemperatureRainfall(0.8F, 0.9F);
		setHeight(height_PartiallySubmerged);
	}
}
