package elcon.mods.agecraft.core.multipart;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import codechicken.lib.lighting.LightMatrix;
import codechicken.lib.render.MultiIconTransformation;
import codechicken.lib.render.Vertex5;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.IMicroMaterialRender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.core.blocks.BlockOverlay;

public class MicroMaterialOverlay extends BlockMicroMaterial {

	public MultiIconTransformation overlayIconTransformation;
	public boolean hasOverlay;

	public MicroMaterialOverlay(BlockOverlay block, int meta) {
		super(block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void loadIcons() {
		super.loadIcons();
		Icon[] overlayIcons = new Icon[6];
		BlockOverlay blockOverlay = (BlockOverlay) Block.blocksList[block().blockID];
		if(blockOverlay != null) {
			for(int i = 0; i < 6; i++) {
				overlayIcons[i] = blockOverlay.getBlockOverlayTexture(i, meta());
				if(overlayIcons[i] != null) {
					hasOverlay = true;
				}
			}
		} else {
			for(int i = 0; i < 6; i++) {
				overlayIcons[i] = ResourcesCore.emptyTexture;
			}
		}
		overlayIconTransformation = new MultiIconTransformation(overlayIcons);
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
