package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.core.DustRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.prehistory.blocks.BlockPot;
import elcon.mods.agecraft.prehistory.blocks.BlockRock;
import elcon.mods.agecraft.prehistory.blocks.BlockRock.RockShape;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityPot;

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
		Tessellator tessellator = Tessellator.instance;
		int bedDirection = block.getBedDirection(blockAccess, x, y, z);
		boolean isBedFoot = block.isBedFoot(blockAccess, x, y, z);
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		int j1 = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
		tessellator.setBrightness(j1);
		tessellator.setColorOpaque_F(f, f, f);
		Icon icon = renderer.getBlockIcon(block, blockAccess, x, y, z, 0);
		if(renderer.hasOverrideBlockTexture()) {
			icon = renderer.overrideBlockTexture;
		}
		double minU = (double) icon.getMinU();
		double maxU = (double) icon.getMaxU();
		double minV = (double) icon.getMinV();
		double maxV = (double) icon.getMaxV();
		double minX = (double) x + renderer.renderMinX;
		double maxX = (double) x + renderer.renderMaxX;
		double minY = (double) y + renderer.renderMinY + 0.1875D;
		double minZ = (double) z + renderer.renderMinZ;
		double maxZ = (double) z + renderer.renderMaxZ;
		tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
		tessellator.addVertexWithUV(minX, minY, minZ, minU, minV);
		tessellator.addVertexWithUV(maxX, minY, minZ, maxU, minV);
		tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z));
		tessellator.setColorOpaque_F(f1, f1, f1);
		icon = renderer.getBlockIcon(block, blockAccess, x, y, z, 1);
		if(renderer.hasOverrideBlockTexture()) {
			icon = renderer.overrideBlockTexture;
		}
		minU = (double) icon.getMinU();
		maxU = (double) icon.getMaxU();
		minV = (double) icon.getMinV();
		maxV = (double) icon.getMaxV();
		minX = minU;
		maxX = maxU;
		minY = minV;
		minZ = minV;
		maxZ = minU;
		double maxUU = maxU;
		double maxVV = maxV;
		double maxVVV = maxV;
		if(bedDirection == 0) {
			maxX = minU;
			minY = maxV;
			maxZ = maxU;
			maxVVV = minV;
		} else if(bedDirection == 2) {
			minX = maxU;
			minZ = maxV;
			maxUU = minU;
			maxVV = minV;
		} else if(bedDirection == 3) {
			minX = maxU;
			minZ = maxV;
			maxUU = minU;
			maxVV = minV;
			maxX = minU;
			minY = maxV;
			maxZ = maxU;
			maxVVV = minV;
		}
		double minXX = (double) x + renderer.renderMinX;
		double maxXX = (double) x + renderer.renderMaxX;
		double maxYY = (double) y + renderer.renderMaxY;
		double minZZ = (double) z + renderer.renderMinZ;
		double maxZZ = (double) z + renderer.renderMaxZ;
		tessellator.addVertexWithUV(maxXX, maxYY, maxZZ, maxZ, maxVV);
		tessellator.addVertexWithUV(maxXX, maxYY, minZZ, minX, minY);
		tessellator.addVertexWithUV(minXX, maxYY, minZZ, maxX, minZ);
		tessellator.addVertexWithUV(minXX, maxYY, maxZZ, maxUU, maxVVV);
		int side = Direction.directionToFacing[bedDirection];
		if(isBedFoot) {
			side = Direction.directionToFacing[Direction.rotateOpposite[bedDirection]];
		}
		byte flippedSide = 4;
		switch(bedDirection) {
		case 0:
			flippedSide = 5;
			break;
		case 1:
			flippedSide = 3;
		case 2:
		default:
			break;
		case 3:
			flippedSide = 2;
		}
		if(side != 2 && (renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x, y, z - 1, 2))) {
			tessellator.setBrightness(renderer.renderMinZ > 0.0D ? j1 : block.getMixedBrightnessForBlock(blockAccess, x, y, z - 1));
			tessellator.setColorOpaque_F(f2, f2, f2);
			renderer.flipTexture = flippedSide == 2;
			renderer.renderFaceZNeg(block, (double) x, (double) y, (double) z, renderer.getBlockIcon(block, blockAccess, x, y, z, 2));
		}
		if(side != 3 && (renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x, y, z + 1, 3))) {
			tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? j1 : block.getMixedBrightnessForBlock(blockAccess, x, y, z + 1));
			tessellator.setColorOpaque_F(f2, f2, f2);
			renderer.flipTexture = flippedSide == 3;
			renderer.renderFaceZPos(block, (double) x, (double) y, (double) z, renderer.getBlockIcon(block, blockAccess, x, y, z, 3));
		}
		if(side != 4 && (renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x - 1, y, z, 4))) {
			tessellator.setBrightness(renderer.renderMinZ > 0.0D ? j1 : block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z));
			tessellator.setColorOpaque_F(f3, f3, f3);
			renderer.flipTexture = flippedSide == 4;
			renderer.renderFaceXNeg(block, (double) x, (double) y, (double) z, renderer.getBlockIcon(block, blockAccess, x, y, z, 4));
		}
		if(side != 5 && (renderer.renderAllFaces || block.shouldSideBeRendered(blockAccess, x + 1, y, z, 5))) {
			tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? j1 : block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z));
			tessellator.setColorOpaque_F(f3, f3, f3);
			renderer.flipTexture = flippedSide == 5;
			renderer.renderFaceXPos(block, (double) x, (double) y, (double) z, renderer.getBlockIcon(block, blockAccess, x, y, z, 5));
		}
		renderer.flipTexture = false;
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
