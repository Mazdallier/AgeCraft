package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.TreeRegistry.Tree;
import elcon.mods.agecraft.core.blocks.BlockLeaves;
import elcon.mods.agecraft.core.blocks.BlockLog;
import elcon.mods.agecraft.core.blocks.BlockPlanks;
import elcon.mods.agecraft.core.blocks.BlockWood;
import elcon.mods.agecraft.core.items.ItemBlockExtendedMetadata;
import elcon.mods.agecraft.core.items.ItemBlockLeaves;
import elcon.mods.agecraft.core.items.ItemLog;

public class Trees extends ACComponent {

	public static Block wood;
	public static Block log;
	public static Block planks;
	public static Block leaves;
	public static Block woodWall;
	
	@Override
	public void preInit() {
		//init blocks
		wood = new BlockWood(2510).setUnlocalizedName("wood");
		log = new BlockLog(2511).setUnlocalizedName("log");
		planks = new BlockPlanks(2512).setUnlocalizedName("planks");
		leaves = new BlockLeaves(2513).setUnlocalizedName("leaves");
		
		//register blocks
		GameRegistry.registerBlock(wood, ItemBlockExtendedMetadata.class, "AC_wood");
		GameRegistry.registerBlock(log, ItemLog.class, "AC_log");
		GameRegistry.registerBlock(planks, ItemBlockExtendedMetadata.class, "AC_planks");
		GameRegistry.registerBlock(leaves, ItemBlockLeaves.class, "AC_leaves");
		
		//register trees
		TreeRegistry.registerTree(new Tree(0, "oak", 0x48B518, true));
		TreeRegistry.registerTree(new Tree(1, "birch", 0x80A755, false));
		TreeRegistry.registerTree(new Tree(2, "spruce", 0x619961, false));
		TreeRegistry.registerTree(new Tree(3, "jungle", 0x48B518, true));
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
