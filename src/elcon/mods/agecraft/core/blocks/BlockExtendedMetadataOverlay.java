package elcon.mods.agecraft.core.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class BlockExtendedMetadataOverlay extends BlockContainerOverlay implements IBlockExtendedMetadata {

	public BlockExtendedMetadataOverlay(int id, Material material) {
		super(id, material);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMetadata();
	}	

	@Override
	public int getPlacedMetadata(ItemStack stack, World world, int x, int y, int z, int side) {
		return stack.getItemDamage();
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int count = quantityDropped(metadata, fortune, world.rand);
		for(int i = 0; i < count; i++) {
			int id = idDropped(metadata, world.rand, fortune);
			if(id > 0) {
				ret.add(new ItemStack(id, 1, getMetadata(world, x, y, z)));
			}
		}
		return ret;
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return getMetadata(world, x, y, z);
	}
	
	@Override
	public int getMetadata(IBlockAccess blockAccess, int x, int y, int z) {
		if(Block.blocksList[blockAccess.getBlockId(x, y, z)] instanceof BlockExtendedMetadata) {
			return ((TileEntityMetadata) blockAccess.getBlockTileEntity(x, y, z)).getTileMetadata();
		}
		return blockAccess.getBlockMetadata(x, y, z);
	}
	
	@Override
	public void setMetadata(World world, int x, int y, int z, int meta) {
		if(Block.blocksList[world.getBlockId(x, y, z)] instanceof BlockExtendedMetadata) {
			((TileEntityMetadata) world.getBlockTileEntity(x, y, z)).setTileMetadata(meta);
		}
	}
}
