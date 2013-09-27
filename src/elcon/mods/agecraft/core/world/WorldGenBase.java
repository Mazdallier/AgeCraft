package elcon.mods.agecraft.core.world;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import elcon.mods.core.util.BlockPosition;

public abstract class WorldGenBase extends WorldGenerator {

	public boolean notifyBlocks;
	public Random random;

	public WorldGenBase() {
		this(false);
	}

	public WorldGenBase(boolean b) {
		notifyBlocks = b;
		random = new Random();
	}

	/**
	 * Sets the block and tile entity without metadata in the world, notifying
	 * neighbors if enabled.
	 */
	protected void setBlockAndTileEntity(World world, int x, int y, int z, int id, TileEntity tile) {
		setBlockAndMetadataAndTileEntity(world, x, y, z, id, 0, tile);
	}

	/**
	 * Sets the block and tile entity in the world, notifying neighbors if
	 * enabled.
	 */
	protected void setBlockAndMetadataAndTileEntity(World world, int x, int y, int z, int id, int data, TileEntity tile) {
		if(notifyBlocks) {
			world.setBlock(x, y, z, id, data, 1);
			world.setBlockTileEntity(x, y, z, tile);
		} else {
			world.setBlock(x, y, z, id, data, 0);
			world.setBlockTileEntity(x, y, z, tile);
		}
	}

	/**
	 * Generates a cube, if replace is enabled it overrides existing blocks
	 */
	public void generateCube(World world, int x, int y, int z, int length, int height, int width, int blockID, int meta, boolean replace) {
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < height; j++) {
				for(int k = 0; k < width; k++) {
					int oldID = world.getBlockId(x + i, y + j, z + k);
					if(oldID == 0 || (oldID != 0 && replace)) {
						setBlockAndMetadata(world, x + i, y + j, z + k, blockID, meta);
					}
				}
			}
		}
	}
	
	/**
	 * Generates a vertical cylinder, if replace is enabled it overrides existing blocks
	 */
	public void generateCylinderVertical(World world, int x, int y, int z, int radius, int height, int blockID, int meta, boolean replace) {
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < height; j++) {
				for(int k = 0; k < radius; k++) {
					if(BlockPosition.distance(new BlockPosition(x + i, y + j, z + k), new BlockPosition(x, y, z)) <= radius + 0.01D) {
						int oldID = world.getBlockId(x + i, y + j, z + k);
						if(oldID == 0 || (oldID != 0 && replace)) {
							setBlockAndMetadata(world, x + i, y + j, z + k, blockID, meta);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Generates a horizontal cylinder along the X-axis, if replace is enabled it overrides existing blocks
	 */
	public void generateCylinderHorizontalX(World world, int x, int y, int z, int radius, int height, int blockID, int meta, boolean replace) {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < radius; j++) {
				for(int k = 0; k < radius; k++) {
					if(BlockPosition.distance(new BlockPosition(x + i, y + j, z + k), new BlockPosition(x, y, z)) <= radius + 0.01D) {
						int oldID = world.getBlockId(x + i, y + j, z + k);
						if(oldID == 0 || (oldID != 0 && replace)) {
							setBlockAndMetadata(world, x + i, y + j, z + k, blockID, meta);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Generates a horizontal cylinder along the Z-axis, if replace is enabled it overrides existing blocks
	 */
	public void generateCylinderHorizontalZ(World world, int x, int y, int z, int radius, int height, int blockID, int meta, boolean replace) {
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				for(int k = 0; k < height; k++) {
					if(BlockPosition.distance(new BlockPosition(x + i, y + j, z + k), new BlockPosition(x, y, z)) <= radius + 0.01D) {
						int oldID = world.getBlockId(x + i, y + j, z + k);
						if(oldID == 0 || (oldID != 0 && replace)) {
							setBlockAndMetadata(world, x + i, y + j, z + k, blockID, meta);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Generates a vertical circle, if replace is enabled it overrides existing blocks
	 */
	public void generateCircleVertical(World world, int x, int y, int z, int radius, int blockID, int meta, boolean replace) {
		generateCylinderVertical(world, x, y, z, radius, 1, blockID, meta, replace);
	}
	
	/**
	 * Generates a horizontal circle along the X-axis, if replace is enabled it overrides existing blocks
	 */
	public void generateCircleHorizontalX(World world, int x, int y, int z, int radius, int blockID, int meta, boolean replace) {
		generateCylinderHorizontalX(world, x, y, z, radius, 1, blockID, meta, replace);
	}
	
	/**
	 * Generates a horizontal circle along the Z-axis, if replace is enabled it overrides existing blocks
	 */
	public void generateCircleHorizontalZ(World world, int x, int y, int z, int radius, int blockID, int meta, boolean replace) {
		generateCylinderHorizontalZ(world, x, y, z, radius, 1, blockID, meta, replace);
	}

	/**
	 * Generates a sphere with different radii, if replace is enabled it overrides existing blocks
	 */
	public void generateSphereCustom(World world, int x, int y, int z, int radiusX, int radiusY, int radiusZ, int blockID, int meta, boolean replace) {
		for(int i = 0; i < radiusX; i++) {
			for(int j = 0; j < radiusY; j++) {
				for(int k = 0; k < radiusZ; k++) {
					if(BlockPosition.distance(new BlockPosition(x + i, y + j, z + k), new BlockPosition(x, y, z)) <= radiusX + 0.01D 
							&& BlockPosition.distance(new BlockPosition(x + i, y + j, z + k), new BlockPosition(x, y, z)) <= radiusY + 0.01D 
							&& BlockPosition.distance(new BlockPosition(x + i, y + j, z + k), new BlockPosition(x, y, z)) <= radiusZ + 0.01D) {
						int oldID = world.getBlockId(x + i, y + j, z + k);
						if(oldID == 0 || (oldID != 0 && replace)) {
							setBlockAndMetadata(world, x + i, y + j, z + k, blockID, meta);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Generates a sphere with the same different radius, if replace is enabled it overrides existing blocks
	 */
	public void generateSphere(World world, int x, int y, int z, int radius, int blockID, int meta, boolean replace) {
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				for(int k = 0; k < radius; k++) {
					if(BlockPosition.distance(new BlockPosition(x + i, y + j, z + k), new BlockPosition(x, y, z)) <= radius + 0.01D) {
						int oldID = world.getBlockId(x + i, y + j, z + k);
						if(oldID == 0 || (oldID != 0 && replace)) {
							setBlockAndMetadata(world, x + i, y + j, z + k, blockID, meta);
						}
					}
				}
			}
		}
	}
}
