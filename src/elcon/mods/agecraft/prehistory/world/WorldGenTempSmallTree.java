package elcon.mods.agecraft.prehistory.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.core.tileentities.TileEntityMetadata;

public class WorldGenTempSmallTree extends WorldGenerator {

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if(world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP)) {
			world.setBlock(x, y - 1, z, Block.grass.blockID, 0, 0);
			
			int treeType = random.nextInt(4);
			TileEntityMetadata tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y, z, Trees.log.blockID, 0, 0);
			world.setBlockTileEntity(x, y, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y + 1, z, Trees.log.blockID, 0, 0);
			world.setBlockTileEntity(x, y + 1, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y + 2, z, Trees.log.blockID, 0, 0);
			world.setBlockTileEntity(x, y + 2, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y + 3, z, Trees.leaves.blockID, 0, 0);
			world.setBlockTileEntity(x, y + 3, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x - 1, y + 2, z, Trees.leaves.blockID, 0, 0);
			world.setBlockTileEntity(x - 1, y + 2, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x + 1, y + 2, z, Trees.leaves.blockID, 0, 0);
			world.setBlockTileEntity(x + 1, y + 2, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y + 2, z - 1, Trees.leaves.blockID, 0, 0);
			world.setBlockTileEntity(x, y + 2, z - 1, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y + 2, z + 1, Trees.leaves.blockID, 0, 0);
			world.setBlockTileEntity(x, y + 2, z + 1, tile);
		}
		return true;
	}
}
