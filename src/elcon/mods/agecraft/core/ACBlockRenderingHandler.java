package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.core.blocks.BlockWoodWall;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class ACBlockRenderingHandler implements ISimpleBlockRenderingHandler {

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		switch(modelID) {
		case 105:
			return renderBlockWood(blockAccess, x, y, z, block, modelID, renderer);
		case 106:
			return renderer.renderStandardBlock(block, x, y, z);
		case 107:
			return renderBlockWoodWall(blockAccess, x, y, z, (BlockWoodWall) block, modelID, renderer);
		}
		return false;
	}

	private boolean renderBlockWood(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getBlockTileEntity(x, y, z);
		int direction = tile.getTileMetadata() & 3;
		if(direction == 2) {
			renderer.uvRotateEast = 1;
			renderer.uvRotateWest = 1;
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 1;
		} else if(direction == 1) {
			renderer.uvRotateSouth = 1;
			renderer.uvRotateNorth = 1;
		}
		boolean flag = renderer.renderStandardBlock(block, x, y, z);
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		return flag;
	}

	private boolean renderBlockWoodWall(IBlockAccess blockAccess, int x, int y, int z, BlockWoodWall block, int modelID, RenderBlocks renderer) {
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getBlockTileEntity(x, y, z);
		int meta = tile.getTileMetadata();
		boolean connectMinX = block.canConnectTo(blockAccess, x - 1, y, z, meta);
		boolean connectMaxX = block.canConnectTo(blockAccess, x + 1, y, z, meta);
		boolean connectMaxY = blockAccess.isAirBlock(x, y + 1, z);
		boolean connectMinZ = block.canConnectTo(blockAccess, x, y, z - 1, meta);
		boolean connectMaxZ = block.canConnectTo(blockAccess, x, y, z + 1, meta);
		boolean onlyX = !connectMinZ && !connectMaxZ && connectMinX && connectMaxX;
		boolean onlyZ = connectMinZ && connectMaxZ && !connectMinX && !connectMaxX;

		if((onlyZ || onlyX) && connectMaxY) {
			if(onlyZ) {
				renderer.setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			} else {
				renderer.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
		} else {
			renderer.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
			renderer.renderStandardBlock(block, x, y, z);
			if(connectMinX) {
				renderer.setRenderBounds(0.0D, 0.0D, 0.3125D, 0.25D, 0.8125D, 0.6875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(connectMaxX) {
				renderer.setRenderBounds(0.75D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(connectMinZ) {
				renderer.setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 0.25D);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(connectMaxZ) {
				renderer.setRenderBounds(0.3125D, 0.0D, 0.75D, 0.6875D, 0.8125D, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		block.setBlockBoundsBasedOnState(blockAccess, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO: after FML change, make model 106 return false
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		switch(modelID) {
		case 105:
		case 106: // TODO: remove this after FML change
			block.setBlockBoundsForItemRender();
			renderItemBlock(block, metadata, modelID, renderer);
			break;
		case 107:
			renderItemBlockWoodWall(block, metadata, modelID, renderer);
			break;
		}
	}

	private void renderItemBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		if(renderer.useInventoryTint) {
			int color = block.getRenderColor(metadata);
			float r = (float) (color >> 16 & 255) / 255.0F;
			float g = (float) (color >> 8 & 255) / 255.0F;
			float b = (float) (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
		}
		renderer.setRenderBoundsFromBlock(block);

		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	private void renderItemBlockWoodWall(Block block, int metadata, int modelID, RenderBlocks renderer) {		
		Tessellator tessellator = Tessellator.instance;
		if(renderer.useInventoryTint) {
			int color = block.getRenderColor(metadata);
			float r = (float) (color >> 16 & 255) / 255.0F;
			float g = (float) (color >> 8 & 255) / 255.0F;
			float b = (float) (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
		}
		renderer.setRenderBoundsFromBlock(block);
		
		for(int k = 0; k < 2; k++) {
			if(k == 0) {
				renderer.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
			}
			if(k == 1) {
				renderer.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
			}
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
			tessellator.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}

	@Override
	public int getRenderId() {
		return 100;
	}
}
