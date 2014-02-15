package org.agecraft.core.multipart;

import org.agecraft.core.TreeRegistry;

import net.minecraft.block.Block;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.IMicroMaterialRender;

public class MicroMaterialLeaves extends BlockMicroMaterial {

	public MicroMaterialLeaves(Block block, int meta) {
		super(block, meta);
	}
	
	@Override
	public int getColour(IMicroMaterialRender part) {
		if(part.world() != null) {
			if(TreeRegistry.trees[(meta() - (meta() & 3)) / 4].useBiomeColor) {
				int r = 0;
				int g = 0;
				int b = 0;
				for(int k = -1; k <= 1; ++k) {
					for(int i = -1; i <= 1; ++i) {
						int color = part.world().getBiomeGenForCoords(part.x() + i, part.z() + k).getBiomeFoliageColor();
						r += (color & 16711680) >> 16;
						g += (color & 65280) >> 8;
						b += color & 255;
					}
				}
				return ((r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255) << 8 | 0xFF;
			}
			return TreeRegistry.trees[(meta() - (meta() & 3)) / 4].leafColor << 8 | 0xFF;
		} else {
			return block().getRenderColor(meta()) << 8 | 0xFF;
		}
	}
}
