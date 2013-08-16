package elcon.mods.agecraft.core.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract interface IBlockExtendedMetadata {

	public int getPlacedMetadata(ItemStack stack, World world, int x, int y, int z, int side);
	
	public int getMetadata(IBlockAccess blockAccess, int x, int y, int z);
	
	public void setMetadata(World world, int x, int y, int z, int meta);
}
