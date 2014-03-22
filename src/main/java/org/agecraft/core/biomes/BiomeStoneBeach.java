package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeStoneBeach extends AgeBiome {

	public BiomeStoneBeach(int id) {
		super(id);
		setColor(0xA2A284);
		setTemperatureRainfall(0.2F, 0.3F);
		setHeight(BiomeGenBase.height_RockyWaters);
	}
}
