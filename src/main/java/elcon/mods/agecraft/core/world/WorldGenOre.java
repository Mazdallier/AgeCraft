package elcon.mods.agecraft.core.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import elcon.mods.core.tileentities.TileEntityMetadata;

public class WorldGenOre extends WorldGenerator {

	private int minableBlockId;
	private int minableBlockMeta;
	private boolean extendedMetadata;
	private int numberOfBlocks;
	private int blockToReplace;
	
	public int oreGenPerChunk;
	public int oreGenMinY;
	public int oreGenMaxY;

	public WorldGenOre(int minableBlockId, int numberOfBlocks, int oreGenPerChunk, int oreGenMinY, int oreGenMaxY) {
		this(minableBlockId, numberOfBlocks, Block.stone.blockID, oreGenPerChunk, oreGenMinY, oreGenMaxY);
	}

	public WorldGenOre(int minableBlockId, int numberOfBlocks, int blockToReplace, int oreGenPerChunk, int oreGenMinY, int oreGenMaxY) {
		this.minableBlockId = minableBlockId;
		this.minableBlockMeta = 0;
		this.extendedMetadata = false;
		this.numberOfBlocks = numberOfBlocks;
		this.blockToReplace = blockToReplace;
		
		this.oreGenPerChunk = oreGenPerChunk;
		this.oreGenMinY = oreGenMinY;
		this.oreGenMaxY = oreGenMaxY;
	}

	public WorldGenOre(int minableBlockId, int minableBlockMeta, boolean extendedMetadata, int numberOfBlocks, int blockToReplace, int oreGenPerChunk, int oreGenMinY, int oreGenMaxY) {
		this(minableBlockId, numberOfBlocks, blockToReplace, oreGenPerChunk, oreGenMinY, oreGenMaxY);
		this.minableBlockMeta = minableBlockMeta;
		this.extendedMetadata = extendedMetadata;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		float f = rand.nextFloat() * (float) Math.PI;
		double minX = (double) ((float) (x + 8) + MathHelper.sin(f) * (float) numberOfBlocks / 8.0F);
		double maxX = (double) ((float) (x + 8) - MathHelper.sin(f) * (float) numberOfBlocks / 8.0F);
		double minY = (double) (y + rand.nextInt(3) - 2);
		double maxY = (double) (y + rand.nextInt(3) - 2);
		double minZ = (double) ((float) (z + 8) + MathHelper.cos(f) * (float) numberOfBlocks / 8.0F);
		double maxZ = (double) ((float) (z + 8) - MathHelper.cos(f) * (float) numberOfBlocks / 8.0F);
		
		for(int i = 0; i <= numberOfBlocks; ++i) {
			double d6 = minX + (maxX - minX) * (double) i / (double) numberOfBlocks;
			double d7 = minY + (maxY - minY) * (double) i / (double) numberOfBlocks;
			double d8 = minZ + (maxZ - minZ) * (double) i / (double) numberOfBlocks;
			double d9 = rand.nextDouble() * (double) numberOfBlocks / 16.0D;
			double d10 = (double) (MathHelper.sin((float) i * (float) Math.PI / (float) numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (double) (MathHelper.sin((float) i * (float) Math.PI / (float) numberOfBlocks) + 1.0F) * d9 + 1.0D;
			int startX = MathHelper.floor_double(d6 - d10 / 2.0D);
			int startY = MathHelper.floor_double(d7 - d11 / 2.0D);
			int startZ = MathHelper.floor_double(d8 - d10 / 2.0D);
			int endX = MathHelper.floor_double(d6 + d10 / 2.0D);
			int endY = MathHelper.floor_double(d7 + d11 / 2.0D);
			int endZ = MathHelper.floor_double(d8 + d10 / 2.0D);

			for(int xx = startX; xx <= endX; ++xx) {
				double d12 = ((double) xx + 0.5D - d6) / (d10 / 2.0D);
				if(d12 * d12 < 1.0D) {
					for(int yy = startY; yy <= endY; ++yy) {
						double d13 = ((double) yy + 0.5D - d7) / (d11 / 2.0D);
						if(d12 * d12 + d13 * d13 < 1.0D) {
							for(int zz = startZ; zz <= endZ; ++zz) {
								double d14 = ((double) zz + 0.5D - d8) / (d10 / 2.0D);
								Block block = Block.blocksList[world.getBlockId(xx, yy, zz)];
								if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && (block != null && block.isGenMineableReplaceable(world, xx, yy, zz, blockToReplace))) {
									if(extendedMetadata) {
										world.setBlock(xx, yy, zz, minableBlockId, 0, 2);
										TileEntity tile = Block.blocksList[minableBlockId].createTileEntity(world, 0);
										if(tile instanceof TileEntityMetadata) {
											((TileEntityMetadata) tile).setTileMetadata(minableBlockMeta);
											world.setBlockTileEntity(xx, yy, zz, tile);
										}
									} else {
										world.setBlock(xx, yy, zz, minableBlockId, minableBlockMeta, 2);
									}
								}	
							}
						}
					}
				}
			}
		}
		return true;
	}
}
