package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.TreeRegistry.Tree;
import elcon.mods.agecraft.core.blocks.tree.BlockLeaves;
import elcon.mods.agecraft.core.blocks.tree.BlockLog;
import elcon.mods.agecraft.core.blocks.tree.BlockPlanks;
import elcon.mods.agecraft.core.blocks.tree.BlockWood;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodDoor;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodFence;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodFenceGate;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodLadder;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodTrapdoor;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodWall;
import elcon.mods.agecraft.core.items.ItemBlockExtendedMetadata;
import elcon.mods.agecraft.core.items.ItemBlockLeaves;
import elcon.mods.agecraft.core.items.ItemLog;
import elcon.mods.agecraft.core.items.ItemWoodDoor;
import elcon.mods.agecraft.core.items.ItemWoodStick;

public class Trees extends ACComponent {

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
	
	public static Item stick;
	
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
	}
	
	@Override
	public void init() {
		//register trees
		TreeRegistry.registerTree(new Tree(0, "oak", 0x48B518, true, 0xB4905A));
		TreeRegistry.registerTree(new Tree(1, "birch", 0x80A755, false, 0xD7C185));
		TreeRegistry.registerTree(new Tree(2, "spruce", 0x619961, false, 0x785836));
		TreeRegistry.registerTree(new Tree(3, "jungle", 0x48B518, true, 0xB1805C));
	}
}
