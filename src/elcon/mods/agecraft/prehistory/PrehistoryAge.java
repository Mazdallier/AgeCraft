package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.Age;
import elcon.mods.agecraft.IACPacketHandler;
import elcon.mods.agecraft.IACPacketHandlerClient;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.items.ItemBlockName;
import elcon.mods.agecraft.core.items.ItemDummy;
import elcon.mods.agecraft.prehistory.blocks.BlockCampfire;
import elcon.mods.agecraft.prehistory.blocks.BlockRock;
import elcon.mods.agecraft.prehistory.items.ItemFakeStone;
import elcon.mods.agecraft.prehistory.items.ItemRock;
import elcon.mods.agecraft.prehistory.items.ItemRockPickaxe;
import elcon.mods.agecraft.prehistory.items.ItemRockTanningTool;
import elcon.mods.agecraft.prehistory.items.ItemRockTool;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfire;
import elcon.mods.agecraft.prehistory.recipes.RecipesSharpener;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

public class PrehistoryAge extends Age {
		
	public PrehistoryPacketHandler packetHandler;
	public PrehistoryPacketHandlerClient packetHandlerClient;
	
	public static Block campfire;
	public static Block rock;
	
	public static Item fakeStone;
	public static Item rockTool;
	public static Item rockTanningTool;
	public static Item rockPickaxeHead;
	public static Item rockPickaxe;
	
	public PrehistoryAge(int id) {
		super(id, "prehistory");
		packetHandler = new PrehistoryPacketHandler();
		packetHandlerClient = new PrehistoryPacketHandlerClient();
	}
	
	@Override
	public void preInit() {
		//init blocks
		campfire = new BlockCampfire(3000).setUnlocalizedName("prehistory_campfire");
		rock = new BlockRock(3001).setUnlocalizedName("prehistory_rock");
		
		//register blocks
		GameRegistry.registerBlock(campfire, ItemBlockName.class, "AC_prehistory_campfire");
		GameRegistry.registerBlock(rock, ItemRock.class, "AC_prehistory_rock");
		
		//init items
		fakeStone = new ItemFakeStone(11000).setUnlocalizedName("prehistory_fakeStone");
		rockTool = new ItemRockTool(13000).setUnlocalizedName("prehistory_rockTool");
		rockTanningTool = new ItemRockTanningTool(13001).setUnlocalizedName("prehistory_rockTanningTool");
		rockPickaxeHead = new ItemDummy(13002, "item.rockPickaxeHead.name", "agecraft:ages/prehistory/rockPickaxeHead").setCreativeTab(ACCreativeTabs.prehistoryAge).setUnlocalizedName("prehistory_rockPickaxeAxe");
		rockPickaxe = new ItemRockPickaxe(13003).setUnlocalizedName("prehistory_rockPickaxe");
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCampfire.class, "TileCampfire");
	}
	
	@Override
	public void postInit() {
		//add recipes
		GameRegistry.addRecipe(new ItemStack(campfire.blockID, 1, 0), "##", '#', Trees.log);
		GameRegistry.addRecipe(new ItemStack(rockPickaxe.itemID, 1, 0), "#", "I", '#', rockPickaxeHead, 'I', Trees.stick);
		
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
