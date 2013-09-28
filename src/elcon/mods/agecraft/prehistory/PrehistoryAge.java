package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
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

public class PrehistoryAge extends Age {
		
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
	
	public IACPacketHandler getPacketHandler() {
		return packetHandler;
	}
}
