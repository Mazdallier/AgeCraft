package elcon.mods.agecraft.core.multipart;

import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.Metals;
import elcon.mods.agecraft.core.Stone;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.blocks.stone.BlockColoredStoneBrick;
import elcon.mods.agecraft.core.blocks.stone.BlockStoneBrick;
import elcon.mods.core.ECUtil;

public class MultiParts extends ACComponent {

	@Override
	public void init() {
		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stone, 0), "stone_stone");
		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stone, 1), "stone_stoneCracked");
		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stone, 2), "stone_stoneMossy");
		for(int i = 0; i < 8; i++) {
			MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stoneBrick, i), "stone_stoneBrick" + ECUtil.firstUpperCase(BlockStoneBrick.types[i]));
		}
		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stoneBrickPillar, 0), "stone_stoneBrickPillar");
		for(int i = 0; i < 16; i++) {
			MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.coloredStone, i), "stone_coloredStone_" + Integer.toString(i));
			MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.coloredStoneCracked, i), "stone_coloredStoneCracked_" + Integer.toString(i));
			MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.coloredStoneMossy, i), "stone_coloredStoneMossy_" + Integer.toString(i));
			for(int j = 0; j < 8; j++) {
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.coloredStoneBrick, j + i * 8), "stone_coloredStoneBrick" + ECUtil.firstUpperCase(BlockColoredStoneBrick.types[j]) + "_" + Integer.toString(i));
			}
			MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.coloredStoneBrickPillar, i), "stone_coloredStonePillar_" + Integer.toString(i));
		}
		
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Trees.wood, i * 4), "trees_wood_" + TreeRegistry.trees[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Trees.planks, i), "trees_planks_" + TreeRegistry.trees[i].name);
				MicroMaterialRegistry.registerMaterial(new MicroMaterialLeaves(Trees.leaves, i * 4), "trees_leaves_" + TreeRegistry.trees[i].name);
			}
		}
		
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasBlock) {
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, i * 8), "metal_block_" + MetalRegistry.metals[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 1 + i * 8), "metal_bricks_" + MetalRegistry.metals[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 2 + i * 8), "metal_smallBricks_" + MetalRegistry.metals[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 3 + i * 8), "metal_blockCircle_" + MetalRegistry.metals[i].name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 4 + i * 8), "metal_pillar_" + MetalRegistry.metals[i].name);
			}
		}
	}
}
