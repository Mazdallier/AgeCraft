package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeRiver extends AgeBiome {

	public BiomeRiver(int id) {
		super(id);
		setColor(0x0000FF);
		setHeight(BiomeGenBase.height_ShallowWaters);
	}
}
