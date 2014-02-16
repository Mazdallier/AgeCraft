package org.agecraft;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.core.player.ACPlayerClient;
import org.agecraft.core.player.ACPlayerRender;
import org.agecraft.core.player.ACPlayerServer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.player.PlayerAPI;
import elcon.mods.elconqore.player.PlayerAPI.PlayerCoreType;

@SideOnly(Side.CLIENT)
public class ACClientProxy extends ACCommonProxy {
	
	@Override
	public void registerPlayerAPI() {
		PlayerAPI.register(PlayerCoreType.CLIENT, ACPlayerClient.class);
		PlayerAPI.register(PlayerCoreType.SERVER, ACPlayerServer.class);
		PlayerAPI.register(PlayerCoreType.RENDER, ACPlayerRender.class);
		AgeCraft.log.info("Registered PlayerAPI classes");
	}
	
	@Override
	public void registerRenderingInformation() {
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].getAgeClient().registerRenderingInformation();
			}
		}
		for(ACComponent component : ACComponent.components) {
			if(component.getComponentClient() != null) {
				component.getComponentClient().registerRenderingInformation();
			}
		}
	}

	public void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].getAgeClient().registerBlockIcons(iconRegister);
			}
		}
		for(ACComponent component : ACComponent.components) {
			if(component.getComponentClient() != null) {
				component.getComponentClient().registerBlockIcons(iconRegister);
			}
		}
	}
	
	public void registerItemIcons(IIconRegister iconRegister) {
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].getAgeClient().registerItemIcons(iconRegister);
			}
		}
		for(ACComponent component : ACComponent.components) {
			if(component.getComponentClient() != null) {
				component.getComponentClient().registerItemIcons(iconRegister);
			}
		}
	}
	
	@Override
	public void postInit() {
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].getAgeClient().postInit();
			}
		}
		for(ACComponent component : ACComponent.components) {
			if(component.getComponentClient() != null) {
				component.getComponentClient().postInit();
			}
		}
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
