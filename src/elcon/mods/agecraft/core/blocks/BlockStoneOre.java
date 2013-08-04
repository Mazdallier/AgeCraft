package elcon.mods.agecraft.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class BlockStoneOre extends BlockContainerOverlay {

	public BlockStoneOre(int i) {
		super(i, Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {		
		int i;
		for(i = 0; y > (i * BlockStoneLayered.LAYER_SIZE); i++) {}
		switch(i)  {
			case 1: return 0x878787;
			case 2: return 0xA7A7A7;
			case 3: return 0xC7C7C7;
			case 4: return 0xE7E7E7;
			default: return 0xFFFFFF;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return Block.stone.getIcon(0, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getOverlayTextureForBlock(int side, int metadata) {
		return null;
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
