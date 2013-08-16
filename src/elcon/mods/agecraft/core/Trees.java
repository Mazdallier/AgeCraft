package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.TreeRegistry.Tree;
import elcon.mods.agecraft.core.blocks.BlockWood;
import elcon.mods.agecraft.core.items.ItemBlockExtendedMetadata;

public class Trees extends ACComponent {

	public static Block wood;
	
	@Override
	public void preInit() {
		//init blocks
		wood = new BlockWood(2510).setUnlocalizedName("wood");
		
		//register blocks
		GameRegistry.registerBlock(wood, ItemBlockExtendedMetadata.class,"AC_wood");
		
		//register trees
		TreeRegistry.registerTree(new Tree(0, "oak"));
		TreeRegistry.registerTree(new Tree(1, "birch"));
		TreeRegistry.registerTree(new Tree(2, "spruce"));
		TreeRegistry.registerTree(new Tree(3, "jungle"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IconRegister iconRegister) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			Tree tree = TreeRegistry.trees[i];
			if(tree != null) {
				tree.wood = iconRegister.registerIcon("agecraft:wood/wood" + ACUtil.firstUpperCase(tree.name));
				tree.woodTop = iconRegister.registerIcon("agecraft:wood/woodTop" + ACUtil.firstUpperCase(tree.name));
				tree.planks = iconRegister.registerIcon("agecraft:wood/planks" + ACUtil.firstUpperCase(tree.name));
				tree.leaves = iconRegister.registerIcon("agecraft:leaves/leaves" + ACUtil.firstUpperCase(tree.name));
				tree.leavesFast = iconRegister.registerIcon("agecraft:leaves/leavesFast" + ACUtil.firstUpperCase(tree.name));
				tree.smallSapling = iconRegister.registerIcon("agecraft:sapling/smallSapling" + ACUtil.firstUpperCase(tree.name));
				tree.sapling = iconRegister.registerIcon("agecraft:sapling//sapling" + ACUtil.firstUpperCase(tree.name));
			}
		}
	}
}
