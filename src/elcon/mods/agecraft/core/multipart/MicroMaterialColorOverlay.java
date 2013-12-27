package elcon.mods.agecraft.core.multipart;

import codechicken.lib.lighting.LightMatrix;
import codechicken.lib.render.Vertex5;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.IMicroMaterialRender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.blocks.BlockOverlay;

public class MicroMaterialColorOverlay extends MicroMaterialOverlay {

	public MicroMaterialColorOverlay(BlockOverlay block, int meta) {
		super(block, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColour(IMicroMaterialRender part) {
		return block().getRenderColor(meta()) << 8 | 0xFF;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderMicroFace(Vertex5[] verts, int side, Vector3 pos, LightMatrix lightMatrix, IMicroMaterialRender part) {
		renderMicroFace(verts, side, pos, lightMatrix, getColour(part), icont());
		if(hasOverlay) {
			renderMicroFace(verts, side, pos, lightMatrix, 0xFFFFFF, overlayIconTransformation);
		}
	}
}
