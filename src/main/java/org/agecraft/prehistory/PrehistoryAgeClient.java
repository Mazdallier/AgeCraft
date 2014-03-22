package org.agecraft.prehistory;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.ResourceLocation;

import org.agecraft.Age;
import org.agecraft.AgeClient;
import org.agecraft.core.registry.TreeRegistry;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBarrel;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBox;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryCampfire;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryPot;
import org.agecraft.prehistory.tileentities.renderers.TileEntityRendererPrehistoryBarrel;
import org.agecraft.prehistory.tileentities.renderers.TileEntityRendererPrehistoryBox;
import org.agecraft.prehistory.tileentities.renderers.TileEntityRendererPrehistoryCampfire;
import org.agecraft.prehistory.tileentities.renderers.TileEntityRendererPrehistoryPot;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;

@SideOnly(Side.CLIENT)
public class PrehistoryAgeClient extends AgeClient {

	public static ResourceLocation guiSharpener = new ResourceLocation("agecraft", "textures/gui/prehistory/sharpener.png");
	
	public static ResourceLocation[] campfire = new ResourceLocation[TreeRegistry.instance.getAll().length];
	
	public PrehistoryAgeClient(Age age) {
		super(age);
	}
	
	@Override
	public void registerRenderingInformation() {
		//PrehistoryBlockRenderingHandler blockRenderingHandler = new PrehistoryBlockRenderingHandler();
		PrehistoryBlockRenderingHandlerWithIcon blockRenderingHandlerWithIcon = new PrehistoryBlockRenderingHandlerWithIcon();
		
		//register block rendering handler
		RenderingRegistry.registerBlockHandler(200, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(201, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(202, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(203, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(204, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(205, blockRenderingHandlerWithIcon);
		
		//register tile entity renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrehistoryCampfire.class, new TileEntityRendererPrehistoryCampfire());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrehistoryPot.class, new TileEntityRendererPrehistoryPot());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrehistoryBarrel.class, new TileEntityRendererPrehistoryBarrel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrehistoryBox.class, new TileEntityRendererPrehistoryBox());
	}
	
	@Override
	public void registerItemIcons(IIconRegister iconRegister) {
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			if(TreeRegistry.instance.get(i) != null) {
				campfire[i] = new ResourceLocation("agecraft", "textures/tile/prehistory/campfire" + EQUtil.firstUpperCase(TreeRegistry.instance.get(i).name) + ".png");
			}
		}
	}
}
