package org.agecraft.prehistory.tileentities.renderers;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.agecraft.prehistory.PrehistoryAgeClient;
import org.agecraft.prehistory.models.ModelPrehistoryCampfire;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryCampfire;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererPrehistoryCampfire extends TileEntitySpecialRenderer {

	public ModelPrehistoryCampfire model;

	private final EntityItem entityItem;
	private final RenderItem customRenderItem;

	public TileEntityRendererPrehistoryCampfire() {
		model = new ModelPrehistoryCampfire();
		entityItem = new EntityItem(null);
		customRenderItem = new RenderItem();
		customRenderItem.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		renderTileCampfire((TileEntityPrehistoryCampfire) tile, x, y, z, f);
	}

	private void renderTileCampfire(TileEntityPrehistoryCampfire tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, -0.5F, 0.5F);
		bindTexture(PrehistoryAgeClient.campfire[0]);
		if(tile.currentLogIndex > 0) {
			bindTexture(PrehistoryAgeClient.campfire[tile.currentLogIndex]);
		}		
		GL11.glPushMatrix();
		model.renderModel(0.0625F, Math.min(tile.logCount, 8));
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		if(tile.isBurning() && tile.spitStack != null) {
			entityItem.setEntityItemStack(tile.spitStack);
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);	
			
			if(tile.frameDirection == 1) {
				GL11.glTranslated(0.425D, 0.75D, 0.5D);
				GL11.glRotatef(-45F, 0.0F, 0.0F, 1.0F);
			} else {
				GL11.glTranslated(0.5D, 0.75D, 0.425D);
				GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
			}
			GL11.glRotatef(90 + (tile.frameDirection * 90), 0.0F, 1.0F, 0.0F);			

			RenderItem.renderInFrame = true;
			RenderManager.instance.renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;

			GL11.glPopMatrix();
		}
	}
}
