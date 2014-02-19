package org.agecraft.core.blocks.metal;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.MetalRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockMetalTrapdoor extends BlockExtendedMetadata {

	public static String[] types = new String[]{"standard", "solid"};
	
	public BlockMetalTrapdoor() {
		super(Material.iron);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(ACCreativeTabs.metals);
		
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 31)) / 32).blockHardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 31)) / 32).blockResistance / 5.0F;
	}

	@Override
	public boolean shouldDropItems(World world, int x, int y, int z, int meta, EntityPlayer player, ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() instanceof ItemTool) {
				return ((ItemTool) stack.getItem()).canHarvestBlock(stack, this, meta);
			}
		}
		return false;
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.instance.get((stack.getItemDamage() - (stack.getItemDamage() & 31)) / 32).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "metals.trapdoor." + types[(stack.getItemDamage() & 16) / 16];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		float f = 0.1875F;
		if((meta & 8) != 0) {
			setBlockBounds(0.0F, 1.0F - f, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
		}
		if((meta & 4) != 0) {
			if((meta & 3) == 0) {
				setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
			}
			if((meta & 3) == 1) {
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
			}
			if((meta & 3) == 2) {
				setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			if((meta & 3) == 3) {
				setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
			}
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		float f = 0.1875F;
		setBlockBounds(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xx, float yy, float zz) {
		setMetadata(world, x, y, z, getMetadata(world, x, y, z) ^ 4);
		world.markBlockForUpdate(x, y, z);
		if(world.isRemote) {
			world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
		}
		return true;
	}

	public void onPoweredBlockChange(World world, int x, int y, int z, boolean powered) {
		int meta = getMetadata(world, x, y, z);
		boolean isOpen = (meta & 4) != 0;
		if(isOpen != powered) {
			setMetadata(world, x, y, z, meta ^ 4);
			world.markBlockForUpdate(x, y, z);
			world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!world.isRemote) {
			int meta = getMetadata(world, x, y, z);
			int oldX = x;
			int oldZ = z;

			if((meta & 3) == 0) {
				oldZ++;
			}
			if((meta & 3) == 1) {
				oldZ--;
			}
			if((meta & 3) == 2) {
				oldX++;
			}
			if((meta & 3) == 3) {
				oldX--;
			}

			if(!(isValidSupportBlock(world.getBlock(oldX, y, oldZ)) || world.isSideSolid(oldX, y, oldZ, ForgeDirection.getOrientation((meta & 3) + 2)))) {
				world.setBlockToAir(x, y, z);
				dropBlockAsItem(world, x, y, z, meta, 0);
			}
			boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z);
			if(powered || block != null && block.canProvidePower()) {
				onPoweredBlockChange(world, x, y, z, powered);
			}
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.collisionRayTrace(world, x, y, z, startVec, endVec);
	}

	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return (meta - (meta & 15));
	}

	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		int meta = 0;
		if(side == 2) {
			meta = 0;
		}
		if(side == 3) {
			meta = 1;
		}
		if(side == 4) {
			meta = 2;
		}
		if(side == 5) {
			meta = 3;
		}
		if(side != 1 && side != 0 && yy > 0.5F) {
			meta |= 8;
		}
		return meta | stack.getItemDamage();
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		if(side == 0 || side == 1) {
			return false;
		} else {
			if(side == 2) {
				z++;
			}
			if(side == 3) {
				z--;
			}
			if(side == 4) {
				x++;
			}
			if(side == 5) {
				x--;
			}
			return isValidSupportBlock(world.getBlock(x, y, z)) || world.isSideSolid(x, y, z, ForgeDirection.UP);
		}
	}

	public boolean isValidSupportBlock(Block block) {
		if(block == null) {
			return false;
		} else {
			return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() || block == Blocks.glowstone || block instanceof BlockSlab || block instanceof BlockStairs;
		}
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
		return (getMetadata(blockAccess, x, y, z) & 4) != 0;
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
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return MetalRegistry.instance.get((meta - (meta & 31)) / 32).metalColor;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 31)) / 32).metalColor;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return ResourcesCore.trapdoorMetalIcons[(meta & 16) / 16];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null && MetalRegistry.instance.get(i).hasDoor) {
				list.add(new ItemStack(item, 1, (i * 32)));
				list.add(new ItemStack(item, 1, (i * 32) | 16));
			}
		}
	}
}
