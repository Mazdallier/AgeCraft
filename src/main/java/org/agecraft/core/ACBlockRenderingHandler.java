package org.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

import org.agecraft.core.blocks.metal.BlockMetalFence;
import org.agecraft.core.blocks.metal.BlockMetalFenceGate;
import org.agecraft.core.blocks.tree.BlockWood;
import org.agecraft.core.blocks.tree.BlockWoodFence;
import org.agecraft.core.blocks.tree.BlockWoodFenceGate;
import org.agecraft.core.blocks.tree.BlockWoodWall;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.elconqore.blocks.IBlockRotated;
import elcon.mods.elconqore.tileentities.TileEntityMetadata;

public class ACBlockRenderingHandler implements ISimpleBlockRenderingHandler {

	//private TileEntityAgeTeleporterChest tileChestInstance = new TileEntityAgeTeleporterChest();

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		switch(modelID) {
		case 91:
			return renderBlockRotated(blockAccess, x, y, z, (IBlockRotated) block, modelID, renderer);
		case 100:
			return renderBlockMetalFence(blockAccess, x, y, z, (BlockMetalFence) block, modelID, renderer);
		case 101:
			return renderBlockMetalFenceGate(blockAccess, x, y, z, (BlockMetalFenceGate) block, modelID, renderer);
		case 105:
			return renderBlockWood(blockAccess, x, y, z, (BlockWood) block, modelID, renderer);
		case 106:
			return renderer.renderStandardBlock(block, x, y, z);
		case 107:
			return renderBlockWoodWall(blockAccess, x, y, z, (BlockWoodWall) block, modelID, renderer);
		case 108:
			return renderBlockWoodFence(blockAccess, x, y, z, (BlockWoodFence) block, modelID, renderer);
		case 109:
			return renderBlockWoodFenceGate(blockAccess, x, y, z, (BlockWoodFenceGate) block, modelID, renderer);
		}
		return false;
	}
	
	private boolean renderBlockRotated(IBlockAccess blockAccess, int x, int y, int z, IBlockRotated block, int modelID, RenderBlocks renderer) {
		int direction = block.getBlockRotation(blockAccess, x, y, z);
		if(direction == 2) {
			renderer.uvRotateEast = 1;
			renderer.uvRotateWest = 1;
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 1;
		} else if(direction == 1) {
			renderer.uvRotateSouth = 1;
			renderer.uvRotateNorth = 1;
		}
		boolean flag = renderer.renderStandardBlock((Block) block, x, y, z);
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		return flag;
	}
	
	private boolean renderBlockMetalFence(IBlockAccess blockAccess, int x, int y, int z, BlockMetalFence block, int modelID, RenderBlocks renderer) {
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getTileEntity(x, y, z);
		int meta = tile.getTileMetadata();

		boolean flag = false;
		float minX = 0.375F;
		float maxX = 0.625F;
		renderer.setRenderBounds((double) minX, 0.0D, (double) minX, (double) maxX, 1.0D, (double) maxX);
		renderer.renderStandardBlock(block, x, y, z);
		flag = true;
		boolean connectX = false;
		boolean connectZ = false;
		if(block.canConnectTo(blockAccess, x - 1, y, z, meta) || block.canConnectTo(blockAccess, x + 1, y, z, meta)) {
			connectX = true;
		}
		if(block.canConnectTo(blockAccess, x, y, z - 1, meta) || block.canConnectTo(blockAccess, x, y, z + 1, meta)) {
			connectZ = true;
		}

		boolean connectMinX = block.canConnectTo(blockAccess, x - 1, y, z, meta);
		boolean connectMaxX = block.canConnectTo(blockAccess, x + 1, y, z, meta);
		boolean connectMinZ = block.canConnectTo(blockAccess, x, y, z - 1, meta);
		boolean connectMaxZ = block.canConnectTo(blockAccess, x, y, z + 1, meta);

		if(!connectX && !connectZ) {
			connectX = true;
		}

		minX = 0.4375F;
		maxX = 0.5625F;
		float minY = 0.75F;
		float maxY = 0.9375F;
		float f4 = connectMinX ? 0.0F : minX;
		float f5 = connectMaxX ? 1.0F : maxX;
		float minZ = connectMinZ ? 0.0F : minX;
		float maxZ = connectMaxZ ? 1.0F : maxX;
		if(connectX) {
			renderer.setRenderBounds((double) f4, (double) minY, (double) minX, (double) f5, (double) maxY, (double) maxX);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}
		if(connectZ) {
			renderer.setRenderBounds((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		minY = 0.375F;
		maxY = 0.5625F;
		if(connectX) {
			renderer.setRenderBounds((double) f4, (double) minY, (double) minX, (double) f5, (double) maxY, (double) maxX);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		if(connectZ) {
			renderer.setRenderBounds((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}
		block.setBlockBoundsBasedOnState(blockAccess, x, y, z);
		return flag;
	}

	private boolean renderBlockMetalFenceGate(IBlockAccess blockAccess, int x, int y, int z, BlockMetalFenceGate block, int modelID, RenderBlocks renderer) {
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getTileEntity(x, y, z);
		int meta = tile.getTileMetadata();
		boolean isOpen = block.isFenceGateOpen(meta);
		int direction = meta & 3;

		boolean flag = true;
		float f = 0.375F;
		float f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = 0.3125F;
		float f5 = 1.0F;
		if((direction == 2 || direction == 0) && blockAccess.getBlock(x - 1, y, z) == Blocks.cobblestone_wall && blockAccess.getBlock(x + 1, y, z) == Blocks.cobblestone_wall || (direction == 3 || direction == 1) && blockAccess.getBlock(x, y, z - 1) == Blocks.cobblestone_wall && blockAccess.getBlock(x, y, z + 1) == Blocks.cobblestone_wall) {
			f -= 0.1875F;
			f1 -= 0.1875F;
			f2 -= 0.1875F;
			f3 -= 0.1875F;
			f4 -= 0.1875F;
			f5 -= 0.1875F;
		}
		renderer.renderAllFaces = true;
		float f6;
		float f7;
		float f8;
		float f9;
		if(direction != 3 && direction != 1) {
			f6 = 0.0F;
			f8 = 0.125F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds((double) f6, (double) f4, (double) f7, (double) f8, (double) f5, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f6 = 0.875F;
			f8 = 1.0F;
			renderer.setRenderBounds((double) f6, (double) f4, (double) f7, (double) f8, (double) f5, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
		} else {
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.0F;
			f9 = 0.125F;
			renderer.setRenderBounds((double) f6, (double) f4, (double) f7, (double) f8, (double) f5, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f7 = 0.875F;
			f9 = 1.0F;
			renderer.setRenderBounds((double) f6, (double) f4, (double) f7, (double) f8, (double) f5, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.uvRotateTop = 0;
		}
		if(isOpen) {
			if(direction == 2 || direction == 0) {
				renderer.uvRotateTop = 1;
			}
			//float f10;
			//float f11;
			//float f12;
			if(direction == 3) {
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.5625F;
				//f12 = 0.8125F;
				//f11 = 0.9375F;
				renderer.setRenderBounds(0.8125D, (double) f, 0.0D, 0.9375D, (double) f3, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.8125D, (double) f, 0.875D, 0.9375D, (double) f3, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.5625D, (double) f, 0.0D, 0.8125D, (double) f1, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.5625D, (double) f, 0.875D, 0.8125D, (double) f1, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.5625D, (double) f2, 0.0D, 0.8125D, (double) f3, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.5625D, (double) f2, 0.875D, 0.8125D, (double) f3, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			} else if(direction == 1) {
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.0625F;
				//f12 = 0.1875F;
				//f11 = 0.4375F;
				renderer.setRenderBounds(0.0625D, (double) f, 0.0D, 0.1875D, (double) f3, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0625D, (double) f, 0.875D, 0.1875D, (double) f3, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.1875D, (double) f, 0.0D, 0.4375D, (double) f1, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.1875D, (double) f, 0.875D, 0.4375D, (double) f1, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.1875D, (double) f2, 0.0D, 0.4375D, (double) f3, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.1875D, (double) f2, 0.875D, 0.4375D, (double) f3, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			} else if(direction == 0) {
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.5625F;
				//f12 = 0.8125F;
				//f11 = 0.9375F;
				renderer.setRenderBounds(0.0D, (double) f, 0.8125D, 0.125D, (double) f3, 0.9375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f, 0.8125D, 1.0D, (double) f3, 0.9375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, (double) f, 0.5625D, 0.125D, (double) f1, 0.8125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f, 0.5625D, 1.0D, (double) f1, 0.8125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, (double) f2, 0.5625D, 0.125D, (double) f3, 0.8125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f2, 0.5625D, 1.0D, (double) f3, 0.8125D);
				renderer.renderStandardBlock(block, x, y, z);
			} else if(direction == 2) {
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.0625F;
				//f12 = 0.1875F;
				//f11 = 0.4375F;
				renderer.setRenderBounds(0.0D, (double) f, 0.0625D, 0.125D, (double) f3, 0.1875D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f, 0.0625D, 1.0D, (double) f3, 0.1875D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, (double) f, 0.1875D, 0.125D, (double) f1, 0.4375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f, 0.1875D, 1.0D, (double) f1, 0.4375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, (double) f2, 0.1875D, 0.125D, (double) f3, 0.4375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f2, 0.1875D, 1.0D, (double) f3, 0.4375D);
				renderer.renderStandardBlock(block, x, y, z);
			}
		} else if(direction != 3 && direction != 1) {
			f6 = 0.375F;
			f8 = 0.5F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f6 = 0.5F;
			f8 = 0.625F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f6 = 0.625F;
			f8 = 0.875F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f1, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds((double) f6, (double) f2, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f6 = 0.125F;
			f8 = 0.375F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f1, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds((double) f6, (double) f2, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
		} else {
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.375F;
			f9 = 0.5F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f7 = 0.5F;
			f9 = 0.625F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f7 = 0.625F;
			f9 = 0.875F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f1, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds((double) f6, (double) f2, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f7 = 0.125F;
			f9 = 0.375F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f1, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds((double) f6, (double) f2, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
		}
		renderer.renderAllFaces = false;
		renderer.uvRotateTop = 0;
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return flag;
	}

	private boolean renderBlockWood(IBlockAccess blockAccess, int x, int y, int z, BlockWood block, int modelID, RenderBlocks renderer) {
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getTileEntity(x, y, z);
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
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getTileEntity(x, y, z);
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

	private boolean renderBlockWoodFence(IBlockAccess blockAccess, int x, int y, int z, BlockWoodFence block, int modelID, RenderBlocks renderer) {
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getTileEntity(x, y, z);
		int meta = tile.getTileMetadata();

		boolean flag = false;
		float minX = 0.375F;
		float maxX = 0.625F;
		renderer.setRenderBounds((double) minX, 0.0D, (double) minX, (double) maxX, 1.0D, (double) maxX);
		renderer.renderStandardBlock(block, x, y, z);
		flag = true;
		boolean connectX = false;
		boolean connectZ = false;
		if(block.canConnectTo(blockAccess, x - 1, y, z, meta) || block.canConnectTo(blockAccess, x + 1, y, z, meta)) {
			connectX = true;
		}
		if(block.canConnectTo(blockAccess, x, y, z - 1, meta) || block.canConnectTo(blockAccess, x, y, z + 1, meta)) {
			connectZ = true;
		}

		boolean connectMinX = block.canConnectTo(blockAccess, x - 1, y, z, meta);
		boolean connectMaxX = block.canConnectTo(blockAccess, x + 1, y, z, meta);
		boolean connectMinZ = block.canConnectTo(blockAccess, x, y, z - 1, meta);
		boolean connectMaxZ = block.canConnectTo(blockAccess, x, y, z + 1, meta);

		if(!connectX && !connectZ) {
			connectX = true;
		}

		minX = 0.4375F;
		maxX = 0.5625F;
		float minY = 0.75F;
		float maxY = 0.9375F;
		float f4 = connectMinX ? 0.0F : minX;
		float f5 = connectMaxX ? 1.0F : maxX;
		float minZ = connectMinZ ? 0.0F : minX;
		float maxZ = connectMaxZ ? 1.0F : maxX;
		if(connectX) {
			renderer.setRenderBounds((double) f4, (double) minY, (double) minX, (double) f5, (double) maxY, (double) maxX);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}
		if(connectZ) {
			renderer.setRenderBounds((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		minY = 0.375F;
		maxY = 0.5625F;
		if(connectX) {
			renderer.setRenderBounds((double) f4, (double) minY, (double) minX, (double) f5, (double) maxY, (double) maxX);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		if(connectZ) {
			renderer.setRenderBounds((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}
		block.setBlockBoundsBasedOnState(blockAccess, x, y, z);
		return flag;
	}

	private boolean renderBlockWoodFenceGate(IBlockAccess blockAccess, int x, int y, int z, BlockWoodFenceGate block, int modelID, RenderBlocks renderer) {
		TileEntityMetadata tile = (TileEntityMetadata) blockAccess.getTileEntity(x, y, z);
		int meta = tile.getTileMetadata();
		boolean isOpen = block.isFenceGateOpen(meta);
		int direction = meta & 3;

		boolean flag = true;
		float f = 0.375F;
		float f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = 0.3125F;
		float f5 = 1.0F;
		if((direction == 2 || direction == 0) && blockAccess.getBlock(x - 1, y, z) == Blocks.cobblestone_wall && blockAccess.getBlock(x + 1, y, z) == Blocks.cobblestone_wall || (direction == 3 || direction == 1) && blockAccess.getBlock(x, y, z - 1) == Blocks.cobblestone_wall && blockAccess.getBlock(x, y, z + 1) == Blocks.cobblestone_wall) {
			f -= 0.1875F;
			f1 -= 0.1875F;
			f2 -= 0.1875F;
			f3 -= 0.1875F;
			f4 -= 0.1875F;
			f5 -= 0.1875F;
		}
		renderer.renderAllFaces = true;
		float f6;
		float f7;
		float f8;
		float f9;
		if(direction != 3 && direction != 1) {
			f6 = 0.0F;
			f8 = 0.125F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds((double) f6, (double) f4, (double) f7, (double) f8, (double) f5, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f6 = 0.875F;
			f8 = 1.0F;
			renderer.setRenderBounds((double) f6, (double) f4, (double) f7, (double) f8, (double) f5, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
		} else {
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.0F;
			f9 = 0.125F;
			renderer.setRenderBounds((double) f6, (double) f4, (double) f7, (double) f8, (double) f5, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f7 = 0.875F;
			f9 = 1.0F;
			renderer.setRenderBounds((double) f6, (double) f4, (double) f7, (double) f8, (double) f5, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.uvRotateTop = 0;
		}
		if(isOpen) {
			if(direction == 2 || direction == 0) {
				renderer.uvRotateTop = 1;
			}
			//float f10;
			//float f11;
			//float f12;
			if(direction == 3) {
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.5625F;
				//f12 = 0.8125F;
				//f11 = 0.9375F;
				renderer.setRenderBounds(0.8125D, (double) f, 0.0D, 0.9375D, (double) f3, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.8125D, (double) f, 0.875D, 0.9375D, (double) f3, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.5625D, (double) f, 0.0D, 0.8125D, (double) f1, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.5625D, (double) f, 0.875D, 0.8125D, (double) f1, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.5625D, (double) f2, 0.0D, 0.8125D, (double) f3, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.5625D, (double) f2, 0.875D, 0.8125D, (double) f3, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			} else if(direction == 1) {
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.0625F;
				//f12 = 0.1875F;
				//f11 = 0.4375F;
				renderer.setRenderBounds(0.0625D, (double) f, 0.0D, 0.1875D, (double) f3, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0625D, (double) f, 0.875D, 0.1875D, (double) f3, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.1875D, (double) f, 0.0D, 0.4375D, (double) f1, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.1875D, (double) f, 0.875D, 0.4375D, (double) f1, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.1875D, (double) f2, 0.0D, 0.4375D, (double) f3, 0.125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.1875D, (double) f2, 0.875D, 0.4375D, (double) f3, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			} else if(direction == 0) {
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.5625F;
				//f12 = 0.8125F;
				//f11 = 0.9375F;
				renderer.setRenderBounds(0.0D, (double) f, 0.8125D, 0.125D, (double) f3, 0.9375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f, 0.8125D, 1.0D, (double) f3, 0.9375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, (double) f, 0.5625D, 0.125D, (double) f1, 0.8125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f, 0.5625D, 1.0D, (double) f1, 0.8125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, (double) f2, 0.5625D, 0.125D, (double) f3, 0.8125D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f2, 0.5625D, 1.0D, (double) f3, 0.8125D);
				renderer.renderStandardBlock(block, x, y, z);
			} else if(direction == 2) {
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.0625F;
				//f12 = 0.1875F;
				//f11 = 0.4375F;
				renderer.setRenderBounds(0.0D, (double) f, 0.0625D, 0.125D, (double) f3, 0.1875D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f, 0.0625D, 1.0D, (double) f3, 0.1875D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, (double) f, 0.1875D, 0.125D, (double) f1, 0.4375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f, 0.1875D, 1.0D, (double) f1, 0.4375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.0D, (double) f2, 0.1875D, 0.125D, (double) f3, 0.4375D);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.875D, (double) f2, 0.1875D, 1.0D, (double) f3, 0.4375D);
				renderer.renderStandardBlock(block, x, y, z);
			}
		} else if(direction != 3 && direction != 1) {
			f6 = 0.375F;
			f8 = 0.5F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f6 = 0.5F;
			f8 = 0.625F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f6 = 0.625F;
			f8 = 0.875F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f1, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds((double) f6, (double) f2, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f6 = 0.125F;
			f8 = 0.375F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f1, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds((double) f6, (double) f2, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
		} else {
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.375F;
			f9 = 0.5F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f7 = 0.5F;
			f9 = 0.625F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f7 = 0.625F;
			f9 = 0.875F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f1, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds((double) f6, (double) f2, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			f7 = 0.125F;
			f9 = 0.375F;
			renderer.setRenderBounds((double) f6, (double) f, (double) f7, (double) f8, (double) f1, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds((double) f6, (double) f2, (double) f7, (double) f8, (double) f3, (double) f9);
			renderer.renderStandardBlock(block, x, y, z);
		}
		renderer.renderAllFaces = false;
		renderer.uvRotateTop = 0;
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return flag;
	}
	
	/*private boolean renderBlockSmelteryFurnace(IBlockAccess blockAccess, int x, int y, int z, BlockSmelteryFurnace block, int modelID, RenderBlocks renderer) {
		boolean flag = renderer.renderStandardBlock(block, x, y, z);
		
		int mixedBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		IIcon overlay = null;
		
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 0);
		if(overlay != null) {
			EQBlockRenderingHandler.renderBottomFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, 255.0F, 255.0F, 255.0F);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 1);
		if(overlay != null) {
			EQBlockRenderingHandler.renderTopFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, 255.0F, 255.0F, 255.0F);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 2);
		if(overlay != null) {
			EQBlockRenderingHandler.renderEastFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, 255.0F, 255.0F, 255.0F);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 3);
		if(overlay != null) {
			EQBlockRenderingHandler.renderWestFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, 255.0F, 255.0F, 255.0F);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 4);
		if(overlay != null) {
			EQBlockRenderingHandler.renderNorthFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, 255.0F, 255.0F, 255.0F);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 5);
		if(overlay != null) {
			EQBlockRenderingHandler.renderSouthFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, 255.0F, 255.0F, 255.0F);
		}
		return flag;
	}*/

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		switch(modelID) {
		case 91:
			renderItemBlock(block, metadata, modelID, renderer);
			break;
		case 99:
			//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            //GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            //Minecraft.getMinecraft().getTextureManager().bindTexture(ResourcesCore.ageTeleporterChest);
            //TileEntityRenderer.instance.renderTileEntityAt(tileChestInstance , 0.0D, 0.0D, 0.0D, 0.0F);
            //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			//break;
		case 100:
			renderItemBlockMetalFence(block, metadata, modelID, renderer);
			break;
		case 101:
			renderItemBlockMetalFenceGate(block, metadata, modelID, renderer);
			break;
		case 105:
			renderItemBlock(block, metadata, modelID, renderer);
			break;
		case 107:
			renderItemBlockWoodWall(block, metadata, modelID, renderer);
			break;
		case 108:
			renderItemBlockWoodFence(block, metadata, modelID, renderer);
			break;
		case 109:
			renderItemBlockWoodFenceGate(block, metadata, modelID, renderer);
			break;
		case 115:
			//renderItemBlockSmelteryFurnace((BlockSmelteryFurnace) block, metadata, modelID, renderer);
			break;
		}
	}
	
	private void renderItemBlockMetalFence(Block block, int metadata, int modelID, RenderBlocks renderer) {
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

		for(int k = 0; k < 4; ++k) {
			float f2 = 0.125F;
			if(k == 0) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.0D, 0.0D, (double) (0.5F + f2), 1.0D, (double) (f2 * 2.0F));
			}
			if(k == 1) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.0D, (double) (1.0F - f2 * 2.0F), (double) (0.5F + f2), 1.0D, 1.0D);
			}
			f2 = 0.0625F;
			if(k == 2) {
				renderer.setRenderBounds((double) (0.5F - f2), (double) (1.0F - f2 * 3.0F), (double) (-f2 * 2.0F), (double) (0.5F + f2), (double) (1.0F - f2), (double) (1.0F + f2 * 2.0F));
			}
			if(k == 3) {
				renderer.setRenderBounds((double) (0.5F - f2), (double) (0.5F - f2 * 3.0F), (double) (-f2 * 2.0F), (double) (0.5F + f2), (double) (0.5F - f2), (double) (1.0F + f2 * 2.0F));
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

	private void renderItemBlockMetalFenceGate(Block block, int metadata, int modelID, RenderBlocks renderer) {
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

		for(int k = 0; k < 3; ++k) {
			float f2 = 0.0625F;
			if(k == 0) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.30000001192092896D, 0.0D, (double) (0.5F + f2), 1.0D, (double) (f2 * 2.0F));
			}
			if(k == 1) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.30000001192092896D, (double) (1.0F - f2 * 2.0F), (double) (0.5F + f2), 1.0D, 1.0D);
			}
			f2 = 0.0625F;
			if(k == 2) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.5D, 0.0D, (double) (0.5F + f2), (double) (1.0F - f2), 1.0D);
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

	private void renderItemBlockWoodWall(Block block, int metadata, int modelID, RenderBlocks renderer) {
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

	private void renderItemBlockWoodFence(Block block, int metadata, int modelID, RenderBlocks renderer) {
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

		for(int k = 0; k < 4; ++k) {
			float f2 = 0.125F;
			if(k == 0) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.0D, 0.0D, (double) (0.5F + f2), 1.0D, (double) (f2 * 2.0F));
			}
			if(k == 1) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.0D, (double) (1.0F - f2 * 2.0F), (double) (0.5F + f2), 1.0D, 1.0D);
			}
			f2 = 0.0625F;
			if(k == 2) {
				renderer.setRenderBounds((double) (0.5F - f2), (double) (1.0F - f2 * 3.0F), (double) (-f2 * 2.0F), (double) (0.5F + f2), (double) (1.0F - f2), (double) (1.0F + f2 * 2.0F));
			}
			if(k == 3) {
				renderer.setRenderBounds((double) (0.5F - f2), (double) (0.5F - f2 * 3.0F), (double) (-f2 * 2.0F), (double) (0.5F + f2), (double) (0.5F - f2), (double) (1.0F + f2 * 2.0F));
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

	private void renderItemBlockWoodFenceGate(Block block, int metadata, int modelID, RenderBlocks renderer) {
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

		for(int k = 0; k < 3; ++k) {
			float f2 = 0.0625F;
			if(k == 0) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.30000001192092896D, 0.0D, (double) (0.5F + f2), 1.0D, (double) (f2 * 2.0F));
			}
			if(k == 1) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.30000001192092896D, (double) (1.0F - f2 * 2.0F), (double) (0.5F + f2), 1.0D, 1.0D);
			}
			f2 = 0.0625F;
			if(k == 2) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.5D, 0.0D, (double) (0.5F + f2), (double) (1.0F - f2), 1.0D);
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
	}
	
	/*private void renderItemBlockSmelteryFurnace(BlockSmelteryFurnace block, int metadata, int modelID, RenderBlocks renderer) {
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
	}*/

	@Override
	public int getRenderId() {
		return 100;
	}
}
