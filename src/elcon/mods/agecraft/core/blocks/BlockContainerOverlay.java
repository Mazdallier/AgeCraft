package elcon.mods.agecraft.core.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.tileentities.TileEntityExtended;

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
	
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		TileEntityExtended tile = (TileEntityExtended) world.getBlockTileEntity(x, y, z);
		if(tile != null && !tile.hasDropppedBlock) {
			drops = Block.blocksList[world.getBlockId(x, y, z)].getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), EnchantmentHelper.getFortuneModifier(player));
		}
		boolean hasBeenBroken = world.setBlockToAir(x, y, z);
		if(hasBeenBroken && !world.isRemote && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for(ItemStack drop : drops) {
				dropBlockAsItem_do(world, x, y, z, drop);
			}
			tile.hasDropppedBlock = true;
		}
		return hasBeenBroken;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
		TileEntityExtended tile = (TileEntityExtended) world.getBlockTileEntity(x, y, z);
		if(tile != null && tile.hasDropppedBlock) {
			super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
		}
	}
	
	public TileEntity getTileEntity(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntity tile = (TileEntity) blockAccess.getBlockTileEntity(x, y, z);
		if(tile == null) {
			if(blockAccess instanceof World) {
				tile = createNewTileEntity(((World) blockAccess));
				((World) blockAccess).setBlockTileEntity(x, y, z, tile);
			}
		}
		return tile;
	}
}
