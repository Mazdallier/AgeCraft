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
