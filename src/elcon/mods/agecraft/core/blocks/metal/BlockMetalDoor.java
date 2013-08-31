package elcon.mods.agecraft.core.blocks.metal;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.blocks.BlockExtendedMetadata;
import elcon.mods.agecraft.lang.LanguageManager;

public class BlockMetalDoor extends BlockExtendedMetadata {

	public static String[] types = new String[]{"standard", "solid", "double", "full"};
	
	public BlockMetalDoor(int id) {
		super(id, Material.iron);
		setStepSound(Block.soundMetalFootstep);
		setCreativeTab(ACCreativeTabs.metals);
		
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = getFullMetadata(world, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 127)) / 128].blockHardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		int meta = getFullMetadata(world, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 127)) / 128].blockResistance / 5.0F;
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.metals[(stack.getItemDamage() - (stack.getItemDamage() & 127)) / 128].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "metals.door." + types[(stack.getItemDamage() & 96) / 32];
	}

	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta - (meta & 31);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		setDoorRotation(getFullMetadata(blockAccess, x, y, z));
	}

	private void setDoorRotation(int meta) {
		float f = 0.1875F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
		int direction = meta & 3;
		boolean isOpen = (meta & 4) != 0;
		boolean hingeLeft = (meta & 16) != 0;
		
		if(direction == 0) {
			if(isOpen) {
				if(!hingeLeft) {
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
				} else {
					setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
				}
			} else {
				setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
			}
		} else if(direction == 1) {
			if(isOpen) {
				if(!hingeLeft) {
					setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				} else {
					setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
				}
			} else {
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
			}
		} else if(direction == 2) {
			if(isOpen) {
				if(!hingeLeft) {
					setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
				} else {
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
				}
			} else {
				setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
		} else if(direction == 3) {
			if(isOpen) {
				if(!hingeLeft) {
					setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
				} else {
					setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				}
			} else {
				setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xx, float yy, float zz) {
		int meta = getFullMetadata(world, x, y, z);
		int directionAndState = meta & 16359;
		directionAndState ^= 4;

		if((meta & 8) == 0) {
			setMetadata(world, x, y, z, directionAndState);
			world.markBlockForRenderUpdate(x, y, z);
			world.markBlockForRenderUpdate(x, y + 1, z);
		} else {
			setMetadata(world, x, y - 1, z, directionAndState);
			world.markBlockForRenderUpdate(x, y, z);
			world.markBlockForRenderUpdate(x, y - 1, z);
		}
		world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
		return true;
	}

	public void onPoweredBlockChange(World world, int x, int y, int z, boolean powered) {
		int meta = getFullMetadata(world, x, y, z);
		boolean isOpen = (meta & 4) != 0;

		if(isOpen != powered) {
			int directionAndState = meta & 16367;
			directionAndState ^= 4;

			if((meta & 8) == 0) {
				setMetadata(world, x, y, z, directionAndState);
				world.markBlockForRenderUpdate(x, y, z);
				world.markBlockForRenderUpdate(x, y + 1, z);
			} else {
				setMetadata(world, x, y - 1, z, directionAndState);
				world.markBlockForRenderUpdate(x, y, z);
				world.markBlockForRenderUpdate(x, y - 1, z);
			}
			world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		int meta = getMetadata(world, x, y, z);

		if((meta & 8) == 0) {
			boolean removed = false;
			if(world.getBlockId(x, y + 1, z) != blockID) {
				world.setBlockToAir(x, y, z);
				removed = true;
			}
			if(!world.doesBlockHaveSolidTopSurface(x, y - 1, z)) {
				world.setBlockToAir(x, y, z);
				removed = true;
				if(world.getBlockId(x, y + 1, z) == blockID) {
					world.setBlockToAir(x, y + 1, z);
				}
			}
			if(removed) {
				if(!world.isRemote) {
					dropBlockAsItem(world, x, y, z, meta, 0);
				}
			} else {
				boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
				if((powered || id > 0 && Block.blocksList[id].canProvidePower()) && id != blockID) {
					onPoweredBlockChange(world, x, y, z, powered);
				}
			}
		} else {
			if(world.getBlockId(x, y - 1, z) != blockID) {
				world.setBlockToAir(x, y, z);
			}
			if(id > 0 && id != blockID) {
				onNeighborBlockChange(world, x, y - 1, z, id);
			}
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.collisionRayTrace(world, x, y, z, startVec, endVec);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return y >= 255 ? false : world.doesBlockHaveSolidTopSurface(x, y - 1, z) && super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player) {
		int meta = getMetadata(world, x, y, z);
		if(player.capabilities.isCreativeMode && (meta & 8) != 0 && world.getBlockId(x, y - 1, z) == blockID) {
			world.setBlockToAir(x, y - 1, z);
		}
	}

	@Override
	public int idDropped(int meta, Random random, int fortune) {
		return (meta & 8) != 0 ? 0 : blockID;
	}

	public int getFullMetadata(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		boolean isTop = (meta & 8) != 0;
		int metaLower;
		int metaUpper;

		if(isTop) {
			metaLower = getMetadata(blockAccess, x, y - 1, z);
			metaUpper = meta;
		} else {
			metaLower = meta;
			metaUpper = getMetadata(blockAccess, x, y + 1, z);
		}		
		boolean hingeLeft = (metaUpper & 1) != 0;
		return metaLower & 16359 | (isTop ? 8 : 0) | (hingeLeft ? 16 : 0);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
		return (getFullMetadata(blockAccess, x, y, z) & 4) != 0;
	}

	@Override
	public int getMobilityFlag() {
		return 1;
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
		return 102;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int meta = getMetadata(blockAccess, x, y, z);
		if((meta & 8) != 0) {
			return side == 1 ? false : super.shouldSideBeRendered(blockAccess, x, y, z, side);
		} else {
			return side == 0 ? false : super.shouldSideBeRendered(blockAccess, x, y, z, side);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return MetalRegistry.metals[(meta - (meta & 127)) / 128].metalColor;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 127)) / 128].metalColor;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		boolean isTop = (meta & 8) != 0;
		int type = (meta & 96) / 32;
		if(side != 1 && side != 0) {
			int direction = meta & 3;
			boolean isOpen = (meta & 4) != 0;
			boolean flipped = false;

			if(isOpen) {
				if(direction == 0 && side == 2) {
					flipped = !flipped;
				} else if(direction == 1 && side == 5) {
					flipped = !flipped;
				} else if(direction == 2 && side == 3) {
					flipped = !flipped;
				} else if(direction == 3 && side == 4) {
					flipped = !flipped;
				}
			} else {
				if(direction == 0 && side == 5) {
					flipped = !flipped;
				} else if(direction == 1 && side == 3) {
					flipped = !flipped;
				} else if(direction == 2 && side == 4) {
					flipped = !flipped;
				} else if(direction == 3 && side == 2) {
					flipped = !flipped;
				}
				if((meta & 16) != 0) {
					flipped = !flipped;
				}
			}
			return ResourcesCore.doorMetalIcons[type][isTop ? 1 : 0][flipped ? 1 : 0];
		} else {
			return ResourcesCore.doorMetalIcons[type][isTop ? 1 : 0][0];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getFullMetadata(blockAccess, x, y, z));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasDoor) {
				list.add(new ItemStack(id, 1, (i * 128)));
				list.add(new ItemStack(id, 1, (i * 128) | 32));
				list.add(new ItemStack(id, 1, (i * 128) | 64));
				list.add(new ItemStack(id, 1, (i * 128) | 96));
			}
		}
	}
}
