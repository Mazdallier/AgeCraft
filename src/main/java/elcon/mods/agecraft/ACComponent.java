package elcon.mods.agecraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ACComponent {

	public ACComponent() {
		AgeCraft.instance.components.add(this);
	}

	public void preInit() {

	}

	public void init() {

	}

	public void postInit() {

	}
	
	public String firstCaps(String s) {
		return Character.toString(s.charAt(0)).toUpperCase() + s.substring(1, s.length());
	}
	
	public IACPacketHandler getPacketHandler() {
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public IACPacketHandlerClient getPacketHandlerClient() {
		return null;
	}
}
