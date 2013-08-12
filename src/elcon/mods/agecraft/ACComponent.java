package elcon.mods.agecraft;

import net.minecraft.client.renderer.texture.IconRegister;
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

	@SideOnly(Side.CLIENT)
	public void clientProxy() {

	}

	public void serverProxy() {

	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IconRegister iconRegister) {

	}

	@SideOnly(Side.CLIENT)
	public void registerItemIcons(IconRegister iconRegister) {

	}
	
	public String firstCaps(String s) {
		return Character.toString(s.charAt(0)).toUpperCase() + s.substring(1, s.length());
	}
	
	public IACPacketHandler getPacketHandler() {
		return null;
	}
}
