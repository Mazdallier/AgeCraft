package org.agecraft.prehistory.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.core.Trees;

import elcon.mods.elconqore.tileentities.TileEntityMetadata;

public class WorldGenTempSmallTree extends WorldGenerator {

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if(world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
			world.setBlock(x, y - 1, z, Blocks.grass, 0, 0);
			
			int treeType = random.nextInt(24);
			TileEntityMetadata tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y, z, Trees.log, 0, 0);
			world.setTileEntity(x, y, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y + 1, z, Trees.log, 0, 0);
			world.setTileEntity(x, y + 1, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType);
			world.setBlock(x, y + 2, z, Trees.log, 0, 0);
			world.setTileEntity(x, y + 2, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType * 4);
			world.setBlock(x, y + 3, z, Trees.leaves, 0, 0);
			world.setTileEntity(x, y + 3, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType * 4);
			world.setBlock(x - 1, y + 2, z, Trees.leaves, 0, 0);
			world.setTileEntity(x - 1, y + 2, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType * 4);
			world.setBlock(x + 1, y + 2, z, Trees.leaves, 0, 0);
			world.setTileEntity(x + 1, y + 2, z, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType * 4);
			world.setBlock(x, y + 2, z - 1, Trees.leaves, 0, 0);
			world.setTileEntity(x, y + 2, z - 1, tile);
			
			tile = new TileEntityMetadata();
			tile.setTileMetadata(treeType * 4);
			world.setBlock(x, y + 2, z + 1, Trees.leaves, 0, 0);
			world.setTileEntity(x, y + 2, z + 1, tile);
		}
		return true;
	}
}
