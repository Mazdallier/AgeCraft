package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeTaiga extends AgeBiome {

	public BiomeTaiga(int id) {
		super(id);
		setColor(0x0B6659);
		func_76733_a(5159473);
		setTemperatureRainfall(0.25F, 0.8F);
		setHeight(BiomeGenBase.height_MidPlains);
	}
}
