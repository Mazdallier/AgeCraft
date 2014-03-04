package org.agecraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.agecraft.core.blocks.crafting.BlockAnvil;
import org.agecraft.core.tileentities.TileEntityAnvil;

import elcon.mods.elconqore.items.ItemBlockName;

public class ItemBlockAnvil extends ItemBlockName {

	public ItemBlockAnvil(Block block) {
		super(block);
		setMaxDamage(0);
        setHasSubtypes(true);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ((BlockAnvil) field_150939_a).getLocalizedName(stack);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ((BlockAnvil) field_150939_a).getUnlocalizedName(stack);
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if(!world.setBlock(x, y, z, field_150939_a, 0, 3)) {
			return false;
		}
		if(world.getBlock(x, y, z) == field_150939_a) {
			TileEntityAnvil tile = (TileEntityAnvil) world.getTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityAnvil();
				world.setTileEntity(x, y, z, tile);
			}
			tile.type = (byte) ((meta - (meta & 3)) / 4);
			tile.damage = (byte) (meta & 3);
			field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			field_150939_a.onPostBlockPlaced(world, x, y, z, meta);
		}
		return true;
	}
}
