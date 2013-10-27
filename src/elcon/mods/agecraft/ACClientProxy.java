package elcon.mods.agecraft;

import java.io.File;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import elcon.mods.agecraft.assets.resources.Resources;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.assets.resources.ResourcesPrehistory;
import elcon.mods.agecraft.core.ACBlockRenderingHandler;
import elcon.mods.agecraft.core.ACBlockRenderingHandlerOverlay;
import elcon.mods.agecraft.core.ACBlockRenderingHandlerWithIcon;
import elcon.mods.agecraft.core.clothing.PlayerClothingClient;
import elcon.mods.agecraft.core.entity.EntityBlock;
import elcon.mods.agecraft.core.gui.ContainerWorkbench;
import elcon.mods.agecraft.core.gui.GuiWorkbench;
import elcon.mods.agecraft.core.player.ACPlayerClient;
import elcon.mods.agecraft.core.player.ACPlayerRender;
import elcon.mods.agecraft.core.player.ACPlayerServer;
import elcon.mods.agecraft.core.render.RenderEntityBlock;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterBeam;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterChest;
import elcon.mods.agecraft.core.tileentities.TileEntityWorkbench;
import elcon.mods.agecraft.core.tileentities.renderers.TileEntityAgeTeleporterBeamRenderer;
import elcon.mods.agecraft.core.tileentities.renderers.TileEntityAgeTeleporterChestRenderer;
import elcon.mods.agecraft.prehistory.PrehistoryBlockRenderingHandler;
import elcon.mods.agecraft.prehistory.PrehistoryBlockRenderingHandlerWithIcon;
import elcon.mods.agecraft.prehistory.gui.GuiSharpener;
import elcon.mods.agecraft.prehistory.gui.InventorySharpener;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityPot;
import elcon.mods.agecraft.prehistory.tileentities.renderers.TileEntityRendererCampfire;
import elcon.mods.agecraft.prehistory.tileentities.renderers.TileEntityRendererPot;
import elcon.mods.core.ElConCore;
import elcon.mods.core.player.PlayerAPI;
import elcon.mods.core.player.PlayerAPI.PlayerCoreType;

public class ACClientProxy extends ACCommonProxy {

	@Override
	public void registerResources() {
		Resources.instance = new Resources();
		new ResourcesCore();
		new ResourcesPrehistory();
	}

	@Override
	public void registerRenderInformation() {
		// register client tick handler
		AgeCraft.instance.tickHandlerClient = new ACTickHandlerClient();
		TickRegistry.registerTickHandler(AgeCraft.instance.tickHandlerClient, Side.CLIENT);

		// register event handler
		ACEventHandlerClient eventHandler = new ACEventHandlerClient();
		MinecraftForge.EVENT_BUS.register(eventHandler);

		// register key handler
		KeyBindingRegistry.registerKeyBinding(new ACKeyHandler());

		// register block handlers
		RenderingRegistry.registerBlockHandler(ACConfig.BLOCK_OVERLAY_RENDER_ID, new ACBlockRenderingHandlerOverlay());

		// init player clothing client
		PlayerClothingClient.clothingDir = new File(ElConCore.minecraftDir, File.separator + "clothing");
		PlayerClothingClient.clothingFileDir = new File(ElConCore.minecraftDir, File.separator + "playerSkins");

		// register block rendering handler
		ACBlockRenderingHandler blockRenderingHandler = new ACBlockRenderingHandler();
		ACBlockRenderingHandlerWithIcon blockRenderingHandlerWithIcon = new ACBlockRenderingHandlerWithIcon();
		RenderingRegistry.registerBlockHandler(90, blockRenderingHandlerWithIcon);
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

		// register item rendering handler
		// ACItemRenderingHandler itemRenderingHandler = new ACItemRenderingHandler();
		// MinecraftForgeClient.registerItemRenderer(itemID, itemRenderingHandler);

		// register tileentity renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAgeTeleporterBeam.class, new TileEntityAgeTeleporterBeamRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAgeTeleporterChest.class, new TileEntityAgeTeleporterChestRenderer());
		
		//register entity renderers
		RenderingRegistry.registerEntityRenderingHandler(EntityBlock.class, RenderEntityBlock.instance);
		
		registerPrehistory();
	}
	
	private void registerPrehistory() {
		PrehistoryBlockRenderingHandler blockRenderingHandler = new PrehistoryBlockRenderingHandler();
		PrehistoryBlockRenderingHandlerWithIcon blockRenderingHandlerWithIcon = new PrehistoryBlockRenderingHandlerWithIcon();
		
		//register block rendering handler
		RenderingRegistry.registerBlockHandler(200, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(201, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(202, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(203, blockRenderingHandlerWithIcon);
		
		//register tile entity renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, new TileEntityRendererCampfire());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPot.class, new TileEntityRendererPot());
	}

	@Override
	public void registerPlayerAPI() {
		PlayerAPI.register(PlayerCoreType.CLIENT, ACPlayerClient.class);
		PlayerAPI.register(PlayerCoreType.SERVER, ACPlayerServer.class);
		PlayerAPI.register(PlayerCoreType.RENDER, ACPlayerRender.class);
		ACLog.info("Registered PlayerAPI classes");
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == 10) {
			return new GuiChest(player.inventory, (TileEntityAgeTeleporterChest) world.getBlockTileEntity(x, y, z));
		} else if(id == 11) {
			return new GuiWorkbench(new ContainerWorkbench(player, player.inventory, (TileEntityWorkbench) world.getBlockTileEntity(x, y, z), world, x, y, z));
		} else if(id == 30) {
			return new GuiSharpener(new InventorySharpener());
		}
		return null;
	}
}
