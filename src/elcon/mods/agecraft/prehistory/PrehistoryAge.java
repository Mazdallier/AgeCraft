package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.Age;
import elcon.mods.agecraft.IACPacketHandler;
import elcon.mods.agecraft.IACPacketHandlerClient;
import elcon.mods.agecraft.prehistory.blocks.BlockCampfire;
import elcon.mods.agecraft.prehistory.blocks.BlockRock;
import elcon.mods.agecraft.prehistory.items.ItemFakeStone;
import elcon.mods.agecraft.prehistory.items.ItemRock;
import elcon.mods.agecraft.prehistory.items.ItemRockTanningTool;
import elcon.mods.agecraft.prehistory.items.ItemRockTool;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfire;
import elcon.mods.agecraft.prehistory.recipes.RecipesSharpener;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

public class PrehistoryAge extends Age {
		
	public PrehistoryPacketHandler packetHandler;
	public PrehistoryPacketHandlerClient packetHandlerClient;
	
	public static Block campfire;
	public static Block rockBlock;
	
	public static Item fakeStone;
	public static Item rock;
	public static Item rockTool;
	public static Item rockTanningTool;
	
	public PrehistoryAge(int id) {
		super(id, "prehistory");
		packetHandler = new PrehistoryPacketHandler();
		packetHandlerClient = new PrehistoryPacketHandlerClient();
	}
	
	@Override
	public void preInit() {
		//init blocks
		campfire = new BlockCampfire(3000).setUnlocalizedName("campfire");
		rockBlock = new BlockRock(3001).setUnlocalizedName("rock");
		
		//register blocks
		GameRegistry.registerBlock(campfire, "AC_prehistory_campfire");
		GameRegistry.registerBlock(rockBlock, "AC_prehistory_rock");
		
		//init items
		fakeStone = new ItemFakeStone(11000).setUnlocalizedName("fakeStone");
		rock = new ItemRock(rockBlock.blockID - 256).setUnlocalizedName("rock");
		rockTool = new ItemRockTool(13000).setUnlocalizedName("rockTool");
		rockTanningTool = new ItemRockTanningTool(13001).setUnlocalizedName("rockTanningTool");
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCampfire.class, "Campfire");
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void postInit() {
		//add recipes
		RecipesCampfire.addRecipes();
		RecipesSharpener.addRecipes();
	}
	
	@Override
	public IACPacketHandler getPacketHandler() {
		return packetHandler;
	}
	
	@Override
	public IACPacketHandlerClient getPacketHandlerClient() {
		return packetHandlerClient;
	}
}
