package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.Age;
import elcon.mods.agecraft.IACPacketHandler;
import elcon.mods.agecraft.IACPacketHandlerClient;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.prehistory.blocks.BlockBarrel;
import elcon.mods.agecraft.prehistory.blocks.BlockBed;
import elcon.mods.agecraft.prehistory.blocks.BlockCampfire;
import elcon.mods.agecraft.prehistory.blocks.BlockPot;
import elcon.mods.agecraft.prehistory.blocks.BlockRock;
import elcon.mods.agecraft.prehistory.items.ItemBarrel;
import elcon.mods.agecraft.prehistory.items.ItemBed;
import elcon.mods.agecraft.prehistory.items.ItemCampfire;
import elcon.mods.agecraft.prehistory.items.ItemFakeStone;
import elcon.mods.agecraft.prehistory.items.ItemPot;
import elcon.mods.agecraft.prehistory.items.ItemRock;
import elcon.mods.agecraft.prehistory.items.ItemRockPickaxe;
import elcon.mods.agecraft.prehistory.items.ItemRockTanningTool;
import elcon.mods.agecraft.prehistory.items.ItemRockTool;
import elcon.mods.agecraft.prehistory.recipes.RecipesBarrel;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfire;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfireLogs;
import elcon.mods.agecraft.prehistory.recipes.RecipesSharpener;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBarrel;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBed;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityPot;
import elcon.mods.core.items.ItemDummy;

public class PrehistoryAge extends Age {
		
	public PrehistoryPacketHandler packetHandler;
	public PrehistoryPacketHandlerClient packetHandlerClient;
	
	public static Block campfire;
	public static Block rock;
	public static Block pot;
	public static Block bed;
	public static Block barrel;
	
	public static Item fakeStone;
	public static Item rockTool;
	public static Item rockSkinningTool;
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
		pot = new BlockPot(3002).setUnlocalizedName("prehistory_pot");
		bed = new BlockBed(3003).setUnlocalizedName("prehistory_bed");
		barrel = new BlockBarrel(3004).setUnlocalizedName("prehistory_barrel");
		
		//register blocks
		GameRegistry.registerBlock(campfire, ItemCampfire.class, "AC_prehistory_campfire");
		GameRegistry.registerBlock(rock, ItemRock.class, "AC_prehistory_rock");
		GameRegistry.registerBlock(pot, ItemPot.class, "AC_prehistory_pot");
		GameRegistry.registerBlock(bed, ItemBed.class, "AC_prehistory_bed");
		GameRegistry.registerBlock(barrel, ItemBarrel.class, "AC_prehistory_barrel");
		
		//init items
		fakeStone = new ItemFakeStone(11000).setUnlocalizedName("prehistory_fakeStone");
		rockTool = new ItemRockTool(13000).setUnlocalizedName("prehistory_rockTool");
		rockSkinningTool = new ItemRockTanningTool(13001).setUnlocalizedName("prehistory_rockSkinningTool");
		rockPickaxeHead = new ItemDummy(13002, "item.rockPickaxeHead.name", "agecraft:ages/prehistory/rockPickaxeHead").setMaxStackSize(1).setCreativeTab(ACCreativeTabs.prehistoryAge).setUnlocalizedName("prehistory_rockPickaxeAxe");
		rockPickaxe = new ItemRockPickaxe(13003).setUnlocalizedName("prehistory_rockPickaxe");
		
		//register items
		GameRegistry.registerItem(fakeStone, "AC_prehistory_fakeStone");
		GameRegistry.registerItem(rockTool, "AC_prehistory_rockTool");
		GameRegistry.registerItem(rockSkinningTool, "AC_prehistory_rockSkinningTool");
		GameRegistry.registerItem(rockPickaxeHead, "AC_prehistory_rockPickaxeHea");
		GameRegistry.registerItem(rockPickaxe, "AC_prehistory_rockPickaxe");
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCampfire.class, "TileCampfire");
		GameRegistry.registerTileEntity(TileEntityPot.class, "TilePot");
		GameRegistry.registerTileEntity(TileEntityBed.class, "TileBed");
		GameRegistry.registerTileEntity(TileEntityBarrel.class, "TileBarrel");
	}
	
	@Override
	public void postInit() {
		//add recipes
		GameRegistry.addRecipe(new ItemStack(rockPickaxe.itemID, 1, 0), "#", "I", '#', rockPickaxeHead, 'I', new ItemStack(Trees.stick.itemID, 1, OreDictionary.WILDCARD_VALUE));
		CraftingManager.getInstance().getRecipeList().add(new RecipesCampfireLogs());
		RecipesCampfire.addRecipes();
		RecipesSharpener.addRecipes();
		RecipesBarrel.addRecipes();
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
