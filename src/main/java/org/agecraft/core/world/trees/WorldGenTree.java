package org.agecraft.core.world.trees;

import java.util.Random;

import org.agecraft.core.Trees;
import org.agecraft.dna.storage.DNAStorage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import elcon.mods.core.tileentities.TileEntityMetadata;
import elcon.mods.core.world.WorldGenBase;

public abstract class WorldGenTree extends WorldGenBase {

	public static ForgeDirection[] sideDirections = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST};
	
	private World world;
	private int startX;
	private int startY;
	private int startZ;

	public WorldGenTree() {
		super(true, 3);
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		return false;
	}

	/*public boolean generateSmallTree(World world, int x, int y, int z, DNAStorage dna) {
		this.random = world.rand;
		this.world = world;
		startX = x;
		startY = y;
		startZ = z;

		int woodType = dna.getGene(0, 0).getActive();
		int leafType = dna.getGene(0, 1).getActive();
		int trunkSize = dna.getGene(3, 0).getActive();
		int leafSize = Math.max((dna.getGene(3, 1).getActive() / 4) * 3, trunkSize + 2);
		int halfLeafSize = leafSize / 2;
		int height = (dna.getGene(3, 2).getActive() / 4) * 3;
		int generationType = dna.getGene(3, 3).getActive();
		
		int centerY = y + height - halfLeafSize;
		
		//TODO: add required size check
		
		generateSmallTreeTrunk(trunkSize, height, woodType);
		
		for(int i = 0; i < trunkSize; i++) {
			for(int j = 0; j < trunkSize; j++) {
				generateSphere(world, x + i, centerY, z + j, halfLeafSize + 0.5D, Trees.leavesDNA.blockID, 0, false, TileEntityDNATree.class, dna.id, dna);
			}
		}
		
		if(leafSize >= 8) {
			generateSmallTreeBranches(centerY - 2, centerY + 2, leafSize / 4, leafSize / 2, leafSize / 4, trunkSize, woodType);
		}
		return true;
	}*/

	public boolean generateTree(World world, int x, int y, int z, DNAStorage dna) {
		this.random = world.rand;
		this.world = world;
		startX = x;
		startY = y;
		startZ = z;

		int woodType = dna.getGene(0, 0).getActive();
		int leafType = dna.getGene(0, 1).getActive();
		int trunkSize = dna.getGene(3, 0).getActive();
		int leafSize = dna.getGene(3, 1).getActive();
		int height = dna.getGene(3, 2).getActive();
		int generationType = dna.getGene(3, 3).getActive();
		
		//TODO: add required size check
		
		generateTreeTrunk(trunkSize, height, woodType);
		
		return true;
	}

	public void generateTreeTrunk(int trunkSize, int height, int woodType) {
		generateTreeTrunk(trunkSize, height, woodType, 0.0F);
	}

	public void generateTreeTrunk(int trunkSize, int height, int woodType, float vines) {
		for(int x = startX; x < startX + trunkSize; x++) {
			for(int z = startZ; z < startZ + trunkSize; z++) {
				for(int y = startY; y < startY + height; y++) {
					setBlockAndTileEntity(world, x, y, z, Trees.wood.blockID, new TileEntityMetadata(woodType * 4));
					if(random.nextFloat() < vines) {
						setBlockAndMetadata(world, x - 1, y, z, Block.vine.blockID, 0);
					}
					if(random.nextFloat() < vines) {
						setBlockAndMetadata(world, x + 1, y, z, Block.vine.blockID, 0);
					}
					if(random.nextFloat() < vines) {
						setBlockAndMetadata(world, x, y, z - 1, Block.vine.blockID, 0);
					}
					if(random.nextFloat() < vines) {
						setBlockAndMetadata(world, x, y, z + 1, Block.vine.blockID, 0);
					}
				}
			}
		}
	}
	
	public void generateTreeBranches(int startY, int endY, int maxBranchCount, int branchLength, int branchHeight, int trunkSize, int woodType) {
		int branchCount = 0;
		int chance = (endY - startY) / maxBranchCount;
		int halfTrunkSize = trunkSize == 1 ? 0 : trunkSize / 2;
		System.out.println("generating branches " + chance);
		for(int i = startY; i < endY; i++) {
			if(random.nextInt(chance) == 0) {
				ForgeDirection direction = sideDirections[i >= sideDirections.length ? random.nextInt(sideDirections.length) : i];
				generateTreeBranch(world, startX + halfTrunkSize + direction.offsetX, i, startZ + halfTrunkSize + direction.offsetZ, direction, branchLength, branchHeight, woodType);
				branchCount++;
				if(branchCount >= maxBranchCount) {
					return;
				}
			}
		}
	}
	
	public void generateTreeBranch(World world, int x, int y, int z, ForgeDirection direction, int branchLength, int branchHeight, int woodType) {
		if(direction.offsetY == 0) {
			int lengthPerHeight = branchLength / branchHeight;
			int additionalBranchLength = branchLength - (branchHeight * lengthPerHeight);
			for(int i = 0; i < branchHeight; i++) {
				int length = lengthPerHeight + (i == branchHeight - 1 ? additionalBranchLength : 0);
				for(int j = 0; j < length; j++) {
					int xx = x + ((j + lengthPerHeight * i) * direction.offsetX);
					int yy = y + i;
					int zz = z + ((j + lengthPerHeight * i) * direction.offsetZ);
					if(world.getBlockMaterial(xx, yy, zz) == Material.leaves) {
						setBlockAndTileEntity(world, xx, yy, zz, Trees.wood.blockID, new TileEntityMetadata(woodType * 4));
					}
				}
			}
		}
	}
	
	/*public void generateSmallTreeTrunk(int trunkSize, int height, int woodType) {
		for(int x = startX; x < startX + trunkSize; x++) {
			for(int z = startZ; z < startZ + trunkSize; z++) {
				for(int y = startY; y < startY + height; y++) {
					setBlockAndTileEntity(world, x, y, z, Trees.log.blockID, new TileEntityMetadata(woodType));
				}
			}
		}
	}
	
	public void generateSmallTreeBranches(int startY, int endY, int maxBranchCount, int branchLength, int branchHeight, int trunkSize, int woodType) {
		int branchCount = 0;
		int chance = (endY - startY) / maxBranchCount;
		int halfTrunkSize = trunkSize == 1 ? 0 : trunkSize / 2;
		for(int i = startY; i < endY; i++) {
			if(random.nextInt(chance) == 0) {
				ForgeDirection direction = sideDirections[i >= sideDirections.length ? random.nextInt(sideDirections.length) : i];
				generateTreeBranch(world, startX + halfTrunkSize + direction.offsetX, i, startX + halfTrunkSize + direction.offsetZ, direction, branchLength, branchHeight, woodType);
				branchCount++;
				if(branchCount >= maxBranchCount) {
					return;
				}
			}
		}
	}
	
	public void generateSmallTreeBranch(World world, int x, int y, int z, ForgeDirection direction, int branchLength, int branchHeight, int woodType) {
		if(direction.offsetY != 0) {
			int lengthPerHeight = branchLength / branchHeight;
			int additionalBranchLength = branchLength - (branchHeight * lengthPerHeight);
			for(int i = 0; i < branchHeight; i++) {
				int length = lengthPerHeight + (i == branchHeight - 1 ? additionalBranchLength : 0);
				for(int j = 0; j < length; j++) {
					int xx = x + ((j + lengthPerHeight * i) * direction.offsetX);
					int yy = y + i;
					int zz = z + ((j + lengthPerHeight * i) * direction.offsetZ);
					if(world.getBlockMaterial(xx, yy, zz) == Material.leaves) {
						setBlockAndTileEntity(world, xx, yy, zz, Trees.log.blockID, new TileEntityMetadata(woodType));
					}
				}
			}
		}
	}*/
}
