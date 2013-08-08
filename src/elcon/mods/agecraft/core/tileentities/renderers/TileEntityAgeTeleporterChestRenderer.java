package elcon.mods.agecraft.core.tileentities.renderers;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterChest;

public class TileEntityAgeTeleporterChestRenderer extends TileEntitySpecialRenderer {

	private ModelChest chestModel = new ModelChest();

	public TileEntityAgeTeleporterChestRenderer() {
		
	}

	public void renderTileEntityChestAt(TileEntityAgeTeleporterChest tileEntityChest, double par2, double par4, double par6, float par8) {
		int var9 = 0;
		if(tileEntityChest.hasWorldObj()) {
			var9 = tileEntityChest.getBlockMetadata();
		}
		func_110628_a(ResourcesCore.ageTeleporterChest);
		ModelChest var14 = chestModel;
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) par2, (float) par4 + 1.0F, (float) par6 + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short var11 = 0;

		if(var9 == 2) {
			var11 = 180;
		}
		if(var9 == 3) {
			var11 = 0;
		}
		if(var9 == 4) {
			var11 = 90;
		}
		if(var9 == 5) {
			var11 = -90;
		}

		GL11.glRotatef((float) var11, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float var12 = tileEntityChest.prevLidAngle + (tileEntityChest.lidAngle - tileEntityChest.prevLidAngle) * par8;
		
		var12 = 1.0F - var12;
		var12 = 1.0F - var12 * var12 * var12;
		var14.chestLid.rotateAngleX = -(var12 * (float) Math.PI / 2.0F);
		var14.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double d0, double d1, double d2, float f) {
		renderTileEntityChestAt((TileEntityAgeTeleporterChest) tile, d0, d1, d2, f);
	}
}
