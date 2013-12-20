package elcon.mods.agecraft.core.multipart;

import codechicken.lib.lighting.LightMatrix;
import codechicken.lib.render.Vertex5;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.IMicroMaterialRender;
import elcon.mods.core.blocks.BlockOverlay;

public class MicroMaterialColorOverlay extends MicroMaterialOverlay {

	public int color = 0xFFFFFF;
	
	public MicroMaterialColorOverlay(BlockOverlay block, int meta) {
		super(block, meta);
		color = block.getRenderColor(meta);
	}
	
	@Override
	public int getColour(IMicroMaterialRender part) {
		return color << 8 | 0xFF;
	}
	
	@Override
	public void renderMicroFace(Vertex5[] verts, int side, Vector3 pos, LightMatrix lightMatrix, IMicroMaterialRender part) {
		renderMicroFace(verts, side, pos, lightMatrix, getColour(part), icont());
		if(hasOverlay) {
			renderMicroFace(verts, side, pos, lightMatrix, 0xFFFFFF, overlayIconTransformation);
		}
	}
}
