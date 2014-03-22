package org.agecraft.prehistory.tileentities.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import org.agecraft.prehistory.tileentities.TileEntityPrehistoryPot;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.render.FluidRenderer;

@SideOnly(Side.CLIENT)
public class TileEntityRendererPrehistoryPot extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		renderTilePot((TileEntityPrehistoryPot) tile, x, y, z);
	}

	private void renderTilePot(TileEntityPrehistoryPot tile, double x, double y, double z) {
		FluidStack liquid = tile.fluid.getFluid();
		if(liquid != null && liquid.amount > 0) {
			int[] displayList = FluidRenderer.getFluidDisplayLists(liquid, tile.getWorldObj(), false);
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
			GL11.glScalef(0.75F, 0.5624F, 0.75F);

			GL11.glCallList(displayList[(int) ((float) liquid.amount / (float) (tile.fluid.getCapacity()) * (FluidRenderer.DISPLAY_STAGES - 1))]);

			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}
}
