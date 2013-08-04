package elcon.mods.agecraft.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.core.blocks.BlockOverlay;

public class BlockOverlayRenderingHandler implements ISimpleBlockRenderingHandler {

	protected static final double OVERLAY_SHIFT = 0.001D;

	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		if(modelID == 42) {
			System.out.println(((BlockOverlay) block).getOverlayTextureForBlock(0, 0).getIconName());
			System.out.println(block.getLocalizedName());
			renderer.renderStandardBlock(block, x, y, z);
			renderBlockOverlay(world, (BlockOverlay) block, x, y, z, renderer, 0xFFFFFF);
			return true;
		}
		return false;
	}

	private boolean renderBlockOverlay(IBlockAccess world, BlockOverlay block, int x, int y, int z, RenderBlocks renderer, int multiplier) {
		float mR = (multiplier >> 16 & 0xFF) / 255.0F;
		float mG = (multiplier >> 8 & 0xFF) / 255.0F;
		float mB = (multiplier & 0xFF) / 255.0F;

		if(EntityRenderer.anaglyphEnable) {
			mR = (mR * 30.0F + mG * 59.0F + mB * 11.0F) / 100.0F;
			mG = (mR * 30.0F + mG * 70.0F) / 100.0F;
			mB = (mR * 30.0F + mB * 70.0F) / 100.0F;
		}

		return renderBlockOverlayWithColourMultiplier(world, block, x, y, z, mR, mG, mB, renderer);
	}

	private boolean renderBlockOverlayWithColourMultiplier(IBlockAccess world, BlockOverlay block, int x, int y, int z, float r, float g, float b, RenderBlocks renderer) {
		int mixedBrightness = block.getMixedBrightnessForBlock(world, x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		Icon overlay = null;
		
		//float adjR = 0.5F * r;
		//float adjG = 0.5F * g;
		//float adjB = 0.5F * b;
		float adjR = 1.0F * r;
		float adjG = 1.0F * g;
		float adjB = 1.0F * b;
		
		overlay = block.getOverlayTextureForBlock(0, metadata);
		if(overlay != null) {
			renderBottomFace(world, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getOverlayTextureForBlock(1, metadata);
		if(overlay != null) {
			renderTopFace(world, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getOverlayTextureForBlock(2, metadata);
		if(overlay != null) {
			renderEastFace(world, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getOverlayTextureForBlock(3, metadata);
		if(overlay != null) {
			renderWestFace(world, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getOverlayTextureForBlock(4, metadata);
		if(overlay != null) {
			renderNorthFace(world, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		overlay = block.getOverlayTextureForBlock(5, metadata);
		if(overlay != null) {
			renderSouthFace(world, block, x, y, z, renderer, overlay, mixedBrightness, adjR, adjG, adjB);
		}
		return true;
	}

	public boolean shouldRender3DInInventory() {
		return true;
	}

	public int getRenderId() {
		return 42;
	}

	protected int determineMixedBrightness(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, int mixedBrightness) {
		return renderer.renderMinY > 0.0D ? mixedBrightness : block.getMixedBrightnessForBlock(world, x, y, z);
	}

	protected void renderBottomFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y - 1, z, 0))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y - 1, z, renderer, mixedBrightness));
		//tesselator.setColorOpaque_F(r, g, b);
		tesselator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
		renderer.renderFaceYNeg(block, x, y - OVERLAY_SHIFT, z, textureIndex);
	}

	protected void renderTopFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y + 1, z, 1))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y + 1, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		//tesselator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
		renderer.renderFaceYPos(block, x, y + OVERLAY_SHIFT, z, textureIndex);
	}

	protected void renderEastFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y, z - 1, 2))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y, z - 1, renderer, mixedBrightness));
		//tesselator.setColorOpaque_F(r, g, b);
		tesselator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
		renderer.renderFaceZNeg(block, x, y, z - OVERLAY_SHIFT, textureIndex);
	}

	protected void renderWestFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y, z + 1, 3))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y, z + 1, renderer, mixedBrightness));
		//tesselator.setColorOpaque_F(r, g, b);
		tesselator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
		renderer.renderFaceZPos(block, x, y, z + OVERLAY_SHIFT, textureIndex);
	}

	protected void renderNorthFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x - 1, y, z, 4))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x - 1, y, z, renderer, mixedBrightness));
		//tesselator.setColorOpaque_F(r, g, b);
		tesselator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderer.renderFaceXNeg(block, x - OVERLAY_SHIFT, y, z, textureIndex);
	}

	protected void renderSouthFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x + 1, y, z, 5))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x + 1, y, z, renderer, mixedBrightness));
		//tesselator.setColorOpaque_F(r, g, b);
		tesselator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderer.renderFaceXPos(block, x + OVERLAY_SHIFT, y, z, textureIndex);
	}
}
