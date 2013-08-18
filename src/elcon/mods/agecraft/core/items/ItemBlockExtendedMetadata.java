package elcon.mods.agecraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.blocks.IBlockExtendedMetadata;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class ItemBlockExtendedMetadata extends ItemBlock {

	public ItemBlockExtendedMetadata(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int meta) {
		return 0;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		Block block = Block.blocksList[getBlockID()];
		if(!(block instanceof IBlockExtendedMetadata)) {
			return false;
		}
		int placedMeta = ((IBlockExtendedMetadata) block).getPlacedMetadata(player, stack, world, x, y, z, side, hitX, hitY, hitZ);
		if(!world.setBlock(x, y, z, getBlockID(), meta, 2)) {
			return false;
		}
		if(world.getBlockId(x, y, z) == getBlockID()) {
			TileEntityMetadata tile = ((TileEntityMetadata) world.getBlockTileEntity(x, y, z));
			if(tile != null) {
				tile.setTileMetadata(placedMeta);
			}
			block.onBlockPlacedBy(world, x, y, z, player, stack);
			block.onPostBlockPlaced(world, x, y, z, meta);
		}
		return true;
	}
}
