package org.agecraft;

import java.io.File;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.agecraft.core.ACBlockRenderingHandler;
import org.agecraft.core.ACBlockRenderingHandlerWithIcon;
import org.agecraft.core.clothing.PlayerClothingClient;
import org.agecraft.core.entity.EntityArrow;
import org.agecraft.core.entity.EntityBolt;
import org.agecraft.core.entity.EntityFallingBlock;
import org.agecraft.core.gui.ContainerAnvil;
import org.agecraft.core.gui.ContainerWorkbench;
import org.agecraft.core.gui.GuiAnvil;
import org.agecraft.core.gui.GuiAnvilRepair;
import org.agecraft.core.gui.GuiWorkbench;
import org.agecraft.core.player.ACPlayerClient;
import org.agecraft.core.player.ACPlayerRender;
import org.agecraft.core.player.ACPlayerServer;
import org.agecraft.core.render.entity.RenderArrow;
import org.agecraft.core.render.entity.RenderBolt;
import org.agecraft.core.render.entity.RenderFallingBlock;
import org.agecraft.core.tileentities.TileEntityAnvil;
import org.agecraft.core.tileentities.TileEntityWorkbench;
import org.agecraft.prehistory.gui.GuiSharpener;
import org.agecraft.prehistory.gui.InventorySharpener;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.ElConQore;
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

		// init player clothing client
		PlayerClothingClient.clothingDir = new File(ElConQore.minecraftDir, File.separator + "clothing");
		PlayerClothingClient.clothingFileDir = new File(ElConQore.minecraftDir, File.separator + "playerSkins");

		// register event handlers
		FMLCommonHandler.instance().bus().register(new ACEventHandlerClient());
		FMLCommonHandler.instance().bus().register(new ACTickHandlerClient());
		FMLCommonHandler.instance().bus().register(new ACKeyHandler());

		// register block rendering handlers
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
		RenderingRegistry.registerBlockHandler(116, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(117, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(118, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(119, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(120, blockRenderingHandler);

		// register entity renderers
		RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlock.class, new RenderFallingBlock());
		RenderingRegistry.registerEntityRenderingHandler(EntityArrow.class, new RenderArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityBolt.class, new RenderBolt());
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
		if(id == 11) {
			return new GuiWorkbench(new ContainerWorkbench(player, (TileEntityWorkbench) world.getTileEntity(x, y, z)));
		} else if(id == 12) {
			//TODO: smeltery gui
		} else if(id == 13) {
			return new GuiAnvil(new ContainerAnvil(player, (TileEntityAnvil) world.getTileEntity(x, y, z), false));
		} else if(id == 14) {
			return new GuiAnvilRepair(new ContainerAnvil(player, (TileEntityAnvil) world.getTileEntity(x, y, z), true));
		} else if(id == 30) {
			return new GuiSharpener(new InventorySharpener());
		}
		return null;
	}
}
