package org.agecraft.prehistory.tileentities.renderers;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.agecraft.prehistory.tileentities.TileEntityBox;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public class TileEntityRendererBox extends TileEntitySpecialRenderer {
	
	private final EntityItem entityItem[];
	private final RenderItem customRenderItem;
	
	private double[][] translations = new double[][]{new double[]{0.5D, 0.3D, 0.5D}, new double[]{0.5D, 0.3D, 0.5D}, new double[]{0.5D, 0.3D, 0.5D}, new double[]{0.5D, 0.3D, 0.5D}};

	public TileEntityRendererBox() {
		entityItem = new EntityItem[]{new EntityItem(null), new EntityItem(null), new EntityItem(null), new EntityItem(null)};
		customRenderItem = new RenderItem();
		customRenderItem.setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		renderTileBox((TileEntityBox) tile, x, y, z);
	}

	private void renderTileBox(TileEntityBox tile, double x, double y, double z) {
		translations = new double[][]{new double[]{0.4D, 0.15D, 0.4D}, new double[]{0.65D, 0.15D, 0.4D}, new double[]{0.4D, 0.15D, 0.65D}, new double[]{0.65D, 0.15D, 0.65D}};
		for(int i = 0; i < tile.stacks.length; i++) {
			if(tile.stacks[i] != null) {
				entityItem[i].setEntityItemStack(tile.stacks[i]);
				GL11.glPushMatrix();
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				
				GL11.glTranslated(x, y, z);	
				
				GL11.glTranslated(translations[i][0], translations[i][1], translations[i][2]);
				GL11.glRotatef(0F, 0.0F, 0.0F, 0.0F);
		
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				
				RenderItem.renderInFrame = true;
				RenderManager.instance.renderEntityWithPosYaw(entityItem[i], 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderItem.renderInFrame = false;
		
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopMatrix();
			}
		}
	}
}
