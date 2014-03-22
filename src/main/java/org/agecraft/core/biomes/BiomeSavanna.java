package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeSavanna extends AgeBiome {

	public BiomeSavanna(int id) {
		super(id);
		setColor(0xBDB25F);
		setTemperatureRainfall(1.2F, 0.0F);
		setDisableRain();
		setHeight(BiomeGenBase.height_LowPlains);
	}
}
