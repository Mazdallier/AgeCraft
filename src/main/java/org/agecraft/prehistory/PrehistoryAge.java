package org.agecraft.prehistory;

import org.agecraft.Age;

import elcon.mods.elconqore.network.EQMessage;

public class PrehistoryAge extends Age {

	public PrehistoryAge(int id) {
		super(id, "prehistory");
	}

	@Override
	public Class<? extends EQMessage>[] getMessages() {
		return new Class[]{};
	}
}
