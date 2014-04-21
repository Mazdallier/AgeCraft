package org.agecraft.core.multiparts;

import net.minecraft.block.Block;
import codechicken.microblock.BlockMicroMaterial;

public class MicroMaterialColor extends BlockMicroMaterial {

	public MicroMaterialColor(Block block, int meta) {
		super(block, meta);
	}

	@Override
	public int getColour(int pass) {
		return block().getRenderColor(meta()) << 8 | 0xFF;
	}
}
