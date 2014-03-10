package org.agecraft.core.render.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.agecraft.core.ACBlockRenderingHandler;
import org.agecraft.core.blocks.crafting.BlockAnvil;
import org.agecraft.core.entity.EntityFallingBlock;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFallingBlock extends Render {

	private final RenderBlocks renderBlocks = new RenderBlocks();

	public RenderFallingBlock() {
		shadowSize = 0.5F;
	}

	public void renderEntityFallingBlock(EntityFallingBlock entity, double x, double y, double z, float f1, float f2) {
		World world = entity.worldObj;
		Block block = entity.block;
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.posY);
		int k = MathHelper.floor_double(entity.posZ);
		if(block != null && block != world.getBlock(i, j, k)) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			bindEntityTexture(entity);
			GL11.glDisable(GL11.GL_LIGHTING);
			Tessellator tessellator;
			if(block instanceof BlockAnvil) {
				renderBlocks.blockAccess = world;
				tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.setTranslation((double) ((float) (-i) - 0.5F), (double) ((float) (-j) - 0.5F), (double) ((float) (-k) - 0.5F));
				ACBlockRenderingHandler.instance.renderBlockAnvil(world, i, j, k, (BlockAnvil) block, ((BlockAnvil) block).getRenderType(), renderBlocks);
				tessellator.setTranslation(0.0D, 0.0D, 0.0D);
				tessellator.draw();
			} else if(block instanceof net.minecraft.block.BlockAnvil) {
				renderBlocks.blockAccess = world;
				tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.setTranslation((double) ((float) (-i) - 0.5F), (double) ((float) (-j) - 0.5F), (double) ((float) (-k) - 0.5F));
				renderBlocks.renderBlockAnvilMetadata((net.minecraft.block.BlockAnvil) block, i, j, k, entity.meta);
				tessellator.setTranslation(0.0D, 0.0D, 0.0D);
				tessellator.draw();
			} else if(block instanceof BlockDragonEgg) {
				renderBlocks.blockAccess = world;
				tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.setTranslation((double) ((float) (-i) - 0.5F), (double) ((float) (-j) - 0.5F), (double) ((float) (-k) - 0.5F));
				renderBlocks.renderBlockDragonEgg((BlockDragonEgg) block, i, j, k);
				tessellator.setTranslation(0.0D, 0.0D, 0.0D);
				tessellator.draw();
			} else {
				renderBlocks.setRenderBoundsFromBlock(block);
				renderBlocks.renderBlockSandFalling(block, world, i, j, k, entity.meta);
			}
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1, float f2) {
		renderEntityFallingBlock((EntityFallingBlock) entity, x, y, z, f1, f2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}
