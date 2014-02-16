package org.agecraft;

import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import org.agecraft.prehistory.PrehistoryAge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;
import elcon.mods.elconqore.network.EQCodec;
import elcon.mods.elconqore.network.EQMessage;
import elcon.mods.elconqore.network.EQPacketHandler;
import elcon.mods.elconqore.network.EQPacketHandlerServer;

public abstract class Age {

	public static Age[] ages = new Age[32];
	
	public static Age prehistory = new PrehistoryAge(0);
	public static Age agriculture;
	public static Age ancientEgypt;
	public static Age ancientChina;
	public static Age romanGreek;
	public static Age medieval;
	public static Age earlyModern;
	public static Age industrial;
	public static Age modern;
	public static Age future;

	public int ageID;
	public String ageName;

	public EQPacketHandler<EQMessage> packetHandler;

	public Age(int id, String name) {
		ageID = id;
		ageName = name;

		packetHandler = new EQPacketHandler<EQMessage>("AgeCraft-" + EQUtil.firstUpperCase(ageName), new EQCodec(getMessages()));
		packetHandler.setServerHandler(new EQPacketHandlerServer());

		ages[ageID] = this;
	}

	public void preInit() {
	}

	public void init() {
	}

	public void postInit() {
	}
	
	@SideOnly(Side.CLIENT)
	public abstract AgeClient getAgeClient();
	
	public abstract Class<? extends EQMessage>[] getMessages();

	public abstract WorldChunkManager getWorldChunkManager(World world);

	public abstract IChunkProvider getChunkProvider(World world);
}
