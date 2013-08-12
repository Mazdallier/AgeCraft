package elcon.mods.agecraft.core;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.blocks.BlockAgeTeleporter;
import elcon.mods.agecraft.core.blocks.BlockAgeTeleporterBeam;
import elcon.mods.agecraft.core.blocks.BlockAgeTeleporterBlock;
import elcon.mods.agecraft.core.blocks.BlockAgeTeleporterChest;
import elcon.mods.agecraft.core.blocks.BlockStoneLayered;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterBeam;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterChest;
import elcon.mods.agecraft.core.tileentities.TileEntityDNA;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;
import elcon.mods.agecraft.core.tileentities.TileEntityNBT;
import elcon.mods.agecraft.core.tileentities.renderers.TileEntityAgeTeleporterBeamRenderer;
import elcon.mods.agecraft.core.tileentities.renderers.TileEntityAgeTeleporterChestRenderer;
import elcon.mods.agecraft.prehistory.world.WorldGenTeleportSphere;

public class AgeCraftCore extends ACComponent {
	
	public Metals ores;
	public Trees trees;
	
	public static Block ageTeleporter;
	public static Block ageTeleporterBlock;
	public static Block ageTeleporterBeam;
	public static Block ageTeleporterChest;
	
	public AgeCraftCore() {
		super();
		ores = new Metals();
		trees = new Trees();
	}
	
	public void preInit() {
		//init block
		ageTeleporter = new BlockAgeTeleporter(2996).setUnlocalizedName("ageTeleporter");
		ageTeleporterBlock = new BlockAgeTeleporterBlock(2997).setUnlocalizedName("ageTeleporterBlock");
		ageTeleporterBeam = new BlockAgeTeleporterBeam(2998).setUnlocalizedName("ageTeleporterBeam");
		ageTeleporterChest = new BlockAgeTeleporterChest(2999).setUnlocalizedName("ageTeleporterChest");
		
		//register blocks
		GameRegistry.registerBlock(ageTeleporter, "AC_ageTeleporter");
		GameRegistry.registerBlock(ageTeleporterBlock, "AC_ageTeleporterBlock");
		GameRegistry.registerBlock(ageTeleporterBeam, "AC_ageTeleporterBeam");
		GameRegistry.registerBlock(ageTeleporterChest, "AC_ageTeleporterChest");
		
		//add block names
		LanguageRegistry.addName(ageTeleporter, "Age Teleporter");
		LanguageRegistry.addName(ageTeleporterBlock, "Age Teleporter Block");
		LanguageRegistry.addName(ageTeleporterBeam, "Age Teleporter Beam");
		LanguageRegistry.addName(ageTeleporterChest, "Age Teleporter Chest");
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityAgeTeleporterBeam.class, "AgeTeleporterBeam");
		GameRegistry.registerTileEntity(TileEntityAgeTeleporterChest.class, "AgeTeleporterChest");
		GameRegistry.registerTileEntity(TileEntityNBT.class, "TileNBT");
		GameRegistry.registerTileEntity(TileEntityDNA.class, "TileDNA");
		GameRegistry.registerTileEntity(TileEntityMetadata.class, "TileMetadata");
	}
	
	public void init() {
		//TODO: change in-game gui and inventory
		//GuiIngameForge.renderExperiance = false;
		//GuiIngameForge.renderJumpBar = false;
	}
	
	public void postInit() {
		Block.blocksList[1] = null;
		Block.stone = new BlockStoneLayered(1).setUnlocalizedName("stone").func_111022_d("stone");
		Item.itemsList[1] = new ItemBlockWithMetadata(1 - 256, Block.stone).setUnlocalizedName("stone");
	}
	
	@SideOnly(Side.CLIENT)
	public void clientProxy() {
		//register block rendering handler
		ACBlockRenderingHandler blockRenderingHandler = new ACBlockRenderingHandler();
		RenderingRegistry.registerBlockHandler(100, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(101, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(102, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(103, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(104, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(105, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(106, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(107, blockRenderingHandler);
		
		//register item rendering handler
		//ACItemRenderingHandler itemRenderingHandler = new ACItemRenderingHandler();
				
		//register tile entity renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAgeTeleporterBeam.class, new TileEntityAgeTeleporterBeamRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAgeTeleporterChest.class, new TileEntityAgeTeleporterChestRenderer());
	}
	
	public void serverProxy() {
		
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
