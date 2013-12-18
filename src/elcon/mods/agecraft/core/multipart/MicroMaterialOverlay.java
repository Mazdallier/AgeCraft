package elcon.mods.agecraft.core.multipart;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import codechicken.lib.lighting.LightMatrix;
import codechicken.lib.render.MultiIconTransformation;
import codechicken.lib.render.Vertex5;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.IMicroMaterialRender;
import codechicken.microblock.handler.MicroblockProxy;
import elcon.mods.core.blocks.BlockOverlay;

public class MicroMaterialOverlay extends BlockMicroMaterial {

	public MultiIconTransformation overlayIconTransformation;

	public MicroMaterialOverlay(BlockOverlay block, int meta) {
		super(block, meta);
	}

	@Override
	public void loadIcons() {
		super.loadIcons();
		Icon[] overlayIcons = new Icon[6];
		BlockOverlay blockOverlay = (BlockOverlay) Block.blocksList[block().blockID];
		if(blockOverlay != null) {
			for(int i = 0; i < 6; i++) {
				overlayIcons[i] = MicroblockProxy.renderBlocks().getIconSafe(blockOverlay.getBlockOverlayTexture(i, meta()));
			}
		} else {
			for(int i = 0; i < 6; i++) {
				overlayIcons[i] = MicroblockProxy.renderBlocks().getIconSafe(null);
			}
		}
		overlayIconTransformation = new MultiIconTransformation(overlayIcons);
	}

	@Override
	public void renderMicroFace(Vertex5[] verts, int side, Vector3 pos, LightMatrix lightMatrix, IMicroMaterialRender part) {
		renderMicroFace(verts, side, pos, lightMatrix, getColour(part), icont());
		renderMicroFace(verts, side, pos, lightMatrix, 0xFFFFFF, overlayIconTransformation);
	}
}
