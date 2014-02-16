package org.agecraft;

import net.minecraft.client.renderer.texture.IIconRegister;

import org.agecraft.prehistory.PrehistoryAgeClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQPacketHandlerClient;

@SideOnly(Side.CLIENT)
public abstract class AgeClient {
	
	public static AgeClient prehistory = new PrehistoryAgeClient(Age.prehistory);
	public static AgeClient agriculture;
	public static AgeClient ancientEgypt;
	public static AgeClient ancientChina;
	public static AgeClient romanGreek;
	public static AgeClient medieval;
	public static AgeClient earlyModern;
	public static AgeClient industrial;
	public static AgeClient modern;
	public static AgeClient future;
	
	public AgeClient(Age age) {
		age.packetHandler.setClientHandler(new EQPacketHandlerClient());
	}	
	
	public void registerRenderingInformation() {
		
	}

	public void registerBlockIcons(IIconRegister iconRegister) {
		
	}

	public void registerItemIcons(IIconRegister iconRegister) {
		
	}
	
	public void postInit() {
		
	}
}
