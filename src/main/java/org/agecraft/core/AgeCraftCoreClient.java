package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;

public class AgeCraftCoreClient extends ACComponentClient {

	public static AgeCraftCoreClient instance = new AgeCraftCoreClient(AgeCraftCore.instance);

	public AgeCraftCoreClient(ACComponent component) {
		super(component);
	}
}
