package elcon.mods.agecraft;

import net.minecraft.client.renderer.texture.IconRegister;

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

	public void clientProxy() {

	}

	public void serverProxy() {

	}

	public void registerBlockIcons(IconRegister iconRegister) {

	}

	public void registerItemIcons(IconRegister iconRegister) {

	}
	
	public String firstCaps(String s) {
		return Character.toString(s.charAt(0)).toUpperCase() + s.substring(1, s.length());
	}
	
	public IACPacketHandler getPacketHandler() {
		return null;
	}
}
