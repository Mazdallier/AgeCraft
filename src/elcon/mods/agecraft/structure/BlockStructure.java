package elcon.mods.agecraft.structure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockStructure extends BlockContainer {

	public BlockStructure(int i, Material material) {
		super(i, material);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		ITileStructure tile = (ITileStructure) world.getBlockTileEntity(x, y, z);

		super.breakBlock(world, x, y, z, par5, par6);

		if((tile.isIntegratedIntoStructure()) && (!tile.isMaster())) {
			ITileStructure central = tile.getCentralTE();
			if(central != null)
				central.validateStructure();
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()) {
			return false;
		}
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(world.isRemote) {
			return true;
		}

		if(((tile instanceof ITileStructure)) && (!((ITileStructure) tile).isIntegratedIntoStructure())) {
			return false;
		}
		
		//tile.openGui();
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighbourBlockId) {
		if(world.isRemote) {
			return;
		}
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(!(tile instanceof ITileStructure)) {
			return;
		}
		((ITileStructure) tile).validateStructure();
	}

	public static enum EnumStructureState {
		VALID, INVALID, INDETERMINATE;
	}
}
