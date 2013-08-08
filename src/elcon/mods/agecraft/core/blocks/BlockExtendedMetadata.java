package elcon.mods.agecraft.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class BlockExtendedMetadata extends BlockContainer {

	protected BlockExtendedMetadata(int i, Material material) {
		super(i, material);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMetadata();
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float xx, float yy, float zz, int metadata) {
		TileEntityMetadata tile = (TileEntityMetadata) world.getBlockTileEntity(x, y, z);
		tile.metadata = metadata;
		return 0;
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return getMetadata(world, x, y, z);
	}
	
	public int getMetadata(World world, int x, int y, int z) {
		if(Block.blocksList[world.getBlockId(x, y, z)] instanceof BlockExtendedMetadata) {
			return ((TileEntityMetadata) world.getBlockTileEntity(x, y, z)).metadata;
		}
		return world.getBlockMetadata(x, y, z);
	}
}
