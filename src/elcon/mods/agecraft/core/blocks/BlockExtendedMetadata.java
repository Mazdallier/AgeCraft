package elcon.mods.agecraft.core.blocks;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class BlockExtendedMetadata extends BlockContainer implements IBlockExtendedMetadata {

	protected BlockExtendedMetadata(int i, Material material) {
		super(i, material);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMetadata();
	}

	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
		return stack.getItemDamage();
	}

	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta;
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		return breakBlock(this, player, world, x, y, z);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance, int fortune) {
		TileEntityMetadata tile = (TileEntityMetadata) world.getBlockTileEntity(x, y, z);
		if(tile != null && !tile.droppedBlock) {
			super.dropBlockAsItemWithChance(world, x, y, z, tile.getTileMetadata(), chance, fortune);
		}
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int count = quantityDropped(metadata, fortune, world.rand);
		for(int i = 0; i < count; i++) {
			int id = idDropped(metadata, world.rand, fortune);
			if(id > 0) {
				ret.add(new ItemStack(id, 1, getDroppedMetadata(world, x, y, z, metadata, fortune)));
			}
		}
		return ret;
	}

	public boolean breakBlock(IBlockExtendedMetadata block, EntityPlayer player, World world, int x, int y, int z) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		
		Block block2 = (Block) block;
		TileEntityMetadata tile = (TileEntityMetadata) world.getBlockTileEntity(x, y, z);
		if(tile != null && !tile.droppedBlock) {
			drops = block2.getBlockDropped(world, x, y, z, getMetadata(world, x, y, z), EnchantmentHelper.getFortuneModifier(player));
		}
		boolean hasBeenBroken = world.setBlockToAir(x, y, z);
		if(hasBeenBroken && !world.isRemote && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for(ItemStack drop : drops) {
				block.dropAsStack(world, x, y, z, drop);
			}
			tile.droppedBlock = true;
		}
		return hasBeenBroken;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return getMetadata(world, x, y, z);
	}

	@Override
	public int getMetadata(IBlockAccess blockAccess, int x, int y, int z) {
		if(Block.blocksList[blockAccess.getBlockId(x, y, z)] instanceof IBlockExtendedMetadata) {
			return ((TileEntityMetadata) blockAccess.getBlockTileEntity(x, y, z)).getTileMetadata();
		}
		return blockAccess.getBlockMetadata(x, y, z);
	}

	@Override
	public void setMetadata(World world, int x, int y, int z, int meta) {
		if(Block.blocksList[world.getBlockId(x, y, z)] instanceof IBlockExtendedMetadata) {
			((TileEntityMetadata) world.getBlockTileEntity(x, y, z)).setTileMetadata(meta);
		}
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}

	@Override
	public void dropAsStack(World world, int x, int y, int z, ItemStack stack) {
		dropBlockAsItem_do(world, x, y, z, stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}
}
