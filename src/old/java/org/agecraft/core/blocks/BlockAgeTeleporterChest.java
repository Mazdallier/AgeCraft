package org.agecraft.core.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.AgeCraft;
import org.agecraft.core.AgeCraftCore;
import org.agecraft.core.tileentities.TileEntityAgeTeleporterChest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockAgeTeleporterChest extends BlockContainer {

	private Random random = new Random();

	public BlockAgeTeleporterChest() {
		super(Material.iron);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setResistance(6000000.0F);
		setLightLevel(0.5F);
		setCreativeTab(ACCreativeTabs.ageCraft);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.ageTeleporterChest.name";
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
		return 99;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity) {
		int var6 = world.getBlockId(x, y, z - 1);
		int var7 = world.getBlockId(x, y, z + 1);
		int var8 = world.getBlockId(x - 1, y, z);
		int var9 = world.getBlockId(x + 1, y, z);
		byte var10 = 0;
		int var11 = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if(var11 == 0) {
			var10 = 2;
		}

		if(var11 == 1) {
			var10 = 5;
		}

		if(var11 == 2) {
			var10 = 3;
		}

		if(var11 == 3) {
			var10 = 4;
		}

		if(var6 != blockID && var7 != blockID && var8 != blockID && var9 != blockID) {
			world.setBlockMetadataWithNotify(x, y, z, var10, 1);
		} else {
			if((var6 == blockID || var7 == blockID) && (var10 == 4 || var10 == 5)) {
				if(var6 == blockID) {
					world.setBlockMetadataWithNotify(x, y, z - 1, var10, 1);
				} else {
					world.setBlockMetadataWithNotify(x, y, z + 1, var10, 1);
				}

				world.setBlockMetadataWithNotify(x, y, z, var10, 1);
			}

			if((var8 == blockID || var9 == blockID) && (var10 == 2 || var10 == 3)) {
				if(var8 == blockID) {
					world.setBlockMetadataWithNotify(x - 1, y, z, var10, 1);
				} else {
					world.setBlockMetadataWithNotify(x + 1, y, z, var10, 1);
				}

				world.setBlockMetadataWithNotify(x, y, z, var10, 1);
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityAgeTeleporterChest var7 = (TileEntityAgeTeleporterChest) world.getBlockTileEntity(x, y, z);

		if(var7 != null) {
			for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
				ItemStack var9 = var7.getStackInSlot(var8);

				if(var9 != null) {
					float var10 = random.nextFloat() * 0.8F + 0.1F;
					float var11 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem var14;

					for(float var12 = random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; world.spawnEntityInWorld(var14)) {
						int var13 = random.nextInt(21) + 10;

						if(var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}

						var9.stackSize -= var13;
						var14 = new EntityItem(world, (double) ((float) x + var10), (double) ((float) y + var11), (double) ((float) z + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
						float var15 = 0.05F;
						var14.motionX = (double) ((float) random.nextGaussian() * var15);
						var14.motionY = (double) ((float) random.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double) ((float) random.nextGaussian() * var15);

						if(var9.hasTagCompound()) {
							var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
						}
					}
				}
			}
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		Object var10 = (TileEntityAgeTeleporterChest) par1World.getBlockTileEntity(par2, par3, par4);

		if(var10 == null) {
			return true;
		} else if(par1World.isBlockSolidOnSide(par2, par3 + 1, par4, DOWN)) {
			return true;
		} else {
			if(par1World.isRemote) {
				return true;
			} else {
				par5EntityPlayer.openGui(AgeCraft.instance, 10, par1World, par2, par3, par4);
				return true;
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAgeTeleporterChest();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = AgeCraftCore.ageTeleporterBlock.getIcon(0, 0);
	}
}
