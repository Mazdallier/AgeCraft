package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AgeCraftCore extends ACComponent {

	public static AgeCraftCore instance;
	
	public AgeCraftCore() {
		super("core", true);
		instance = this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ACComponentClient getComponentClient() {
		return AgeCraftCoreClient.instance;
	}
}
