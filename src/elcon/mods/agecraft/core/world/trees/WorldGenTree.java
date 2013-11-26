package elcon.mods.agecraft.core.world.trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.tileentities.TileEntityDNATree;
import elcon.mods.agecraft.dna.storage.DNAStorage;
import elcon.mods.core.tileentities.TileEntityMetadata;
import elcon.mods.core.world.WorldGenBase;

public abstract class WorldGenTree extends WorldGenBase {

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

	public boolean generateSmallTree(World world, int x, int y, int z, DNAStorage dna) {
		this.random = world.rand;
		this.world = world;
		startX = x;
		startY = y;
		startZ = z;

		int woodType = dna.getGene(0, 0).getActive();
		int leaveType = dna.getGene(0, 1).getActive();
		int trunkSize = dna.getGene(3, 0).getActive() + 1;
		int leaveSize = Math.max(((dna.getGene(3, 1).getActive() + 1) / 4) * 3, trunkSize + 2);
		int halfLeaveSize = leaveSize / 2;
		int height = ((dna.getGene(3, 2).getActive() + 1) / 4) * 3;
		int generationType = dna.getGene(3, 3).getActive();

		generateSmallTreeTrunk(trunkSize, height, woodType);
		
		for(int i = 0; i < trunkSize; i++) {
			for(int j = 0; j < trunkSize; j++) {
				generateSphere(world, x + i, y + height - halfLeaveSize, z + j, halfLeaveSize + 0.5D, Trees.leavesDNA.blockID, 0, false, TileEntityDNATree.class, dna.id, dna);
				//generateSphere(world, x + (trunkSize / 2) - i, y + height - (leaveSize / 2) + 2, z + (trunkSize / 2) - j, leaveSize / 2, Trees.leavesDNA.blockID, 0, false, TileEntityDNATree.class, dna.id, dna);
			}
		}
		return true;
	}

	public boolean generateTree(World world, int x, int y, int z, DNAStorage dna) {
		this.random = world.rand;
		this.world = world;
		startX = x;
		startY = y;
		startZ = z;

		int woodType = dna.getGene(0, 0).getActive();
		int leaveType = dna.getGene(0, 1).getActive();
		int trunkSize = dna.getGene(3, 0).getActive() + 1;
		int leaveSize = dna.getGene(3, 1).getActive() + 1;
		int height = dna.getGene(3, 2).getActive() + 1;
		int generationType = dna.getGene(3, 3).getActive();
		
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
	
	public void generateSmallTreeTrunk(int trunkSize, int height, int woodType) {
		for(int x = startX; x < startX + trunkSize; x++) {
			for(int z = startZ; z < startZ + trunkSize; z++) {
				for(int y = startY; y < startY + height; y++) {
					setBlockAndTileEntity(world, x, y, z, Trees.log.blockID, new TileEntityMetadata(woodType));
				}
			}
		}
	}
}
