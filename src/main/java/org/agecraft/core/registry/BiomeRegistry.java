package org.agecraft.core.registry;

import net.minecraft.world.biome.BiomeGenBase;

import org.agecraft.core.registry.BiomeRegistry.Biome;

public class BiomeRegistry extends Registry<Biome> {

	public static class Biome {
		
		public int id;
		public String name;
		
		public BiomeGenBase biome;
		
		public float temperature;
		public float humidity;
		
		public Biome(int id, String name, BiomeGenBase biome, float temperature, float humidity) {
			this.id = id;
			this.name = name;
			
			this.biome = biome;
			
			this.temperature = (float) Math.round(temperature * 10F) / 10F;
			this.humidity = (float) Math.round(humidity * 10F) / 10F;
		}
	}
	
	public static BiomeRegistry instance = new BiomeRegistry();
	
	public BiomeRegistry() {
		super(BiomeGenBase.getBiomeGenArray().length);
	}
	
	public static Biome getBiome(BiomeGenBase biomeGenBase) {
		for(int i = 0; i < instance.getAll().length; i++) {
			if(instance.get(i) != null && instance.get(i).biome.equals(biomeGenBase)) {
				return instance.get(i);
			}
		}
		return null;
	}
	
	public static float getTemperature(BiomeGenBase biomeGenBase) {
		Biome biome = getBiome(biomeGenBase);
		if(biome != null) {
			return biome.temperature;
		}
		return 0;
	}
	
	public static float getHumidity(BiomeGenBase biomeGenBase) {
		Biome biome = getBiome(biomeGenBase);
		if(biome != null) {
			return biome.humidity;
		}
		return 0;
	}
	
	public static boolean canSurviveTemperature(BiomeGenBase biomeGenBase, float preferedTemperature, int resistance) {
		float temperature = getTemperature(biomeGenBase);
		if(preferedTemperature == temperature) {
			return true;
		} else if(resistance == 0) {
			return false;
		} else {
			if((resistance > 0 && temperature < preferedTemperature) || (resistance < 0 && temperature > preferedTemperature)) {
				return false;
			}
			float temp = Math.min(Math.max((preferedTemperature) + (resistance * 0.5F), 0.0F), 2.0F);
			return ((preferedTemperature >= 1.0F && temperature >= 1.0F) || (preferedTemperature <= 1.0F && temperature <= 1.0F)) && temperature <= temp;
		}
	}
	
	public static boolean canSurviveHumidity(BiomeGenBase biomeGenBase, float preferedHumidity, int resistance) {
		float humidity = getHumidity(biomeGenBase);
		if(preferedHumidity == humidity) {
			return true;
		} else if(resistance == 0) {
			return false;
		} else {
			if((resistance > 0 && humidity < preferedHumidity) || (resistance < 0 && humidity > preferedHumidity)) {
				return false;
			}
			float hum = Math.min(Math.max((preferedHumidity + 1.0F) + (resistance * 0.5F), 0.0F), 2.0F);
			return ((preferedHumidity >= 1.0F && humidity >= 1.0F) || (preferedHumidity <= 1.0F && humidity <= 1.0F)) && humidity <= hum;
		}
	}
	
	public static void registerBiome(Biome biome) {
		instance.set(biome.id, biome);
	}
	
	public static void registerBiomes() {
		for(int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			if(BiomeGenBase.getBiome(i) != null) {
				BiomeGenBase biome = BiomeGenBase.getBiome(i);
				registerBiome(new Biome(biome.biomeID, biome.biomeName, biome, biome.temperature, biome.rainfall));
			}
		}
	}
}
