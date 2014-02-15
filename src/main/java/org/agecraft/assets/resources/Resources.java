package org.agecraft.assets.resources;

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
	
	public void registerBlockIconsCall(IconRegister iconRegister) {
		for(Resources resource : resources) {
			resource.registerBlockIcons(iconRegister);
		}
	}

	public void registerItemIconsCall(IconRegister iconRegister) {
		for(Resources resource : resources) {
			resource.registerItemIcons(iconRegister);
		}
	}
	
	public void postInitCall() {
		for(Resources resource : resources) {
			resource.postInit();
		}
	}
	
	public void registerBlockIcons(IconRegister iconRegister) {
		
	}

	public void registerItemIcons(IconRegister iconRegister) {
		
	}
	
	public void postInit() {
		
	}
}
