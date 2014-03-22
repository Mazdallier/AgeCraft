package org.agecraft;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;
import elcon.mods.elconqore.network.EQCodec;
import elcon.mods.elconqore.network.EQMessage;
import elcon.mods.elconqore.network.EQPacketHandler;
import elcon.mods.elconqore.network.EQPacketHandlerServer;

public abstract class ACComponent {

	public static ArrayList<ACComponent> components = new ArrayList<ACComponent>();
	
	public String name;
	public boolean hasPacketHandler;
	
	public EQPacketHandler<EQMessage> packetHandler;
	
	public ACComponent(String name, boolean hasPacketHandler) {
		this.name = name;
		this.hasPacketHandler = hasPacketHandler;
		
		if(hasPacketHandler) {
			packetHandler = new EQPacketHandler<EQMessage>("AgeCraft-" + EQUtil.firstUpperCase(name), new EQCodec(getMessages()));
			packetHandler.setServerHandler(new EQPacketHandlerServer());
		}
			
		components.add(this);
	}

	public void preInit() {

	}

	public void init() {

	}

	public void postInit() {

	}
	
	public Class<? extends EQMessage>[] getMessages() {
		return new Class[]{};
	}
	
	@SideOnly(Side.CLIENT)
	public ACComponentClient getComponentClient() {
		return null;
	}
}
