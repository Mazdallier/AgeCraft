package elcon.mods.agecraft.core.multipart;

import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.Metals;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;

public class MultiParts extends ACComponent {

	@Override
	public void init() {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Trees.wood, i * 4), "trees_wood_" + TreeRegistry.trees[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Trees.planks, i), "trees_planks_" + TreeRegistry.trees[i].name);
				MicroMaterialRegistry.registerMaterial(new MicroMaterialLeaves(Trees.leaves, i * 4), "trees_leaves_" + TreeRegistry.trees[i].name);
			}
		}
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasBlock) {
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, i * 8), "metal_block" + MetalRegistry.metals[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 1 + i * 8), "metal_bricks" + MetalRegistry.metals[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 2 + i * 8), "metal_smallBricks" + MetalRegistry.metals[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 3 + i * 8), "metal_blockCircle" + MetalRegistry.metals[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 4 + i * 8), "metal_pillar" + MetalRegistry.metals[i].name);
			}
		}
	}
}
