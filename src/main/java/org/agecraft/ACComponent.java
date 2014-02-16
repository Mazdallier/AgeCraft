package org.agecraft;

import java.util.ArrayList;

import elcon.mods.elconqore.EQUtil;
import elcon.mods.elconqore.network.EQCodec;
import elcon.mods.elconqore.network.EQMessage;
import elcon.mods.elconqore.network.EQPacketHandler;

public abstract class ACComponent {

	public static ArrayList<ACComponent> components = new ArrayList<ACComponent>();
	
	public String name;
	
	public EQPacketHandler<EQMessage> packetHandler;
	
	public ACComponent(String name, boolean hasPacketHandler) {
		this.name = name;
		
		if(hasPacketHandler) {
			packetHandler = new EQPacketHandler<EQMessage>("AgeCraft-" + EQUtil.firstUpperCase(name), new EQCodec(getMessages()));
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
}
