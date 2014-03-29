package org.agecraft.core.biomes;

import net.minecraft.init.Blocks;

public class BiomeDesertHills extends AgeBiome {

	public BiomeDesertHills(int id) {
		super(id);
		topBlock = Blocks.sand;
		fillerBlock = Blocks.sand;
		setColor(0xD25F12);
		setDisableRain();
		setTemperatureRainfall(2.0F, 0.0F);
		setHeight(height_LowHills);
	}
}
