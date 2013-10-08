package elcon.mods.agecraft.prehistory.tileentities.renderers;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import elcon.mods.agecraft.assets.resources.ResourcesPrehistory;
import elcon.mods.agecraft.prehistory.models.ModelCampfire;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

public class TileEntityRendererCampfire extends TileEntitySpecialRenderer {

	public ModelCampfire model;

	private final EntityItem dummyEntityItem;
	private final RenderItem customRenderItem;
	
	public TileEntityRendererCampfire() {
		model = new ModelCampfire();
		dummyEntityItem = new EntityItem(null);
		customRenderItem = new RenderItem();
	    customRenderItem.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		renderTileCampfire((TileEntityCampfire) tile, x, y, z, f);
	}

	private void renderTileCampfire(TileEntityCampfire tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		bindTexture(ResourcesPrehistory.campfire);
		GL11.glPushMatrix();
		model.renderModel(0.0625F, tile.getLogCount());
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		//TODO: render item
	}
}
