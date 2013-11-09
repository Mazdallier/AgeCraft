package elcon.mods.agecraft.prehistory.tileentities.renderers;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.fluids.FluidRenderer;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBarrel;

@SideOnly(Side.CLIENT)
public class TileEntityRendererBarrel extends TileEntitySpecialRenderer {

	private final EntityItem entityItem;
	private final RenderItem customRenderItem;
	
	public TileEntityRendererBarrel() {
		entityItem = new EntityItem(null);
		customRenderItem = new RenderItem();
		customRenderItem.setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		renderTileBarrel((TileEntityBarrel) tile, x, y, z);
	}

	private void renderTileBarrel(TileEntityBarrel tile, double x, double y, double z) {
		if(tile.hasStack()) {
			entityItem.setEntityItemStack(tile.stack);
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);	
			
			GL11.glTranslated(0.5D, 0.4D, 0.5D);
			GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);

			RenderItem.renderInFrame = true;
			RenderManager.instance.renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;

			GL11.glPopMatrix();
		}
		
		FluidStack liquid = tile.fluid.getFluid();
		if(liquid != null && liquid.amount > 0) {
			int[] displayList = FluidRenderer.getFluidDisplayLists(liquid, tile.worldObj, false);
			if(displayList == null) {
				return;
			}
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			bindTexture(FluidRenderer.getFluidSheet(liquid));
			FluidRenderer.setColorForFluidStack(liquid);

			GL11.glTranslatef((float) x + 0.125F, (float) y + 0.0625F, (float) z + 0.125F);
			GL11.glScalef(0.75F, 0.8124F, 0.75F);

			GL11.glCallList(displayList[(int) ((float) liquid.amount / (float) (tile.fluid.getCapacity()) * (FluidRenderer.DISPLAY_STAGES - 1))]);

			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}
}
