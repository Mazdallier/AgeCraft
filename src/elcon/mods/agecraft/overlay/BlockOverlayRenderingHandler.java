package elcon.mods.agecraft.overlay;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.core.blocks.BlockOverlay;

public class BlockOverlayRenderingHandler implements ISimpleBlockRenderingHandler {

	public static final double OVERLAY_SHIFT = 0.001D;
	
	public int getRenderId() {
		return 42;
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		if(modelID == 42) {
			boolean flag = renderer.renderStandardBlock(block, x, y, z);
			renderBlockOverlay(world, (BlockOverlay) block, x, y, z, renderer, 0xFFFFFF);
			return flag;
		}
		return false;
	}

	public static boolean renderBlockWithOverlay(IBlockAccess blockAcccess, Block block, int x, int y, int z, RenderBlocks renderer, int overlayMultiplier) {
		boolean flag = renderer.renderStandardBlock(block, x, y, z);
		renderBlockOverlay(blockAcccess, (BlockOverlay) block, x, y, z, renderer, overlayMultiplier);
		return flag;
	}
	
	public static boolean renderBlockOverlay(IBlockAccess blockAccess, BlockOverlay block, int x, int y, int z, RenderBlocks renderer, int multiplier) {		
		float mR = (multiplier >> 16 & 0xFF) / 255.0F;
		float mG = (multiplier >> 8 & 0xFF) / 255.0F;
		float mB = (multiplier & 0xFF) / 255.0F;

		if(EntityRenderer.anaglyphEnable) {
			mR = (mR * 30.0F + mG * 59.0F + mB * 11.0F) / 100.0F;
			mG = (mR * 30.0F + mG * 70.0F) / 100.0F;
			mB = (mR * 30.0F + mB * 70.0F) / 100.0F;
		}
		return renderBlockOverlayWithColourMultiplier(blockAccess, block, x, y, z, mR, mG, mB, renderer);
	}

	private static boolean renderBlockOverlayWithColourMultiplier(IBlockAccess blockAccess, BlockOverlay block, int x, int y, int z, float r, float g, float b, RenderBlocks renderer) {
		int mixedBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		Icon overlay = null;
		
		float adjR = 1.0F * r;
		float adjG = 1.0F * g;
		float adjB = 1.0F * b;
		
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 0);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 0)) {
			renderBottomFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 1);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 1)) {
			renderTopFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 2);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 2)) {
			renderEastFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 3);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 3)) {
			renderWestFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 4);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 4)) {
			renderNorthFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 5);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 5)) {
			renderSouthFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		return true;
	}

	

	protected static int determineMixedBrightness(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, int mixedBrightness) {
		return renderer.renderMinY > 0.0D ? mixedBrightness : block.getMixedBrightnessForBlock(world, x, y, z);
	}

	protected static void renderBottomFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y - 1, z, 0))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y - 1, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.5F * r, 0.5F * g, 0.5F * b);
		renderer.renderFaceYNeg(block, x, y - OVERLAY_SHIFT, z, textureIndex);
	}

	protected static void renderTopFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y + 1, z, 1))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y + 1, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		renderer.renderFaceYPos(block, x, y + OVERLAY_SHIFT, z, textureIndex);
	}

	protected static void renderEastFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y, z - 1, 2))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y, z - 1, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.8F * r, 0.8F * g, 0.8F * b);
		renderer.renderFaceZNeg(block, x, y, z - OVERLAY_SHIFT, textureIndex);
	}

	protected static void renderWestFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y, z + 1, 3))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y, z + 1, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.8F * r, 0.8F * g, 0.8F * b);
		renderer.renderFaceZPos(block, x, y, z + OVERLAY_SHIFT, textureIndex);
	}

	protected static void renderNorthFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x - 1, y, z, 4))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x - 1, y, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.6F * r, 0.6F * g, 0.6F * b);
		renderer.renderFaceXNeg(block, x - OVERLAY_SHIFT, y, z, textureIndex);
	}

	protected static void renderSouthFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x + 1, y, z, 5))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x + 1, y, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.6F * r, 0.6F * g, 0.6F * b);
		renderer.renderFaceXPos(block, x + OVERLAY_SHIFT, y, z, textureIndex);
	}
	
	public boolean shouldRender3DInInventory() {
		return true;
	}
	
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if(modelID == 42) {
			renderItemBlockOverlay((BlockOverlay) block, metadata, modelID, renderer);
		}
	}

	private void renderItemBlockOverlay(BlockOverlay block, int metadata, int modelID, RenderBlocks renderer) {
		Icon overlay = null;
		Tessellator tessellator = Tessellator.instance;
		if(renderer.useInventoryTint) {
			int color = block.getRenderColor(metadata);
			float r = (float) (color >> 16 & 255) / 255.0F;
			float g = (float) (color >> 8 & 255) / 255.0F;
			float b = (float) (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
		}
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);

		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		overlay = block.getBlockOverlayTexture(0, metadata);
		if(overlay != null) {
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		overlay = block.getBlockOverlayTexture(1, metadata);
		if(overlay != null) {
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		overlay = block.getBlockOverlayTexture(2, metadata);
		if(overlay != null) {
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		overlay = block.getBlockOverlayTexture(3, metadata);
		if(overlay != null) {
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		overlay = block.getBlockOverlayTexture(4, metadata);
		if(overlay != null) {
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		overlay = block.getBlockOverlayTexture(5, metadata);
		if(overlay != null) {
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
