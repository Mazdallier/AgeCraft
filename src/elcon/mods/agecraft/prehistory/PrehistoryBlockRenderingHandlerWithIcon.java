package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.core.DustRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.prehistory.blocks.BlockPot;
import elcon.mods.agecraft.prehistory.blocks.BlockRock;
import elcon.mods.agecraft.prehistory.blocks.BlockRock.RockShape;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBed;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityPot;
import elcon.mods.core.BlockRenderingHandlerOverlay;

public class PrehistoryBlockRenderingHandlerWithIcon implements ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		return 200;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		switch(modelID) {
		case 200:
			return renderBlockRock(blockAccess, x, y, z, block, modelID, renderer);
		case 201:
			return renderBlockCampfire(blockAccess, x, y, z, block, modelID, renderer);
		case 202:
			return renderBlockPot(blockAccess, x, y, z, (BlockPot) block, modelID, renderer);
		case 203:
			return renderBlockBed(blockAccess, x, y, z, block, modelID, renderer);
		}
		return false;
	}

	private boolean renderBlockRock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		RockShape shape = BlockRock.shapes[blockAccess.getBlockMetadata(x, y, z)];
		renderer.setRenderBounds(shape.minX, shape.minY, shape.minZ, shape.maxX, shape.maxY, shape.maxZ);
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}

	private boolean renderBlockCampfire(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		TileEntityCampfire tile = (TileEntityCampfire) blockAccess.getBlockTileEntity(x, y, z);
		if(tile.frameStage > 0) {
			if(tile.frameDirection == 0) {
				if(tile.frameStage >= 1) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[0]));
					renderer.setRenderBounds(0.0D, 0.0D, 0.4D, 0.1D, 1.0D, 0.6D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 2) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[1]));
					renderer.setRenderBounds(0.9D, 0.0D, 0.4D, 1.0D, 1.0D, 0.6D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 3) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[2]));
					renderer.setRenderBounds(0.1D, 0.9D, 0.45D, 0.9D, 1.0D, 0.55D);
					renderer.renderStandardBlock(block, x, y, z);
				}
			} else if(tile.frameDirection == 1) {
				if(tile.frameStage >= 1) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[0]));
					renderer.setRenderBounds(0.4D, 0.0D, 0.0D, 0.6D, 1.0D, 0.1D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 2) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[1]));
					renderer.setRenderBounds(0.4D, 0.0D, 0.9D, 0.6D, 1.0D, 1.0D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 3) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[2]));
					renderer.setRenderBounds(0.45D, 0.9D, 0.1D, 0.55D, 1.0D, 0.9D);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			renderer.clearOverrideBlockTexture();
		}
		return true;
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

	private boolean renderBlockBed(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		TileEntityBed tile = (TileEntityBed) blockAccess.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityBed();
		}
		if(!tile.isFoot) {
			if(tile.direction == 0) {
				renderer.uvRotateTop = 1;
			} else if(tile.direction == 1) {
				renderer.uvRotateTop = 3;
			} else if(tile.direction == 2) {
				renderer.uvRotateTop = 2;
			}
		}
		BlockRenderingHandlerOverlay.renderBlockWithOverlay(blockAccess, block, x, y, z, renderer, 0xFFFFFF);
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
	    renderer.uvRotateNorth = 0;
	    renderer.uvRotateTop = 0;
	    renderer.uvRotateBottom = 0;
		return true;
	}
	
	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}
}
