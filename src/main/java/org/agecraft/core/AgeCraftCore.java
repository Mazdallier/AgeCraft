package org.agecraft.core;

import java.io.File;
import java.util.Random;

import org.agecraft.ACComponent;
import org.agecraft.core.blocks.BlockAgeTeleporter;
import org.agecraft.core.blocks.BlockAgeTeleporterBeam;
import org.agecraft.core.blocks.BlockAgeTeleporterBlock;
import org.agecraft.core.blocks.BlockAgeTeleporterChest;
import org.agecraft.core.blocks.BlockClothingSelectorTest;
import org.agecraft.core.blocks.metal.BlockStoneLayered;
import org.agecraft.core.clothing.ClothingCategory;
import org.agecraft.core.clothing.ClothingRegistry;
import org.agecraft.core.clothing.ClothingUpdater;
import org.agecraft.core.clothing.ClothingRegistry.ClothingType;
import org.agecraft.core.multipart.MultiParts;
import org.agecraft.core.tech.TechTree;
import org.agecraft.core.tileentities.TileEntityAgeTeleporterBeam;
import org.agecraft.core.tileentities.TileEntityAgeTeleporterChest;
import org.agecraft.core.tileentities.TileEntityDNA;
import org.agecraft.prehistory.world.WorldGenTeleportSphere;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.core.ElConCore;
import elcon.mods.core.items.ItemBlockName;

public class AgeCraftCore extends ACComponent {

	public TechTree techTree;
	public Stone stone;
	public Metals ores;
	public Trees trees;
	public Tools tools;
	public Armor armor;
	public Crafting crafting;
	public MultiParts multiparts;

	public static Block ageTeleporter;
	public static Block ageTeleporterBlock;
	public static Block ageTeleporterBeam;
	public static Block ageTeleporterChest;

	public static Block clothingSelectorTest;

	public AgeCraftCore() {
		super();
		techTree = new TechTree();
		stone = new Stone();
		ores = new Metals();
		trees = new Trees();
		tools = new Tools();
		armor = new Armor();
		crafting = new Crafting();
		multiparts = new MultiParts();
	}

	public void preInit() {
		// init block
		ageTeleporter = new BlockAgeTeleporter(2996).setUnlocalizedName("ageTeleporter");
		ageTeleporterBlock = new BlockAgeTeleporterBlock(2997).setUnlocalizedName("ageTeleporterBlock");
		ageTeleporterBeam = new BlockAgeTeleporterBeam(2998).setUnlocalizedName("ageTeleporterBeam");
		ageTeleporterChest = new BlockAgeTeleporterChest(2999).setUnlocalizedName("ageTeleporterChest");

		clothingSelectorTest = new BlockClothingSelectorTest(2995).setUnlocalizedName("clothingSelectorTest");

		// register blocks
		GameRegistry.registerBlock(ageTeleporter, ItemBlockName.class, "AC_ageTeleporter");
		GameRegistry.registerBlock(ageTeleporterBlock, ItemBlockName.class, "AC_ageTeleporterBlock");
		GameRegistry.registerBlock(ageTeleporterBeam, ItemBlockName.class, "AC_ageTeleporterBeam");
		GameRegistry.registerBlock(ageTeleporterChest, ItemBlockName.class, "AC_ageTeleporterChest");

		GameRegistry.registerBlock(clothingSelectorTest, ItemBlockName.class, "AC_clothingSelectorTest");

		// register tile entities
		GameRegistry.registerTileEntity(TileEntityAgeTeleporterBeam.class, "TileAgeTeleporterBeam");
		GameRegistry.registerTileEntity(TileEntityAgeTeleporterChest.class, "TileAgeTeleporterChest");
		GameRegistry.registerTileEntity(TileEntityDNA.class, "TileDNA");

		// remove recipes
		CraftingManager.getInstance().getRecipeList().clear();
	}

	public void init() {
		// register clothing types
		ClothingRegistry.registerClothingType(new ClothingType("skin", 0, 0));
		ClothingRegistry.registerClothingType(new ClothingType("hair", 1, 3));
		ClothingRegistry.registerClothingType(new ClothingType("eyes", 2, 2));
		ClothingRegistry.registerClothingType(new ClothingType("mouth", 3, 1));
		ClothingRegistry.registerClothingType(new ClothingType("facialHair", 4, 4));
		ClothingRegistry.registerClothingType(new ClothingType("hat", 5, 5));
		ClothingRegistry.registerClothingType(new ClothingType("shirt", 6, 8));
		ClothingRegistry.registerClothingType(new ClothingType("pants", 7, 7));
		ClothingRegistry.registerClothingType(new ClothingType("boots", 8, 6));

		// register clothing categories
		ClothingRegistry.registerClothingCategory(new ClothingCategory("general", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing-versions.dat", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing/general/general.zip"));
		ClothingRegistry.registerClothingCategory(new ClothingCategory("special", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing-versions.dat", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing/special/special.zip"));
		ClothingRegistry.registerClothingCategory(new ClothingCategory("prehistory", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing-versions.dat", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing/prehistory/prehistory.zip"));
	}

	public void postInit() {
		Block.blocksList[1] = null;
		Block.stone = new BlockStoneLayered(1).setUnlocalizedName("stone").setTextureName("stone");
		Item.itemsList[1] = new ItemBlockWithMetadata(1 - 256, Block.stone).setUnlocalizedName("stone");

		// register biomes
		BiomeRegistry.registerBiomes();

		// sort clothing types
		ClothingRegistry.sortClothingTypes();

		// update clothing
		ClothingUpdater clothingUpdater = new ClothingUpdater(new File(ElConCore.minecraftDir, File.separator + "clothing"));
		clothingUpdater.excecute();
	}

	public void generateWorld(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(chunkX == 0 && chunkZ == 0) {
			(new WorldGenTeleportSphere()).generate(world, random, 0, 128, 0);
		}
		chunkX *= 16;
		chunkZ *= 16;
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 128; j++) {
				for(int k = 0; k < 16; k++) {
					if(world.getBlockId(chunkX + i, j, chunkZ + k) == Block.stone.blockID) {
						((BlockStoneLayered) Block.stone).updateHeight(world, chunkX + i, j, chunkZ + k, random);
					}
				}
			}
		}
	}
}
