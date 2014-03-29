package org.agecraft.core.biomes;

public class BiomePlains extends AgeBiome {

	public BiomePlains(int id) {
		super(id);
		setColor(0x8DB360);
		setTemperatureRainfall(0.8F, 0.4F);
		setHeight(height_LowPlains);
	}
	
	@Override
	public void setDecoratorOptions(AgeDecorator decorator) {
		decorator.grassPerChunk = 10;
	}
}
