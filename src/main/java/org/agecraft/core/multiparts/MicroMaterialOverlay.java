package org.agecraft.core.multiparts;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

import org.agecraft.core.AgeCraftCoreClient;

import codechicken.lib.render.uv.MultiIconTransformation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MaterialRenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.IBlockOverlay;

public class MicroMaterialOverlay extends BlockMicroMaterial {

	public MultiIconTransformation overlayIconTransformation;
	public boolean hasOverlay;

	public MicroMaterialOverlay(Block block, int meta) {
		super(block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void loadIcons() {
		super.loadIcons();
		IIcon[] overlayIcons = new IIcon[6];
		IBlockOverlay blockOverlay = (IBlockOverlay) block();
		if(blockOverlay != null) {
			for(int i = 0; i < 6; i++) {
				overlayIcons[i] = blockOverlay.getBlockOverlayTexture(i, meta());
				if(overlayIcons[i] != null) {
					hasOverlay = true;
				}
			}
		} else {
			for(int i = 0; i < 6; i++) {
				overlayIcons[i] = AgeCraftCoreClient.emptyTexture;
			}
		}
		overlayIconTransformation = new MultiIconTransformation(overlayIcons);
	}

	@Override
	public void renderMicroFace(Vector3 pos, int pass, Cuboid6 bounds) {
		MaterialRenderHelper.start(pos, pass, icont()).blockColour(getColour(pass)).lighting().render();
		if(hasOverlay) {
			MaterialRenderHelper.start(pos, pass, overlayIconTransformation).blockColour(0xFFFFFF).lighting().render();
		}
	}
}
