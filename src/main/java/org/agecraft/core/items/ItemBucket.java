package org.agecraft.core.items;

import java.util.List;

import org.agecraft.ACCreativeTabs;
import org.agecraft.assets.resources.ResourcesCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.lang.LanguageManager;

public abstract class ItemBucket extends ItemFluidContainer {

	public ItemBucket(int id) {
		super(id);
		setCapacity(FluidContainerRegistry.BUCKET_VOLUME);
		setCreativeTab(ACCreativeTabs.tools);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if(hasFluid(stack)) {
			list.add(LanguageManager.getLocalization(getFluid(stack).getFluid().getUnlocalizedName()));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, !hasFluid(stack));
		if(mop == null) {
			return stack;
		}
		if(mop.typeOfHit == EnumMovingObjectType.TILE) {
			if(hasFluid(stack)) {
				if(mop.sideHit == 0) {
					mop.blockY--;
				}
				if(mop.sideHit == 1) {
					mop.blockY++;
				}
				if(mop.sideHit == 2) {
					mop.blockZ--;
				}
				if(mop.sideHit == 3) {
					mop.blockZ++;
				}
				if(mop.sideHit == 4) {
					mop.blockX--;
				}
				if(mop.sideHit == 5) {
					mop.blockX++;
				}
				if(!player.canPlayerEdit(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, stack) || !getFluid(stack).getFluid().canBePlacedInWorld()) {
					return stack;
				}
				if(tryPlaceContainedFluid(world, mop.blockX, mop.blockY, mop.blockZ, stack) && !player.capabilities.isCreativeMode) {
					drain(stack, FluidContainerRegistry.BUCKET_VOLUME, true);
					return stack;
				}
				return stack;
			} else {
				if(!world.canMineBlock(player, mop.blockX, mop.blockY, mop.blockZ)) {
					return stack;
				}
				Fluid fluid = FluidRegistry.lookupFluidForBlock(Block.blocksList[world.getBlockId(mop.blockX, mop.blockY, mop.blockZ)]);
				if(fluid != null && !getFluidBlacklist(stack).contains(fluid)) {
					ItemStack newStack = new ItemStack(stack.itemID, 1, stack.getItemDamage());
					newStack.stackTagCompound = stack.stackTagCompound;
					int amount = fill(newStack, new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME), true);
					if(amount > 0) {
						world.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
						if(!player.capabilities.isCreativeMode) {
							if(--stack.stackSize <= 0) {
								return newStack;
							}
							if(!player.inventory.addItemStackToInventory(newStack)) {
								player.dropPlayerItem(newStack);
							}
						}
						return stack;
					}
				}
			}
		}
		return stack;
	}

	public boolean tryPlaceContainedFluid(World world, int x, int y, int z, ItemStack stack) {
		Material material = world.getBlockMaterial(x, y, z);
		boolean isMaterialSolid = !world.getBlockMaterial(x, y, z).isSolid();
		if(!world.isAirBlock(x, y, z) && !isMaterialSolid) {
			return false;
		} else {
			if(world.provider.isHellWorld && getFluid(stack).getFluid().getID() == FluidRegistry.WATER.getID()) {
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
				for(int l = 0; l < 8; ++l) {
					world.spawnParticle("largesmoke", (double) x + Math.random(), (double) y + Math.random(), (double) z + Math.random(), 0.0D, 0.0D, 0.0D);
				}
			} else {
				if(!world.isRemote && isMaterialSolid && !material.isLiquid()) {
					world.destroyBlock(x, y, z, true);
				}
				int blockID = getFluid(stack).getFluid().getBlockID();
				if(blockID == Block.waterStill.blockID) {
					blockID = Block.waterMoving.blockID;
				} else if(blockID == Block.lavaStill.blockID) {
					blockID = Block.lavaMoving.blockID;
				}
				world.setBlock(x, y, z, blockID, 0, 3);
			}
			return true;
		}
	}
	
	@Override
	public boolean hasContainerItem() {
		return true;
	}
	
	@Override
	public ItemStack getContainerItemStack(ItemStack stack) {
		return new ItemStack(stack.itemID, 1, stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int meta) {
		return 2;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if(pass == 0) {
			return getIconIndex(stack);
		} else if(pass == 1) {
			return hasFluid(stack) && ResourcesCore.fluidContainerIcons.containsKey(getFluid(stack).getFluid().getName()) ? ResourcesCore.fluidContainerIcons.get(getFluid(stack).getFluid().getName())[0] : ResourcesCore.emptyTexture;
		}
		return ResourcesCore.emptyTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack stack) {
		return ResourcesCore.emptyTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 0) {
			return hasFluid(stack) ? getFluid(stack).getFluid().getColor(getFluid(stack)) : 0xFFFFFF;
		}
		return 0xFFFFFF;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return hasFluid(stack) ? 1 : 16;
	}

	public boolean hasFluid(ItemStack stack) {
		return getFluid(stack) != null;
	}
	
	public abstract List<Fluid> getFluidBlacklist(ItemStack stack);
}
