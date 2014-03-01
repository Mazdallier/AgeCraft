package org.agecraft.core.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

import org.agecraft.ACCreativeTabs;
import org.agecraft.ACUtil;
import org.agecraft.core.AgeCraftCoreClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public abstract class ItemBucket extends ItemFluidContainer {

	public ItemBucket() {
		super(0);
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
		if(mop.typeOfHit == MovingObjectType.BLOCK) {
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			if(!world.canMineBlock(player, x, y, z)) {
				return stack;
			}
			if(hasFluid(stack)) {
				if(mop.sideHit == 0) {
					y--;
				}
				if(mop.sideHit == 1) {
					y++;
				}
				if(mop.sideHit == 2) {
					z--;
				}
				if(mop.sideHit == 3) {
					z++;
				}
				if(mop.sideHit == 4) {
					x--;
				}
				if(mop.sideHit == 5) {
					x++;
				}
				if(!player.canPlayerEdit(x, y, z, mop.sideHit, stack) || !getFluid(stack).getFluid().canBePlacedInWorld()) {
					return stack;
				}
				if(tryPlaceContainedFluid(world, x, y, z, stack) && !player.capabilities.isCreativeMode) {
					drain(stack, FluidContainerRegistry.BUCKET_VOLUME, true);
					return stack;
				}
				return stack;
			} else {
				Fluid fluid = ACUtil.getFluidForBlock(world, x, y, z);				
				if(fluid != null && !getFluidBlacklist(stack).contains(fluid)) {
					ItemStack newStack = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
					newStack.stackTagCompound = stack.stackTagCompound;
					int amount = fill(newStack, new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME), true);
					if(amount > 0) {
						world.setBlockToAir(x, y, z);
						if(!player.capabilities.isCreativeMode) {
							if(--stack.stackSize <= 0) {
								return newStack;
							}
							if(!player.inventory.addItemStackToInventory(newStack)) {
								player.dropPlayerItemWithRandomChoice(newStack, false);
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
		Material material = world.getBlock(x, y, z).getMaterial();
		boolean isMaterialSolid = !material.isSolid();
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
					world.setBlockToAir(x, y, z);
				}
				Block block = getFluid(stack).getFluid().getBlock();
				if(block == Blocks.water) {
					block = Blocks.flowing_water;
				} else if(block == Blocks.lava) {
					block = Blocks.flowing_lava;
				}
				world.setBlock(x, y, z, block, 0, 3);
			}
			return true;
		}
	}
	
	@Override
	public boolean hasContainerItem() {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return new ItemStack(stack.getItem(), 1, stack.getItemDamage());
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
	public IIcon getIcon(ItemStack stack, int pass) {
		if(pass == 0) {
			return getIconIndex(stack);
		} else if(pass == 1) {
			return hasFluid(stack) && AgeCraftCoreClient.fluidContainerIcons.containsKey(getFluid(stack).getFluid().getName()) ? AgeCraftCoreClient.fluidContainerIcons.get(getFluid(stack).getFluid().getName())[0] : AgeCraftCoreClient.emptyTexture;
		}
		return AgeCraftCoreClient.emptyTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		return AgeCraftCoreClient.emptyTexture;
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
