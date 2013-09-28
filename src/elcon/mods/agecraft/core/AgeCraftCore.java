package elcon.mods.agecraft.core;

import java.io.File;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.blocks.BlockAgeTeleporter;
import elcon.mods.agecraft.core.blocks.BlockAgeTeleporterBeam;
import elcon.mods.agecraft.core.blocks.BlockAgeTeleporterBlock;
import elcon.mods.agecraft.core.blocks.BlockAgeTeleporterChest;
import elcon.mods.agecraft.core.blocks.metal.BlockStoneLayered;
import elcon.mods.agecraft.core.clothing.ClothingCategory;
import elcon.mods.agecraft.core.clothing.ClothingRegistry;
import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;
import elcon.mods.agecraft.core.clothing.ClothingUpdater;
import elcon.mods.agecraft.core.items.ItemBlockName;
import elcon.mods.agecraft.core.tech.TechTree;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterBeam;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterChest;
import elcon.mods.agecraft.core.tileentities.TileEntityDNA;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;
import elcon.mods.agecraft.core.tileentities.TileEntityNBT;
import elcon.mods.agecraft.prehistory.world.WorldGenTeleportSphere;
import elcon.mods.core.ElConCore;

public class AgeCraftCore extends ACComponent {
	
	public TechTree techTree;
	public Metals ores;
	public Trees trees;
	public Tools tools;
	
	public static Block ageTeleporter;
	public static Block ageTeleporterBlock;
	public static Block ageTeleporterBeam;
	public static Block ageTeleporterChest;
	
	public AgeCraftCore() {
		super();
		techTree = new TechTree();
		ores = new Metals();
		trees = new Trees();
		tools = new Tools();
	}
	
	public void preInit() {
		//init block
		ageTeleporter = new BlockAgeTeleporter(2996).setUnlocalizedName("ageTeleporter");
		ageTeleporterBlock = new BlockAgeTeleporterBlock(2997).setUnlocalizedName("ageTeleporterBlock");
		ageTeleporterBeam = new BlockAgeTeleporterBeam(2998).setUnlocalizedName("ageTeleporterBeam");
		ageTeleporterChest = new BlockAgeTeleporterChest(2999).setUnlocalizedName("ageTeleporterChest");
		
		//register blocks
		GameRegistry.registerBlock(ageTeleporter, ItemBlockName.class, "AC_ageTeleporter");
		GameRegistry.registerBlock(ageTeleporterBlock, ItemBlockName.class, "AC_ageTeleporterBlock");
		GameRegistry.registerBlock(ageTeleporterBeam, ItemBlockName.class, "AC_ageTeleporterBeam");
		GameRegistry.registerBlock(ageTeleporterChest, ItemBlockName.class, "AC_ageTeleporterChest");
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityAgeTeleporterBeam.class, "AgeTeleporterBeam");
		GameRegistry.registerTileEntity(TileEntityAgeTeleporterChest.class, "AgeTeleporterChest");
		GameRegistry.registerTileEntity(TileEntityNBT.class, "TileNBT");
		GameRegistry.registerTileEntity(TileEntityDNA.class, "TileDNA");
		GameRegistry.registerTileEntity(TileEntityMetadata.class, "TileMetadata");
	}
	
	public void init() {
		//register clothing types
		ClothingRegistry.registerClothingType(new ClothingType(0, "skin", 0));
		ClothingRegistry.registerClothingType(new ClothingType(1, "hair", 3));
		ClothingRegistry.registerClothingType(new ClothingType(2, "mouth", 1));
		ClothingRegistry.registerClothingType(new ClothingType(3, "eyes", 2));
		ClothingRegistry.registerClothingType(new ClothingType(4, "facialHair", 4));
		ClothingRegistry.registerClothingType(new ClothingType(5, "shirt", 7));
		ClothingRegistry.registerClothingType(new ClothingType(6, "pants", 6));
		ClothingRegistry.registerClothingType(new ClothingType(7, "boots", 5));
		
		//register clothing categories
		ClothingRegistry.registerClothingCategory(new ClothingCategory(0, "test", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing-versions.dat", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing/test/test.zip"));

		//load clothing
		ClothingUpdater clothingManager = new ClothingUpdater(new File(ElConCore.minecraftDir, File.separator + "clothing"));
		clothingManager.excecute();
	}
	
	public void postInit() {
		Block.blocksList[1] = null;
		Block.stone = new BlockStoneLayered(1).setUnlocalizedName("stone").setTextureName("stone");
		Item.itemsList[1] = new ItemBlockWithMetadata(1 - 256, Block.stone).setUnlocalizedName("stone");
	}
	
	@SideOnly(Side.CLIENT)
	public void clientProxy() {		
		
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
