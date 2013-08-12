package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.Age;
import elcon.mods.agecraft.IACPacketHandler;
import elcon.mods.agecraft.prehistory.blocks.BlockCampfire;
import elcon.mods.agecraft.prehistory.blocks.BlockRock;
import elcon.mods.agecraft.prehistory.items.ItemFakeStone;
import elcon.mods.agecraft.prehistory.items.ItemRock;
import elcon.mods.agecraft.prehistory.items.ItemRockTanningTool;
import elcon.mods.agecraft.prehistory.items.ItemRockTool;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;
import elcon.mods.agecraft.prehistory.tileentities.renderers.TileEntityRendererCampfire;

public class PrehistoryAge extends Age {
	
	public PrehistoryBlockRenderingHandler blockRenderingHandler;
	
	public PrehistoryPacketHandler packetHandler;
	
	public static Block campfireOff;
	public static Block campfireOn;
	public static Block rockBlock;
	
	public static Item fakeStone;
	public static Item rock;
	public static Item rockTool;
	public static Item rockTanningTool;
	
	public PrehistoryAge(int id) {
		super(id, "prehistory", ACCreativeTabs.prehistoryAge);
		packetHandler = new PrehistoryPacketHandler();
	}
	
	@Override
	public void preInit() {
		//init blocks
		campfireOff = new BlockCampfire(3000, false).setCreativeTab(tab).setUnlocalizedName("campfireOff");
		campfireOn = new BlockCampfire(3001, true).setUnlocalizedName("campfireOn");
		rockBlock = new BlockRock(3002).setCreativeTab(tab).setUnlocalizedName("rock");
		
		//register blocks
		GameRegistry.registerBlock(campfireOff, "AC_prehistory_campfireOff");
		GameRegistry.registerBlock(campfireOn, "AC_prehistory_campfireOn");
		GameRegistry.registerBlock(rockBlock, "AC_prehistory_rock");
		
		//init items
		fakeStone = new ItemFakeStone(11000).setUnlocalizedName("fakeStone");
		rock = new ItemRock(3002 - 256).setCreativeTab(tab).setUnlocalizedName("rock");
		rockTool = new ItemRockTool(13000).setCreativeTab(tab).setUnlocalizedName("rockTool");
		rockTanningTool = new ItemRockTanningTool(13001).setCreativeTab(tab).setUnlocalizedName("rockTanningTool");
		
		//add block names
		LanguageRegistry.addName(campfireOff, "Campfire");
		LanguageRegistry.addName(campfireOn, "Campfire");
		LanguageRegistry.addName(rockBlock, "Rock");
		
		//add item names
		LanguageRegistry.addName(fakeStone, "Rock");
		LanguageRegistry.addName(rock, "Rock");
		LanguageRegistry.addName(rockTool, "Rock Tool");
		LanguageRegistry.addName(rockTanningTool, "Rock Tanning Tool");
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCampfire.class, "Campfire");
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void postInit() {
		//add recipes
		CampfireRecipes.addRecipes();
		SharpenerRecipes.addRecipes();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void clientProxy() {
		blockRenderingHandler = new PrehistoryBlockRenderingHandler();
		
		//register block rendering handler
		RenderingRegistry.registerBlockHandler(200, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(201, blockRenderingHandler);
		
		//register tile entity renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, new TileEntityRendererCampfire());
	}
	
	public IACPacketHandler getPacketHandler() {
		return packetHandler;
	}
}
