package org.agecraft;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQPacketHandlerClient;

@SideOnly(Side.CLIENT)
public abstract class ACComponentClient {

	public ACComponentClient(ACComponent component) {
		if(component.hasPacketHandler) {
			component.packetHandler.setClientHandler(new EQPacketHandlerClient());
		}
	}
	
	public void registerRenderingInformation() {

	}

	public void registerBlockIcons(IIconRegister iconRegister) {

	}

	public void registerItemIcons(IIconRegister iconRegister) {

	}

	public void postInit() {

	}
}
