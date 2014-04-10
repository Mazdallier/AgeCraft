package org.agecraft.prehistory.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.Trees;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryCampfire;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedContainer;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockPrehistoryCampfire extends BlockExtendedContainer { // implements IWailaBlock {

	@SideOnly(Side.CLIENT)
	private IIcon icon;

	public BlockPrehistoryCampfire() {
		super(Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
	}

	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "tile.campfire.name";
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPrehistoryCampfire();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityPrehistoryCampfire.class;
	}

	@Override
	public int getLightValue(IBlockAccess blockAccess, int x, int y, int z) {
		if(blockAccess.getBlock(x, y, z) == this) {
			TileEntityPrehistoryCampfire tile = (TileEntityPrehistoryCampfire) getTileEntity(blockAccess, x, y, z);
			return tile.isBurning() ? 15 : 0;
		}
		return super.getLightValue(blockAccess, x, y, z);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y - 1, z);
		if(block != null && block != this && world.getBlock(x, y - 1, z).isOpaqueCube()) {
			return true;
		}
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, 0));
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem(world, x, y, z, new ItemStack(this, 1, 0));
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(!world.isRemote) {
			TileEntityPrehistoryCampfire tile = (TileEntityPrehistoryCampfire) getTileEntity(world, x, y, z);
			if(tile.isBurning()) {
				entity.setFire(8);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if(!world.isRemote) {
			TileEntityPrehistoryCampfire tile = (TileEntityPrehistoryCampfire) getTileEntity(world, x, y, z);
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				return;
			}
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("Logs")) {
				NBTTagList list = nbt.getTagList("Logs", 10);
				for(int i = 0; i < list.tagCount(); i++) {
					ItemStack logStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) list.getCompoundTagAt(i));
					tile.addLogs(logStack);
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			TileEntityPrehistoryCampfire tile = (TileEntityPrehistoryCampfire) getTileEntity(world, x, y, z);
			return tile.onBlockActivated(player, player.rotationYaw, player.capabilities.isCreativeMode, player.getCurrentEquippedItem());
		}
		return true;
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return null;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		TileEntityPrehistoryCampfire tile = (TileEntityPrehistoryCampfire) getTileEntity(world, x, y, z);
		if(tile != null) {
			for(int i = 0; i < tile.frameStage; i++) {
				list.add(new ItemStack(Trees.stick, 1, tile.frameTypes[i]));
			}
			for(int i = 0; i < tile.logs.length; i++) {
				if(tile.logs[i] > 0) {
					list.add(new ItemStack(Trees.log, tile.logs[i], i));
				}
			}
		}
		return list;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 201;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/campfire");
	}

	/*
	 * @Override public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) { return null; }
	 * 
	 * @Override public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) { return currenttip; }
	 * 
	 * @Override public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) { TileEntityPrehistoryCampfire tile =
	 * (TileEntityPrehistoryCampfire) accessor.getTileEntity(); if(tile.logCount > 0) { currenttip.add(Integer.toString(tile.logCount) + " " + LanguageManager.getLocalization("trees.logs")); }
	 * if(tile.isCooking() && tile.spitStack != null) { currenttip.add(tile.spitStack.getDisplayName()); } return currenttip; }
	 */
}
