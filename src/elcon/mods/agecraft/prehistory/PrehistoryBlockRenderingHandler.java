package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.core.DustRegistry;
import elcon.mods.agecraft.prehistory.blocks.BlockPot;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityPot;

public class PrehistoryBlockRenderingHandler implements ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		return 200;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		switch(modelID) {
		case 202:
			return renderBlockPot(blockAccess, x, y, z, (BlockPot) block, modelID, renderer);
		}
		return false;
	}

	private boolean renderBlockPot(IBlockAccess blockAccess, int x, int y, int z, BlockPot block, int modelID, RenderBlocks renderer) {
		TileEntityPot tile = (TileEntityPot) blockAccess.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityPot();
		}

		block.renderSolid = true;
		renderer.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.0625D, 0.875D);
		renderer.renderStandardBlock(block, x, y, z);
		block.renderSolid = false;

		renderer.setRenderBounds(0.125D, 0.0625D, 0.125D, 0.875D, 0.625D, 0.1875D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.8125D, 0.0625D, 0.1875D, 0.875D, 0.625D, 0.8125D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.125D, 0.0625D, 0.8125D, 0.875D, 0.625D, 0.875D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.125D, 0.0625D, 0.1875D, 0.1875D, 0.625D, 0.8125D);
		renderer.renderStandardBlock(block, x, y, z);

		if(tile.hasLid) {
			renderer.setRenderBounds(0.1875D, 0.625D, 0.1875D, 0.8125D, 0.6875D, 0.8125D);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.hasDust()) {
			renderer.setOverrideBlockTexture(DustRegistry.getDust(tile.dust).icon);
			renderer.setRenderBounds(0.1875D, 0.0625D, 0.1875D, 0.8125D, 0.5625D, 0.8125D);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.clearOverrideBlockTexture();
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		block.setBlockBoundsForItemRender();
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
}
