package elcon.mods.agecraft.assets.resources;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Resources {

	public static Resources instance;
	public static ArrayList<Resources> resources = new ArrayList<Resources>();
	
	public Resources() {
		resources.add(this);
	}
	
	public void registerBlockIcons(IconRegister iconRegister) {
		for(Resources resource : resources) {
			resource.registerBlockIcons(iconRegister);
		}
	}

	public void registerItemIcons(IconRegister iconRegister) {
		for(Resources resource : resources) {
			resource.registerItemIcons(iconRegister);
		}
	}
}
