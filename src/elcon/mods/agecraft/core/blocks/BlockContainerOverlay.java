package elcon.mods.agecraft.core.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainerOverlay extends BlockOverlay implements ITileEntityProvider {

	public BlockContainerOverlay(int id, Material material) {
		super(id, material);
		isBlockContainer = true;
	}
	
	@Override
	public abstract TileEntity createNewTileEntity(World world);
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int i, int meta) {
		super.breakBlock(world, x, y, z, i, meta);
		world.removeBlockTileEntity(x, y, z);
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int blockID, int eventID) {
		super.onBlockEventReceived(world, x, y, z, blockID, eventID);
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(blockID, eventID) : false;
	}
}
