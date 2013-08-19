package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.ACUtil;
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
	
	@Override
	public void preInit() {
		//init blocks
		wood = new BlockWood(2510).setUnlocalizedName("wood");
		log = new BlockLog(2511).setUnlocalizedName("log");
		planks = new BlockPlanks(2512).setUnlocalizedName("planks");
		leaves = new BlockLeaves(2513).setUnlocalizedName("leaves");
		woodWall = new BlockWoodWall(2514).setUnlocalizedName("woodWall");
		fence = new BlockWoodFence(2515).setUnlocalizedName("fence");
		fenceGate = new BlockWoodFenceGate(2516).setUnlocalizedName("fenceGate");
		door = new BlockWoodDoor(2517).setUnlocalizedName("door");
		trapdoor = new BlockWoodTrapdoor(2518).setUnlocalizedName("trapdoor");
		ladder = new BlockWoodLadder(2519).setUnlocalizedName("ladder");
		
		//register blocks
		GameRegistry.registerBlock(wood, ItemBlockExtendedMetadata.class, "AC_wood");
		GameRegistry.registerBlock(log, ItemLog.class, "AC_log");
		GameRegistry.registerBlock(planks, ItemBlockExtendedMetadata.class, "AC_planks");
		GameRegistry.registerBlock(leaves, ItemBlockLeaves.class, "AC_leaves");
		GameRegistry.registerBlock(woodWall, ItemBlockExtendedMetadata.class, "AC_woodWall");
		GameRegistry.registerBlock(fence, ItemBlockExtendedMetadata.class, "AC_fence");
		GameRegistry.registerBlock(fenceGate, ItemBlockExtendedMetadata.class, "AC_fenceGate");
		GameRegistry.registerBlock(door, ItemWoodDoor.class, "AC_door");
		GameRegistry.registerBlock(trapdoor, ItemBlockExtendedMetadata.class, "AC_trapdoor");
		GameRegistry.registerBlock(ladder, ItemBlockExtendedMetadata.class, "AC_ladder");
	}
	
	@Override
	public void init() {
		//register trees
		TreeRegistry.registerTree(new Tree(0, "oak", 0x48B518, true, 0xB4905A));
		TreeRegistry.registerTree(new Tree(1, "birch", 0x80A755, false, 0xD7C185));
		TreeRegistry.registerTree(new Tree(2, "spruce", 0x619961, false, 0x785836));
		TreeRegistry.registerTree(new Tree(3, "jungle", 0x48B518, true, 0xB1805C));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IconRegister iconRegister) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			Tree tree = TreeRegistry.trees[i];
			if(tree != null) {
				tree.wood = iconRegister.registerIcon("agecraft:wood/wood" + ACUtil.firstUpperCase(tree.name));
				tree.woodTop = iconRegister.registerIcon("agecraft:wood/woodTop" + ACUtil.firstUpperCase(tree.name));
				tree.logTop = iconRegister.registerIcon("agecraft:wood/logTop" + ACUtil.firstUpperCase(tree.name));
				tree.planks = iconRegister.registerIcon("agecraft:wood/planks" + ACUtil.firstUpperCase(tree.name));
				tree.leaves = iconRegister.registerIcon("agecraft:leaves/leaves" + ACUtil.firstUpperCase(tree.name));
				tree.leavesFast = iconRegister.registerIcon("agecraft:leaves/leavesFast" + ACUtil.firstUpperCase(tree.name));
				tree.smallSapling = iconRegister.registerIcon("agecraft:sapling/smallSapling" + ACUtil.firstUpperCase(tree.name));
				tree.sapling = iconRegister.registerIcon("agecraft:sapling/sapling" + ACUtil.firstUpperCase(tree.name));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemIcons(IconRegister iconRegister) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			Tree tree = TreeRegistry.trees[i];
			if(tree != null) {
				tree.log = iconRegister.registerIcon("agecraft:wood/log" + ACUtil.firstUpperCase(tree.name));
				tree.stick = iconRegister.registerIcon("agecraft:wood/stick" + ACUtil.firstUpperCase(tree.name));
			}
		}
	}
}
