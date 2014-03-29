package org.agecraft.core.dimension;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import org.agecraft.Age;
import org.agecraft.core.biomes.AgeBiome;

import elcon.mods.elconqore.EQUtil;
import elcon.mods.elconqore.lang.LanguageManager;

public class AgeProvider extends WorldProvider {

	public Age age;

	public AgeProvider() {
	}

	@Override
	public String getDimensionName() {
		return EQUtil.firstUpperCase(age.ageName) + "Age";
	}
	
	@Override
	public String getWelcomeMessage() {
		return String.format(LanguageManager.getLocalization("age.enter"), EQUtil.firstUpperCase(age.ageName));
	}
	
	@Override
	public String getDepartMessage() {
		return String.format(LanguageManager.getLocalization("age.leave"), EQUtil.firstUpperCase(age.ageName));
	}
	
	@Override
	protected void registerWorldChunkManager() {
		age = Age.ages[dimensionId - 10];
		worldChunkMgr = age.getWorldChunkManager(worldObj);
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return age.getChunkProvider(worldObj);
	}
	
	@Override
	public BiomeGenBase getBiomeGenForCoords(int x, int z) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoordsBody(x, z);
		if(biome instanceof AgeBiome && worldObj.getChunkProvider() instanceof AgeChunkProvider) {
			((AgeBiome) biome).currentChunkProvider = (AgeChunkProvider) worldObj.getChunkProvider();
		}
		return biome;
	}
}
