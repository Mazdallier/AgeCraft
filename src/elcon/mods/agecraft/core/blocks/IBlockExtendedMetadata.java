package elcon.mods.agecraft.core.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract interface IBlockExtendedMetadata {

	public abstract String getLocalizedName(ItemStack stack);

	public abstract String getUnlocalizedName(ItemStack stack);
	
	public abstract int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune);
	
	public abstract int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz);
	
	public abstract int getMetadata(IBlockAccess blockAccess, int x, int y, int z);
	
	public abstract void setMetadata(World world, int x, int y, int z, int meta);
	
	public abstract void dropAsStack(World world, int x, int y, int z, ItemStack stack);
}
