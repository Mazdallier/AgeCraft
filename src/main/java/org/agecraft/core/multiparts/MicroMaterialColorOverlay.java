package org.agecraft.core.multiparts;

import net.minecraft.block.Block;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.MaterialRenderHelper;

public class MicroMaterialColorOverlay extends MicroMaterialOverlay {

	public MicroMaterialColorOverlay(Block block, int meta) {
		super(block, meta);
	}
	
	@Override
	public int getColour(int pass) {
		return block().getRenderColor(meta()) << 8 | 0xFF;
	}

	@Override
	public void renderMicroFace(Vector3 pos, int pass, Cuboid6 bounds) {
		MaterialRenderHelper.start(pos, pass, icont()).blockColour(getColour(pass)).lighting().render();
		if(hasOverlay) {
			MaterialRenderHelper.start(pos, pass, overlayIconTransformation).blockColour(0xFFFFFF).lighting().render();
		}
	}
}
