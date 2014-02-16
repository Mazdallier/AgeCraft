package org.agecraft.prehistory;

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
	public Class<? extends EQMessage>[] getMessages() {
		return new Class[]{};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AgeClient getAgeClient() {
		return AgeClient.prehistory;
	}
}
