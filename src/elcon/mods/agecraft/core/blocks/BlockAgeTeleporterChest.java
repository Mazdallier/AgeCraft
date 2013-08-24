package elcon.mods.agecraft.core.blocks;

import static net.minecraftforge.common.ForgeDirection.DOWN;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.agecraft.AgeCraft;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterChest;
import elcon.mods.agecraft.lang.LanguageManager;

public class BlockAgeTeleporterChest extends BlockContainer {

	private Random random = new Random();

	public BlockAgeTeleporterChest(int par1) {
		super(par1, Material.iron);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setResistance(6000000.0F);
		setLightValue(0.5F);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.ageTeleporterChest.name";
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 22;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving) {
		int var6 = par1World.getBlockId(par2, par3, par4 - 1);
		int var7 = par1World.getBlockId(par2, par3, par4 + 1);
		int var8 = par1World.getBlockId(par2 - 1, par3, par4);
		int var9 = par1World.getBlockId(par2 + 1, par3, par4);
		byte var10 = 0;
		int var11 = MathHelper.floor_double((double) (par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

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
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var10, 1);
		} else {
			if((var6 == blockID || var7 == blockID) && (var10 == 4 || var10 == 5)) {
				if(var6 == blockID) {
					par1World.setBlockMetadataWithNotify(par2, par3, par4 - 1, var10, 1);
				} else {
					par1World.setBlockMetadataWithNotify(par2, par3, par4 + 1, var10, 1);
				}

				par1World.setBlockMetadataWithNotify(par2, par3, par4, var10, 1);
			}

			if((var8 == blockID || var9 == blockID) && (var10 == 2 || var10 == 3)) {
				if(var8 == blockID) {
					par1World.setBlockMetadataWithNotify(par2 - 1, par3, par4, var10, 1);
				} else {
					par1World.setBlockMetadataWithNotify(par2 + 1, par3, par4, var10, 1);
				}

				par1World.setBlockMetadataWithNotify(par2, par3, par4, var10, 1);
			}
		}
	}

	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileEntityAgeTeleporterChest var7 = (TileEntityAgeTeleporterChest) par1World.getBlockTileEntity(par2, par3, par4);

		if(var7 != null) {
			for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
				ItemStack var9 = var7.getStackInSlot(var8);

				if(var9 != null) {
					float var10 = random.nextFloat() * 0.8F + 0.1F;
					float var11 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem var14;

					for(float var12 = random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; par1World.spawnEntityInWorld(var14)) {
						int var13 = random.nextInt(21) + 10;

						if(var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}

						var9.stackSize -= var13;
						var14 = new EntityItem(par1World, (double) ((float) par2 + var10), (double) ((float) par3 + var11), (double) ((float) par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
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

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

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
				par5EntityPlayer.openGui(AgeCraft.instance, 0, par1World, par2, par3, par4);
				return true;
			}
		}
	}

	public TileEntity createNewTileEntity(World par1World) {
		return new TileEntityAgeTeleporterChest();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		blockIcon = Block.chest.getIcon(0, 0);
	}
}
