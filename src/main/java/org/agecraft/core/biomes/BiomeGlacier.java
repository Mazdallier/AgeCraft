package org.agecraft.core.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGlacier extends AgeBiome {

	public BiomeGlacier(int id) {
		super(id);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.ice;
		setEnableSnow();
		setColor(0xA0E0E0);
		setTemperatureRainfall(0.0F, 0.5F);
		setHeight(BiomeGenBase.height_MidHills);
	}
}
