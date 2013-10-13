package elcon.mods.agecraft.core.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.tileentities.TileEntityExtended;

public abstract class BlockExtendedContainer extends BlockContainer {

	public BlockExtendedContainer(int id, Material material) {
		super(id, material);
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
}
