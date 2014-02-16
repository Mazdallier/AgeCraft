package org.agecraft.assets.resources;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Resources {

	public static Resources instance;
	public static ArrayList<Resources> resources = new ArrayList<Resources>();
	
	public Resources() {
		resources.add(this);
	}
	
	public void registerBlockIconsCall(IIconRegister iconRegister) {
		for(Resources resource : resources) {
			resource.registerBlockIcons(iconRegister);
		}
	}

	public void registerItemIconsCall(IIconRegister iconRegister) {
		for(Resources resource : resources) {
			resource.registerItemIcons(iconRegister);
		}
	}
	
	public void postInitCall() {
		for(Resources resource : resources) {
			resource.postInit();
		}
	}
	
	public void registerBlockIcons(IIconRegister iconRegister) {
		
	}

	public void registerItemIcons(IIconRegister iconRegister) {
		
	}
	
	public void postInit() {
		
	}
}
