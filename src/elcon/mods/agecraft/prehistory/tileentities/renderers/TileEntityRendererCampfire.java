package elcon.mods.agecraft.prehistory.tileentities.renderers;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import elcon.mods.agecraft.assets.resources.ResourcesPrehistory;
import elcon.mods.agecraft.prehistory.blocks.models.BlockModelCampfire;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

@Deprecated
public class TileEntityRendererCampfire extends TileEntitySpecialRenderer {

	public BlockModelCampfire model;

	private final EntityItem dummyEntityItem = new EntityItem(null);
	private final RenderItem customRenderItem;
	
	public TileEntityRendererCampfire() {
		model = new BlockModelCampfire();
		customRenderItem = new RenderItem();
	    customRenderItem.setRenderManager(RenderManager.instance);
	}

	public void renderAModelAt(TileEntityCampfire tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, -0.5F, 0.5F);
		bindTexture(ResourcesPrehistory.campfire);
		GL11.glPushMatrix();
		model.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		TileEntityCampfire campfire = (TileEntityCampfire) tile;
		renderAModelAt(campfire, x, y, z, f);
		
		if(campfire.spitStack != null) {
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			float light = tile.worldObj.getLightBrightness(tile.xCoord, tile.yCoord, tile.zCoord);
			doRenderItem(campfire.spitStack, campfire.cookTime % 360, campfire.spitDirection, x + 0.5D, y + 0.85D, z + 0.485D, light);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	public void doRenderItem(ItemStack stack, double rotation, byte direction, double d, double d1, double d2, float f1) {
		if(stack == null) {
			return;
		}
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		
		GL11.glRotatef((float) rotation, direction == 0 ? 1.0F : 0.0F, 0.0F, direction == 1 ? 1.0F : 0.0F);
		GL11.glRotatef(90 + (direction * 90), 0.0F, 1.0F, 0.0F);

		float ff = 0.97500002437500060937501523437538F;
		GL11.glScalef(ff, ff, ff);
		
		dummyEntityItem.setEntityItemStack(stack);
		RenderItem.renderInFrame = true;
		customRenderItem.doRenderItem(dummyEntityItem, 0, 0, 0, 0, 0);
		RenderItem.renderInFrame = false;
		
		GL11.glPopMatrix();
	}
}
