package org.agecraft.prehistory;

import org.agecraft.ACCreativeTabs;
import org.agecraft.Age;
import org.agecraft.IACPacketHandler;
import org.agecraft.IACPacketHandlerClient;
import org.agecraft.core.Trees;
import org.agecraft.prehistory.blocks.BlockBarrel;
import org.agecraft.prehistory.blocks.BlockBed;
import org.agecraft.prehistory.blocks.BlockBox;
import org.agecraft.prehistory.blocks.BlockCampfire;
import org.agecraft.prehistory.blocks.BlockPot;
import org.agecraft.prehistory.blocks.BlockRock;
import org.agecraft.prehistory.items.ItemBarrel;
import org.agecraft.prehistory.items.ItemBed;
import org.agecraft.prehistory.items.ItemBox;
import org.agecraft.prehistory.items.ItemCampfire;
import org.agecraft.prehistory.items.ItemFakeStone;
import org.agecraft.prehistory.items.ItemPot;
import org.agecraft.prehistory.items.ItemRock;
import org.agecraft.prehistory.items.ItemRockPickaxe;
import org.agecraft.prehistory.items.ItemRockTanningTool;
import org.agecraft.prehistory.items.ItemRockTool;
import org.agecraft.prehistory.recipes.RecipesBarrel;
import org.agecraft.prehistory.recipes.RecipesCampfire;
import org.agecraft.prehistory.recipes.RecipesCampfireLogs;
import org.agecraft.prehistory.recipes.RecipesSharpener;
import org.agecraft.prehistory.tileentities.TileEntityBarrel;
import org.agecraft.prehistory.tileentities.TileEntityBed;
import org.agecraft.prehistory.tileentities.TileEntityBox;
import org.agecraft.prehistory.tileentities.TileEntityCampfire;
import org.agecraft.prehistory.tileentities.TileEntityPot;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.core.items.ItemDummy;

public class PrehistoryAge extends Age {
		
	public PrehistoryPacketHandler packetHandler;
	public PrehistoryPacketHandlerClient packetHandlerClient;
	
	public static Block campfire;
	public static Block rock;
	public static Block pot;
	public static Block bed;
	public static Block barrel;
	public static Block box;
	
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
		box = new BlockBox(3005).setUnlocalizedName("prehistroy_box");
		
		//register blocks
		GameRegistry.registerBlock(campfire, ItemCampfire.class, "AC_prehistory_campfire");
		GameRegistry.registerBlock(rock, ItemRock.class, "AC_prehistory_rock");
		GameRegistry.registerBlock(pot, ItemPot.class, "AC_prehistory_pot");
		GameRegistry.registerBlock(bed, ItemBed.class, "AC_prehistory_bed");
		GameRegistry.registerBlock(barrel, ItemBarrel.class, "AC_prehistory_barrel");
		GameRegistry.registerBlock(box, ItemBox.class, "AC_prehistory_box");
		
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
		GameRegistry.registerTileEntity(TileEntityBox.class, "TileBox");
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
