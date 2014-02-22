package org.agecraft;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.core.ACBlockRenderingHandler;
import org.agecraft.core.ACBlockRenderingHandlerWithIcon;
import org.agecraft.core.player.ACPlayerClient;
import org.agecraft.core.player.ACPlayerRender;
import org.agecraft.core.player.ACPlayerServer;

import cpw.mods.fml.client.registry.RenderingRegistry;
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
		
		//register block rendering handlers
		ACBlockRenderingHandler blockRenderingHandler = new ACBlockRenderingHandler();
		ACBlockRenderingHandlerWithIcon blockRenderingHandlerWithIcon = new ACBlockRenderingHandlerWithIcon();
		RenderingRegistry.registerBlockHandler(90, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(91, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(99, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(100, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(101, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(102, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(103, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(104, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(105, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(106, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(107, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(108, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(109, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(110, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(111, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(115, blockRenderingHandler);
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
