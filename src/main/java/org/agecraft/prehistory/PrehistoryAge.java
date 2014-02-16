package org.agecraft.prehistory;

import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import org.agecraft.Age;
import org.agecraft.AgeClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQMessage;

public class PrehistoryAge extends Age {

	public PrehistoryAge(int id) {
		super(id, "prehistory");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AgeClient getAgeClient() {
		return AgeClient.prehistory;
	}
	
	@Override
	public Class<? extends EQMessage>[] getMessages() {
		return new Class[]{};
	}
	
	@Override
	public WorldChunkManager getWorldChunkManager(World world) {
		return new PrehistoryChunkManager(world);
	}
	
	@Override
	public IChunkProvider getChunkProvider(World world) {
		return new PrehistoryChunkProvider(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
	}
}
