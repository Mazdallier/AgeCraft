package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.DustRegistry.Dust;
import elcon.mods.agecraft.core.TreeRegistry.Tree;
import elcon.mods.agecraft.core.blocks.tree.BlockLeaves;
import elcon.mods.agecraft.core.blocks.tree.BlockLeavesDNA;
import elcon.mods.agecraft.core.blocks.tree.BlockLog;
import elcon.mods.agecraft.core.blocks.tree.BlockLogDNA;
import elcon.mods.agecraft.core.blocks.tree.BlockPlanks;
import elcon.mods.agecraft.core.blocks.tree.BlockSaplingDNA;
import elcon.mods.agecraft.core.blocks.tree.BlockWood;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodDNA;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodDoor;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodFence;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodFenceGate;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodLadder;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodTrapdoor;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodWall;
import elcon.mods.agecraft.core.items.ItemBlockLeaves;
import elcon.mods.agecraft.core.items.ItemLog;
import elcon.mods.agecraft.core.items.ItemSaplingDNA;
import elcon.mods.agecraft.core.items.ItemWoodBucket;
import elcon.mods.agecraft.core.items.ItemWoodDoor;
import elcon.mods.agecraft.core.items.ItemWoodDust;
import elcon.mods.agecraft.core.items.ItemWoodStick;
import elcon.mods.agecraft.core.tileentities.TileEntityDNATree;
import elcon.mods.agecraft.dna.structure.Chromosome;
import elcon.mods.agecraft.dna.structure.DNAObject;
import elcon.mods.agecraft.dna.structure.Gene;
import elcon.mods.core.ElConCore;
import elcon.mods.core.items.ItemBlockExtendedMetadata;
import elcon.mods.core.items.ItemBlockName;

public class Trees extends ACComponent {

	public static final int MAX_WOOD_TYPE = 3;
	public static final int MAX_LEAVE_TYPE = 3;
	
	public static final int MAX_BIOME_TYPE = 22;
	
	public static final int MAX_TRUNK_SIZE = 2;
	public static final int MAX_LEAVE_SIZE = 10;
	public static final int MAX_HEIGHT = 32;
	
	public static Block wood;
	public static Block log;
	public static Block planks;
	public static Block leaves;
	public static Block woodWall;
	public static Block fence;
	public static Block fenceGate;
	public static Block door;
	public static Block trapdoor;
	public static Block ladder;
	
	public static Block woodDNA;
	public static Block logDNA;
	public static Block leavesDNA;
	public static Block saplingDNA;
	
	public static Item stick;
	public static Item dust;
	public static Item bucket;
	
	public static DNAObject treeDNA = new DNAObject(0, "tree", TileEntityDNATree.class, new Chromosome[] {
		new Chromosome(0, "species", new Gene[] {
			new Gene(0, "woodType", MAX_WOOD_TYPE),
			new Gene(1, "leaveType", MAX_LEAVE_TYPE),
			new Gene(2, "leaveColor", 0xFFFFFF, false, true)
		}),
		new Chromosome(1, "habitat", new Gene[] {
			new Gene(0, "biome", MAX_BIOME_TYPE),
			new Gene(1, "temperature", 4),
			new Gene(2, "humidity", 4)
		}),
		new Chromosome(2, "growth", new Gene[] {
			new Gene(0, "saplingGrowSpeed", 4),
			new Gene(1, "grrowSpeed", 4),
			new Gene(2, "breedingSpeed", 4)
		}),
		new Chromosome(3, "appearance", new Gene[] {
			new Gene(0, "trunkSize", MAX_TRUNK_SIZE),
			new Gene(1, "leaveSize", MAX_LEAVE_SIZE),
			new Gene(2, "height", MAX_HEIGHT)
		}),
		new Chromosome(4, "drops", new Gene[] {
			new Gene(0, "saplingDropRate"),
			new Gene(1, "sappiness"),
			new Gene(2, "fruit"),
			new Gene(3, "fruitDropRate")
		})
	});
	
	@Override
	public void preInit() {
		//init blocks
		wood = new BlockWood(2510).setUnlocalizedName("trees_wood");
		log = new BlockLog(2511).setUnlocalizedName("trees_log");
		planks = new BlockPlanks(2512).setUnlocalizedName("trees_planks");
		leaves = new BlockLeaves(2513).setUnlocalizedName("trees_leaves");
		woodWall = new BlockWoodWall(2514).setUnlocalizedName("trees_woodWall");
		fence = new BlockWoodFence(2515).setUnlocalizedName("trees_fence");
		fenceGate = new BlockWoodFenceGate(2516).setUnlocalizedName("trees_fenceGate");
		door = new BlockWoodDoor(2517).setUnlocalizedName("trees_door");
		trapdoor = new BlockWoodTrapdoor(2518).setUnlocalizedName("trees_trapdoor");
		ladder = new BlockWoodLadder(2519).setUnlocalizedName("trees_ladder");
		
		woodDNA = new BlockWoodDNA(2530).setUnlocalizedName("tree_woodDNA");
		logDNA = new BlockLogDNA(2531).setUnlocalizedName("tree_logDNA");
		leavesDNA = new BlockLeavesDNA(2532).setUnlocalizedName("tree_leavesDNA");
		saplingDNA = new BlockSaplingDNA(2533).setUnlocalizedName("tree_saplingDNA");
		
		//register blocks
		GameRegistry.registerBlock(wood, ItemBlockExtendedMetadata.class, "AC_trees_wood");
		GameRegistry.registerBlock(log, ItemLog.class, "AC_trees_log");
		GameRegistry.registerBlock(planks, ItemBlockExtendedMetadata.class, "AC_trees_planks");
		GameRegistry.registerBlock(leaves, ItemBlockLeaves.class, "AC_trees_leaves");
		GameRegistry.registerBlock(woodWall, ItemBlockExtendedMetadata.class, "AC_trees_woodWall");
		GameRegistry.registerBlock(fence, ItemBlockExtendedMetadata.class, "AC_trees_fence");
		GameRegistry.registerBlock(fenceGate, ItemBlockExtendedMetadata.class, "AC_trees_fenceGate");
		GameRegistry.registerBlock(door, ItemWoodDoor.class, "AC_trees_door");
		GameRegistry.registerBlock(trapdoor, ItemBlockExtendedMetadata.class, "AC_trees_trapdoor");
		GameRegistry.registerBlock(ladder, ItemBlockExtendedMetadata.class, "AC_trees_ladder");
		
		GameRegistry.registerBlock(woodDNA, ItemBlockName.class, "AC_trees_woodDNA");
		GameRegistry.registerBlock(logDNA, ItemBlockName.class, "AC_trees_logDNA");
		GameRegistry.registerBlock(leavesDNA, ItemBlockName.class, "AC_trees_leavesDNA");
		GameRegistry.registerBlock(saplingDNA, ItemSaplingDNA.class, "AC_trees_saplingDNA");
		
		//register block flammability
		Block.setBurnProperties(wood.blockID, 5, 5);
		Block.setBurnProperties(log.blockID, 5, 5);
		Block.setBurnProperties(planks.blockID, 5, 20);
		Block.setBurnProperties(leaves.blockID, 30, 60);
		Block.setBurnProperties(woodWall.blockID, 5, 5);
		Block.setBurnProperties(fence.blockID, 5, 20);
		Block.setBurnProperties(fenceGate.blockID, 5, 20);
		Block.setBurnProperties(door.blockID, 5, 20);
		Block.setBurnProperties(trapdoor.blockID, 5, 20);
		Block.setBurnProperties(ladder.blockID, 5, 20);
		
		//init items
		stick = new ItemWoodStick(12510).setUnlocalizedName("trees_stick");
		dust = new ItemWoodDust(12511).setUnlocalizedName("trees_dust");
		bucket = new ItemWoodBucket(12512).setUnlocalizedName("trees_bucket");
		
		//register items
		GameRegistry.registerItem(stick, "AC_trees_stick");
		GameRegistry.registerItem(dust, "AC_trees_dust");
		GameRegistry.registerItem(bucket, "AC_trees_bucket");
		
		//register tileentities
		GameRegistry.registerTileEntity(TileEntityDNATree.class, "TileDNATree");
	}
	
	@Override
	public void init() {
		//register trees
		TreeRegistry.registerTree(new Tree(0, "oak", 0x48B518, true, 0xB4905A));
		TreeRegistry.registerTree(new Tree(1, "birch", 0x80A755, false, 0xD7C185));
		TreeRegistry.registerTree(new Tree(2, "spruce", 0x619961, false, 0x785836));
		TreeRegistry.registerTree(new Tree(3, "jungle", 0x48B518, true, 0xB1805C));
		
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				DustRegistry.registerDust(new Dust(i, "wood" + ElConCore.firstUpperCase(TreeRegistry.trees[i].name), "trees." + TreeRegistry.trees[i].name, new ItemStack(dust.itemID, 1, i)));
			}
		}
	}
	
	@Override
	public void postInit() {
		for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			for(int i = 0; i < TreeRegistry.trees.length; i++) {
				if(TreeRegistry.trees[i] != null) {
					FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(bucket.itemID, 1, i));
				}
			}
		}
	}
}
