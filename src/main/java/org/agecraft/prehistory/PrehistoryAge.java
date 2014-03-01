package org.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.oredict.OreDictionary;

import org.agecraft.Age;
import org.agecraft.AgeClient;
import org.agecraft.core.Trees;
import org.agecraft.prehistory.blocks.BlockBarrel;
import org.agecraft.prehistory.blocks.BlockBed;
import org.agecraft.prehistory.blocks.BlockBox;
import org.agecraft.prehistory.blocks.BlockCampfire;
import org.agecraft.prehistory.blocks.BlockPot;
import org.agecraft.prehistory.blocks.BlockRock;
import org.agecraft.prehistory.items.ItemFakeStone;
import org.agecraft.prehistory.items.ItemPrehistoryBarrel;
import org.agecraft.prehistory.items.ItemPrehistoryBed;
import org.agecraft.prehistory.items.ItemPrehistoryBox;
import org.agecraft.prehistory.items.ItemPrehistoryCampfire;
import org.agecraft.prehistory.items.ItemPrehistoryPot;
import org.agecraft.prehistory.items.ItemRock;
import org.agecraft.prehistory.items.ItemRockPickaxe;
import org.agecraft.prehistory.items.ItemRockPickaxeHead;
import org.agecraft.prehistory.items.ItemRockTanningTool;
import org.agecraft.prehistory.items.ItemRockTool;
import org.agecraft.prehistory.recipes.RecipesBarrel;
import org.agecraft.prehistory.recipes.RecipesCampfire;
import org.agecraft.prehistory.recipes.RecipesCampfireLogs;
import org.agecraft.prehistory.recipes.RecipesSharpener;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBarrel;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBarrel.MessageTilePrehistoryBarrel;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBed;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBed.MessageTilePrehistoryBed;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBox;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBox.MessageTilePrehistoryBox;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryCampfire;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryCampfire.MessageTilePrehistoryCampfire;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryPot;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryPot.MessageTilePrehistoryPot;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQMessage;

public class PrehistoryAge extends Age {

	public static PrehistoryAge instance;
	
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
		instance = this;
	}

	@Override
	public void preInit() {
		// init blocks
		campfire = new BlockCampfire().setBlockName("AC_prehistory_campfire");
		rock = new BlockRock().setBlockName("AC_prehistory_rock");
		pot = new BlockPot().setBlockName("AC_prehistory_pot");
		bed = new BlockBed().setBlockName("AC_prehistory_bed");
		barrel = new BlockBarrel().setBlockName("AC_prehistory_barrel");
		box = new BlockBox().setBlockName("AC_prehistroy_box");

		// register blocks
		GameRegistry.registerBlock(campfire, ItemPrehistoryCampfire.class, "AC_prehistory_campfire");
		GameRegistry.registerBlock(rock, ItemRock.class, "AC_prehistory_rock");
		GameRegistry.registerBlock(pot, ItemPrehistoryPot.class, "AC_prehistory_pot");
		GameRegistry.registerBlock(bed, ItemPrehistoryBed.class, "AC_prehistory_bed");
		GameRegistry.registerBlock(barrel, ItemPrehistoryBarrel.class, "AC_prehistory_barrel");
		GameRegistry.registerBlock(box, ItemPrehistoryBox.class, "AC_prehistory_box");

		// init items
		fakeStone = new ItemFakeStone().setUnlocalizedName("AC_prehistory_fakeStone");
		rockTool = new ItemRockTool().setUnlocalizedName("AC_prehistory_rockTool");
		rockSkinningTool = new ItemRockTanningTool().setUnlocalizedName("AC_prehistory_rockSkinningTool");
		rockPickaxeHead = new ItemRockPickaxeHead().setUnlocalizedName("AC_prehistory_rockPickaxeAxe");
		rockPickaxe = new ItemRockPickaxe().setUnlocalizedName("AC_prehistory_rockPickaxe");

		// register items
		GameRegistry.registerItem(fakeStone, "AC_prehistory_fakeStone");
		GameRegistry.registerItem(rockTool, "AC_prehistory_rockTool");
		GameRegistry.registerItem(rockSkinningTool, "AC_prehistory_rockSkinningTool");
		GameRegistry.registerItem(rockPickaxeHead, "AC_prehistory_rockPickaxeHea");
		GameRegistry.registerItem(rockPickaxe, "AC_prehistory_rockPickaxe");

		// register tile entities
		GameRegistry.registerTileEntity(TileEntityPrehistoryCampfire.class, "AC_TilePrehistoryCampfire");
		GameRegistry.registerTileEntity(TileEntityPrehistoryPot.class, "AC_TilePrehistoryPot");
		GameRegistry.registerTileEntity(TileEntityPrehistoryBed.class, "AC_TilePrehistoryBed");
		GameRegistry.registerTileEntity(TileEntityPrehistoryBarrel.class, "AC_TilePrehistoryBarrel");
		GameRegistry.registerTileEntity(TileEntityPrehistoryBox.class, "AC_TilePrehistoryBox");
	}

	@Override
	public void postInit() {
		// add recipes
		GameRegistry.addRecipe(new ItemStack(rockPickaxe, 1, 0), "#", "I", '#', rockPickaxeHead, 'I', new ItemStack(Trees.stick, 1, OreDictionary.WILDCARD_VALUE));
		CraftingManager.getInstance().getRecipeList().add(new RecipesCampfireLogs());
		RecipesCampfire.addRecipes();
		RecipesSharpener.addRecipes();
		RecipesBarrel.addRecipes();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AgeClient getAgeClient() {
		return AgeClient.prehistory;
	}

	@Override
	public Class<? extends EQMessage>[] getMessages() {
		return new Class[]{MessageTilePrehistoryCampfire.class, MessageTilePrehistoryPot.class, MessageTilePrehistoryBed.class, MessageTilePrehistoryBarrel.class, MessageTilePrehistoryBox.class};
	}

	@Override
	public WorldChunkManager getWorldChunkManager(World world) {
		return new PrehistoryChunkManager(world);
	}

	@Override
	public IChunkProvider getChunkProvider(World world) {
		return new PrehistoryChunkProvider(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
	}
}
