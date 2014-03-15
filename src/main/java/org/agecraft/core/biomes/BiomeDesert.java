package org.agecraft.core.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeDesert extends AgeBiome {

	public BiomeDesert(int id) {
		super(id);
		topBlock = Blocks.sand;
		fillerBlock = Blocks.sand;
		setColor(0xFA9418);
		setDisableRain();
		setTemperatureRainfall(2.0F, 0.0F);
		setHeight(BiomeGenBase.height_LowPlains);
	}

	@Override
	public BiomeGenBase getMutation() {
		return null;
	}
}
