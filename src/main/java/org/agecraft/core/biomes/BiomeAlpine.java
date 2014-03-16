package org.agecraft.core.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeAlpine extends AgeBiome {

	public BiomeAlpine(int id) {
		super(id);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;
		setEnableSnow();
		setColor(0xA0A0A0);
		setTemperatureRainfall(0.0F, 0.5F);
		setHeight(BiomeGenBase.height_MidHills);
	}
}
