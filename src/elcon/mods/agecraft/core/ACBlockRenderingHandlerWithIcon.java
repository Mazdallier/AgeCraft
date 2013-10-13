package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalLadder;
import elcon.mods.agecraft.core.blocks.tree.BlockWoodLadder;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class ACBlockRenderingHandlerWithIcon implements ISimpleBlockRenderingHandler {

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		switch(modelID) {
		case 90:
			return renderBlockCrossedSquares(blockAccess, x, y, z, block, modelID, renderer);
		case 102:
			return renderer.renderStandardBlock(block, x, y, z);
		case 103:
			return renderBlockMetalLadder(blockAccess, x, y, z, (BlockMetalLadder) block, modelID, renderer);
		case 110:
			return renderer.renderStandardBlock(block, x, y, z);
		case 111:
			return renderBlockWoodLadder(blockAccess, x, y, z, (BlockWoodLadder) block, modelID, renderer);
		}
		return false;
	}

	private boolean renderBlockCrossedSquares(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
		float f = 1.0F;
		int l = block.colorMultiplier(blockAccess, x, y, z);
		float f1 = (float) (l >> 16 & 255) / 255.0F;
		float f2 = (float) (l >> 8 & 255) / 255.0F;
		float f3 = (float) (l & 255) / 255.0F;

		if(EntityRenderer.anaglyphEnable) {
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}

		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		double xx = (double) x;
		double yy = (double) y;
		double zz = (double) z;
		if(block == Block.tallGrass) {
			long i1 = (long) (x * 3129871) ^ (long) z * 116129781L ^ (long) y;
			i1 = i1 * i1 * 42317861L + i1 * 11L;
			xx += ((double) ((float) (i1 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
			yy += ((double) ((float) (i1 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
			zz += ((double) ((float) (i1 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
		}
		drawCrossedSquares(blockAccess, xx, yy, zz, 1.0F, block, blockAccess.getBlockMetadata(x, y, z), renderer);
		return true;
	}

	private void drawCrossedSquares(IBlockAccess blockAccess, double x, double y, double z, float size, Block block, int meta, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		Icon icon = renderer.getBlockIcon(block, blockAccess, (int) x, (int) y, (int) z, meta);
		if(renderer.hasOverrideBlockTexture()) {
			icon = renderer.overrideBlockTexture;
		}
		double minU = (double) icon.getMinU();
		double minV = (double) icon.getMinV();
		double maxU = (double) icon.getMaxU();
		double maxV = (double) icon.getMaxV();
		double scaledSize = 0.45D * (double) size;
		double minX = x + 0.5D - scaledSize;
		double maxX = x + 0.5D + scaledSize;
		double minZ = z + 0.5D - scaledSize;
		double maxZ = z + 0.5D + scaledSize;
		tessellator.addVertexWithUV(minX, y + (double) size, minZ, minU, minV);
		tessellator.addVertexWithUV(minX, y + 0.0D, minZ, minU, maxV);
		tessellator.addVertexWithUV(maxX, y + 0.0D, maxZ, maxU, maxV);
		tessellator.addVertexWithUV(maxX, y + (double) size, maxZ, maxU, minV);
		tessellator.addVertexWithUV(maxX, y + (double) size, maxZ, minU, minV);
		tessellator.addVertexWithUV(maxX, y + 0.0D, maxZ, minU, maxV);
		tessellator.addVertexWithUV(minX, y + 0.0D, minZ, maxU, maxV);
		tessellator.addVertexWithUV(minX, y + (double) size, minZ, maxU, minV);
		tessellator.addVertexWithUV(minX, y + (double) size, maxZ, minU, minV);
		tessellator.addVertexWithUV(minX, y + 0.0D, maxZ, minU, maxV);
		tessellator.addVertexWithUV(maxX, y + 0.0D, minZ, maxU, maxV);
		tessellator.addVertexWithUV(maxX, y + (double) size, minZ, maxU, minV);
		tessellator.addVertexWithUV(maxX, y + (double) size, minZ, minU, minV);
		tessellator.addVertexWithUV(maxX, y + 0.0D, minZ, minU, maxV);
		tessellator.addVertexWithUV(minX, y + 0.0D, maxZ, maxU, maxV);
		tessellator.addVertexWithUV(minX, y + (double) size, maxZ, maxU, minV);
	}

	private boolean renderBlockMetalLadder(IBlockAccess blockAccess, int x, int y, int z, BlockMetalLadder block, int modelID, RenderBlocks renderer) {
		int color = block.colorMultiplier(blockAccess, x, y, z);
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable) {
			float f3 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			float f4 = (r * 30.0F + g * 70.0F) / 100.0F;
			float f5 = (r * 30.0F + b * 70.0F) / 100.0F;
			r = f3;
			g = f4;
			b = f5;
		}
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getBlockTileEntity(x, y, z);
		int meta = tile.getTileMetadata();
		int direction = meta & 7;

		Tessellator tessellator = Tessellator.instance;
		Icon icon = renderer.getBlockIconFromSideAndMetadata(block, 0, meta);
		if(renderer.hasOverrideBlockTexture()) {
			icon = renderer.overrideBlockTexture;
		}
		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
		tessellator.setColorOpaque_F(r, g, b);
		double minU = (double) icon.getMinU();
		double minV = (double) icon.getMinV();
		double maxU = (double) icon.getMaxU();
		double maxV = (double) icon.getMaxV();
		double d4 = 0.0D;
		double d5 = 0.05000000074505806D;

		if(direction == 5) {
			tessellator.addVertexWithUV((double) x + d5, (double) (y + 1) + d4, (double) (z + 1) + d4, minU, minV);
			tessellator.addVertexWithUV((double) x + d5, (double) (y + 0) - d4, (double) (z + 1) + d4, minU, maxV);
			tessellator.addVertexWithUV((double) x + d5, (double) (y + 0) - d4, (double) (z + 0) - d4, maxU, maxV);
			tessellator.addVertexWithUV((double) x + d5, (double) (y + 1) + d4, (double) (z + 0) - d4, maxU, minV);
		}
		if(direction == 4) {
			tessellator.addVertexWithUV((double) (x + 1) - d5, (double) (y + 0) - d4, (double) (z + 1) + d4, maxU, maxV);
			tessellator.addVertexWithUV((double) (x + 1) - d5, (double) (y + 1) + d4, (double) (z + 1) + d4, maxU, minV);
			tessellator.addVertexWithUV((double) (x + 1) - d5, (double) (y + 1) + d4, (double) (z + 0) - d4, minU, minV);
			tessellator.addVertexWithUV((double) (x + 1) - d5, (double) (y + 0) - d4, (double) (z + 0) - d4, minU, maxV);
		}
		if(direction == 3) {
			tessellator.addVertexWithUV((double) (x + 1) + d4, (double) (y + 0) - d4, (double) z + d5, maxU, maxV);
			tessellator.addVertexWithUV((double) (x + 1) + d4, (double) (y + 1) + d4, (double) z + d5, maxU, minV);
			tessellator.addVertexWithUV((double) (x + 0) - d4, (double) (y + 1) + d4, (double) z + d5, minU, minV);
			tessellator.addVertexWithUV((double) (x + 0) - d4, (double) (y + 0) - d4, (double) z + d5, minU, maxV);
		}
		if(direction == 2) {
			tessellator.addVertexWithUV((double) (x + 1) + d4, (double) (y + 1) + d4, (double) (z + 1) - d5, minU, minV);
			tessellator.addVertexWithUV((double) (x + 1) + d4, (double) (y + 0) - d4, (double) (z + 1) - d5, minU, maxV);
			tessellator.addVertexWithUV((double) (x + 0) - d4, (double) (y + 0) - d4, (double) (z + 1) - d5, maxU, maxV);
			tessellator.addVertexWithUV((double) (x + 0) - d4, (double) (y + 1) + d4, (double) (z + 1) - d5, maxU, minV);
		}
		return true;
	}

	private boolean renderBlockWoodLadder(IBlockAccess blockAccess, int x, int y, int z, BlockWoodLadder block, int modelID, RenderBlocks renderer) {
		int color = block.colorMultiplier(blockAccess, x, y, z);
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable) {
			float f3 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			float f4 = (r * 30.0F + g * 70.0F) / 100.0F;
			float f5 = (r * 30.0F + b * 70.0F) / 100.0F;
			r = f3;
			g = f4;
			b = f5;
		}
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getBlockTileEntity(x, y, z);
		int meta = tile.getTileMetadata();
		int direction = meta & 7;

		Tessellator tessellator = Tessellator.instance;
		Icon icon = renderer.getBlockIconFromSideAndMetadata(block, 0, meta);
		if(renderer.hasOverrideBlockTexture()) {
			icon = renderer.overrideBlockTexture;
		}
		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
		tessellator.setColorOpaque_F(r, g, b);
		double minU = (double) icon.getMinU();
		double minV = (double) icon.getMinV();
		double maxU = (double) icon.getMaxU();
		double maxV = (double) icon.getMaxV();
		double d4 = 0.0D;
		double d5 = 0.05000000074505806D;

		if(direction == 5) {
			tessellator.addVertexWithUV((double) x + d5, (double) (y + 1) + d4, (double) (z + 1) + d4, minU, minV);
			tessellator.addVertexWithUV((double) x + d5, (double) (y + 0) - d4, (double) (z + 1) + d4, minU, maxV);
			tessellator.addVertexWithUV((double) x + d5, (double) (y + 0) - d4, (double) (z + 0) - d4, maxU, maxV);
			tessellator.addVertexWithUV((double) x + d5, (double) (y + 1) + d4, (double) (z + 0) - d4, maxU, minV);
		}
		if(direction == 4) {
			tessellator.addVertexWithUV((double) (x + 1) - d5, (double) (y + 0) - d4, (double) (z + 1) + d4, maxU, maxV);
			tessellator.addVertexWithUV((double) (x + 1) - d5, (double) (y + 1) + d4, (double) (z + 1) + d4, maxU, minV);
			tessellator.addVertexWithUV((double) (x + 1) - d5, (double) (y + 1) + d4, (double) (z + 0) - d4, minU, minV);
			tessellator.addVertexWithUV((double) (x + 1) - d5, (double) (y + 0) - d4, (double) (z + 0) - d4, minU, maxV);
		}
		if(direction == 3) {
			tessellator.addVertexWithUV((double) (x + 1) + d4, (double) (y + 0) - d4, (double) z + d5, maxU, maxV);
			tessellator.addVertexWithUV((double) (x + 1) + d4, (double) (y + 1) + d4, (double) z + d5, maxU, minV);
			tessellator.addVertexWithUV((double) (x + 0) - d4, (double) (y + 1) + d4, (double) z + d5, minU, minV);
			tessellator.addVertexWithUV((double) (x + 0) - d4, (double) (y + 0) - d4, (double) z + d5, minU, maxV);
		}
		if(direction == 2) {
			tessellator.addVertexWithUV((double) (x + 1) + d4, (double) (y + 1) + d4, (double) (z + 1) - d5, minU, minV);
			tessellator.addVertexWithUV((double) (x + 1) + d4, (double) (y + 0) - d4, (double) (z + 1) - d5, minU, maxV);
			tessellator.addVertexWithUV((double) (x + 0) - d4, (double) (y + 0) - d4, (double) (z + 1) - d5, maxU, maxV);
			tessellator.addVertexWithUV((double) (x + 0) - d4, (double) (y + 1) + d4, (double) (z + 1) - d5, maxU, minV);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public int getRenderId() {
		return 100;
	}
}
