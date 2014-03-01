package org.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.agecraft.core.Trees;
import org.agecraft.core.registry.DustRegistry;
import org.agecraft.core.registry.TreeRegistry;
import org.agecraft.prehistory.blocks.BlockBarrel;
import org.agecraft.prehistory.blocks.BlockBox;
import org.agecraft.prehistory.blocks.BlockPot;
import org.agecraft.prehistory.blocks.BlockRock;
import org.agecraft.prehistory.blocks.BlockRock.RockShape;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBarrel;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBed;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBox;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryCampfire;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryPot;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.elconqore.render.EQBlockRenderingHandler;

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
		case 204:
			return renderBlockBarrel(blockAccess, x, y, z, (BlockBarrel) block, modelID, renderer);
		case 205:
			return renderBlockBox(blockAccess, x, y, z, (BlockBox) block, modelID, renderer);
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
		TileEntityPrehistoryCampfire tile = (TileEntityPrehistoryCampfire) blockAccess.getTileEntity(x, y, z);
		if(tile.frameStage > 0) {
			if(tile.frameDirection == 0) {
				if(tile.frameStage >= 1) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameTypes[0]));
					renderer.setRenderBounds(0.0D, 0.0D, 0.4D, 0.1D, 1.0D, 0.6D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 2) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameTypes[1]));
					renderer.setRenderBounds(0.9D, 0.0D, 0.4D, 1.0D, 1.0D, 0.6D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 3) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameTypes[2]));
					renderer.setRenderBounds(0.1D, 0.9D, 0.45D, 0.9D, 1.0D, 0.55D);
					renderer.renderStandardBlock(block, x, y, z);
				}
			} else if(tile.frameDirection == 1) {
				if(tile.frameStage >= 1) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameTypes[0]));
					renderer.setRenderBounds(0.4D, 0.0D, 0.0D, 0.6D, 1.0D, 0.1D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 2) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameTypes[1]));
					renderer.setRenderBounds(0.4D, 0.0D, 0.9D, 0.6D, 1.0D, 1.0D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 3) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameTypes[2]));
					renderer.setRenderBounds(0.45D, 0.9D, 0.1D, 0.55D, 1.0D, 0.9D);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			renderer.clearOverrideBlockTexture();
		}
		return true;
	}

	private boolean renderBlockPot(IBlockAccess blockAccess, int x, int y, int z, BlockPot block, int modelID, RenderBlocks renderer) {
		TileEntityPrehistoryPot tile = (TileEntityPrehistoryPot) blockAccess.getTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityPrehistoryPot();
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
		TileEntityPrehistoryBed tile = (TileEntityPrehistoryBed) blockAccess.getTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityPrehistoryBed();
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
		EQBlockRenderingHandler.renderBlockWithOverlay(blockAccess, block, x, y, z, renderer);
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
	    renderer.uvRotateNorth = 0;
	    renderer.uvRotateTop = 0;
	    renderer.uvRotateBottom = 0;
		return true;
	}
	
	private boolean renderBlockBarrel(IBlockAccess blockAccess, int x, int y, int z, BlockBarrel block, int modelID, RenderBlocks renderer) {
		TileEntityPrehistoryBarrel tile = (TileEntityPrehistoryBarrel) blockAccess.getTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityPrehistoryBarrel();
		}

		renderer.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.0625D, 0.875D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.125D, 0.0625D, 0.125D, 0.875D, 1.0D, 0.1875D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.8125D, 0.0625D, 0.1875D, 0.875D, 1.0D, 0.8125D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.125D, 0.0625D, 0.8125D, 0.875D, 1.0D, 0.875D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.125D, 0.0625D, 0.1875D, 0.1875D, 1.0D, 0.8125D);
		renderer.renderStandardBlock(block, x, y, z);

		if(tile.hasLid) {
			renderer.setRenderBounds(0.1875D, 0.875D, 0.1875D, 0.8125D, 0.9375D, 0.8125D);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if(tile.stickType >= 0) {
			renderer.setOverrideBlockTexture(TreeRegistry.instance.get(tile.stickType).planks);
			renderer.setRenderBounds(0.1875D, 0.8075D, 0.4375D, 0.8125D, 0.875D, 0.5625D);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.clearOverrideBlockTexture();
		}
		return true;
	}
	
	private boolean renderBlockBox(IBlockAccess blockAccess, int x, int y, int z, BlockBox block, int modelID, RenderBlocks renderer) {
		TileEntityPrehistoryBox tile = (TileEntityPrehistoryBox) blockAccess.getTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityPrehistoryBox();
		}

		renderer.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.0625D, 0.875D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.125D, 0.0625D, 0.125D, 0.875D, 0.5D, 0.1875D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.8125D, 0.0625D, 0.1875D, 0.875D, 0.5D, 0.8125D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.125D, 0.0625D, 0.8125D, 0.875D, 0.5D, 0.875D);
		renderer.renderStandardBlock(block, x, y, z);

		renderer.setRenderBounds(0.125D, 0.0625D, 0.1875D, 0.1875D, 0.5D, 0.8125D);
		renderer.renderStandardBlock(block, x, y, z);

		if(tile.hasLid) {
			renderer.setRenderBounds(0.1875D, 0.375D, 0.1875D, 0.8125D, 0.4375D, 0.8125D);
			renderer.renderStandardBlock(block, x, y, z);
		}
		return true;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}
}
