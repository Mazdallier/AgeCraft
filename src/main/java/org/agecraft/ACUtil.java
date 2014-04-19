package org.agecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.oredict.OreDictionary;
import elcon.mods.elconqore.blocks.BlockFluidMetadata;

public class ACUtil {
	
	public static String[] colorNames = new String[]{"white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "lightGray", "cyan", "purple", "blue", "brown", "green", "red", "black"};

	public static String arrayToString(Object[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for(int i = 0; i < array.length; i++) {
			sb.append(array[i].toString());
			if(i < (array.length - 1)) {
				sb.append(", ");
			}
		}
		sb.append(']');
		return sb.toString();
	}

	public static boolean arraysEqual(Object[] array1, Object[] array2) {
		if(array1.length != array2.length) {
			return false;
		}
		for(int i = 0; i < array1.length; i++) {
			if(!array1[i].equals(array2[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static String getColorName(int color) {
		return colorNames[color];
	}
	
	public static int getColorFromName(String colorName) {
		for(int i = 0; i < colorNames.length; i++) {
			if(colorNames[i].equalsIgnoreCase(colorName)) {
				return i;
			}
		}
		return -1;
	}

	public static boolean areItemStacksEqualNoSize(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return Item.getIdFromItem(stack1.getItem()) != Item.getIdFromItem(stack2.getItem()) ? false : (!areItemStacksDamageEqual(stack1, stack2) ? false : (stack1.stackTagCompound == null && stack2.stackTagCompound != null ? false : stack1.stackTagCompound == null || stack1.stackTagCompound.equals(stack2.stackTagCompound)));
		}
		return false;
	}

	public static boolean areItemStacksEqualNoTags(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return stack1.stackSize != stack2.stackSize ? false : (Item.getIdFromItem(stack1.getItem()) != Item.getIdFromItem(stack2.getItem()) ? false : (!areItemStacksDamageEqual(stack1, stack2) ? false : (stack1.stackTagCompound == null && stack2.stackTagCompound != null ? false : stack1.stackTagCompound == null || stack1.stackTagCompound.equals(stack2.stackTagCompound))));
		}
		return false;
	}

	public static boolean areItemStacksEqualNoSizeNoTags(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return Item.getIdFromItem(stack1.getItem()) != Item.getIdFromItem(stack2.getItem()) ? false : areItemStacksDamageEqual(stack1, stack2);
		}
		return false;
	}

	public static boolean areItemStacksDamageEqual(ItemStack stack1, ItemStack stack2) {
		return stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack1.getItemDamage() == stack2.getItemDamage();
	}

	public static int compareItemStacks(ItemStack stack1, ItemStack stack2) {
		if(stack1 != null && stack2 != null) {
			if(Item.getIdFromItem(stack1.getItem()) != Item.getIdFromItem(stack2.getItem())) {
				if(stack1.getItemDamage() == stack2.getItemDamage()) {
					if(stack1.hasTagCompound() && stack2.hasTagCompound()) {
						if(stack1.getTagCompound().equals(stack2.getTagCompound())) {
							return (stack1.stackSize - stack2.stackSize);
						} else {
							return (stack1.getTagCompound().hashCode() - stack2.getTagCompound().hashCode());
						}
					} else if(!(stack1.hasTagCompound()) && stack2.hasTagCompound()) {
						return 1;
					} else if(stack1.hasTagCompound() && !(stack2.hasTagCompound())) {
						return -1;
					} else {
						return (stack1.stackSize - stack2.stackSize);
					}
				} else {
					return (stack1.getItemDamage() - stack2.getItemDamage());
				}
			} else {
				return Item.getIdFromItem(stack1.getItem()) - Item.getIdFromItem(stack2.getItem());
			}
		} else if(stack1 != null && stack2 == null) {
			return -1;
		} else if(stack1 == null && stack2 != null) {
			return 1;
		} else {
			return 0;
		}
	}

	public static boolean isItemStackDamageable(ItemStack stack) {
		return stack.getItem().getMaxDamage(stack) > 0;
	}

	public static void damageItem(ItemStack stack, int damage, EntityLivingBase entity) {
		if(!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode) {
			if(isItemStackDamageable(stack)) {
				if(attemptDamageItem(stack, damage, entity.getRNG())) {
					entity.renderBrokenItemStack(stack);
					stack.stackSize--;
					if(entity instanceof EntityPlayer) {
						EntityPlayer entityplayer = (EntityPlayer) entity;
						entityplayer.addStat(StatList.objectBreakStats[Item.getIdFromItem(stack.getItem())], 1);
						if(stack.stackSize == 0 && stack.getItem() instanceof ItemBow) {
							entityplayer.destroyCurrentEquippedItem();
						}
					}
					if(stack.stackSize < 0) {
						stack.stackSize = 0;
					}
					stack.setItemDamage(0);
				}
			}
		}
	}

	public static boolean attemptDamageItem(ItemStack stack, int damage, Random rand) {
		if(!isItemStackDamageable(stack)) {
			return false;
		} else {
			if(damage > 0) {
				int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
				int k = 0;
				for(int l = 0; j > 0 && l < damage; ++l) {
					if(EnchantmentDurability.negateDamage(stack, j, rand)) {
						k++;
					}
				}
				damage -= k;
				if(damage <= 0) {
					return false;
				}
			}
			stack.setItemDamage(stack.getItemDamage() + damage);
			return stack.getItemDamage() > stack.getMaxDamage();
		}
	}

	public static ItemStack consumeItem(ItemStack stack) {
		if(stack.stackSize == 1) {
			if(stack.getItem().hasContainerItem(stack)) {
				return stack.getItem().getContainerItem(stack);
			} else {
				return null;
			}
		} else {
			stack.splitStack(1);
			return stack;
		}
	}

	public static FluidStack getFluidContainerStack(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof IFluidContainerItem) {
			return ((IFluidContainerItem) stack.getItem()).getFluid(stack);
		}
		return FluidContainerRegistry.getFluidForFilledItem(stack);
	}

	public static ItemStack fillFluidContainer(ItemStack stack, FluidStack fluidStack) {
		if(stack != null && stack.getItem() instanceof IFluidContainerItem) {
			((IFluidContainerItem) stack.getItem()).fill(stack, fluidStack, true);
			return stack;
		}
		return FluidContainerRegistry.fillFluidContainer(fluidStack, stack);
	}

	public static ItemStack drainFluidContainer(ItemStack stack, int amount) {
		if(stack != null && stack.getItem() instanceof IFluidContainerItem) {
			((IFluidContainerItem) stack.getItem()).drain(stack, amount, true);
			return stack;
		}
		return ACUtil.consumeItem(stack);
	}

	public static Fluid getFluidForBlock(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if(block instanceof BlockFluidMetadata) {
			return ((BlockFluidMetadata) block).getQuantaValue(world, x, y, z) > 0 ? ((BlockFluidMetadata) block).getFluid(world, x, y, z) : null;
		}
		return FluidRegistry.lookupFluidForBlock(block);
	}

	public static int compareFluidStack(FluidStack stack1, FluidStack stack2) {
		if(stack1 != null && stack2 != null) {
			if(stack1.fluidID == stack2.fluidID) {
				if(stack1.tag != null && stack2.tag != null) {
					if(stack1.tag.equals(stack2.tag)) {
						return (stack1.amount - stack2.amount);
					} else {
						return (stack1.tag.hashCode() - stack2.tag.hashCode());
					}
				} else if(stack1.tag != null && stack2.tag == null) {
					return -1;
				} else if(stack1.tag == null && stack2.tag != null) {
					return 1;
				} else {
					return (stack1.amount - stack2.amount);
				}
			} else {
				return (stack1.fluidID - stack2.fluidID);
			}
		} else if(stack1 != null && stack2 == null) {
			return -1;
		} else if(stack1 == null && stack2 != null) {
			return 1;
		} else {
			return 0;
		}
	}

	public static void transferEntityToDimension(Entity entity, int dimensionID, Class<? extends Teleporter> teleporterClass) {
		if(!entity.worldObj.isRemote && !entity.isDead) {
			entity.worldObj.theProfiler.startSection("changeDimension");
			MinecraftServer server = MinecraftServer.getServer();
			int oldDimension = entity.dimension;
			WorldServer oldWorld = server.worldServerForDimension(oldDimension);
			WorldServer world = server.worldServerForDimension(dimensionID);
			entity.dimension = dimensionID;
			if(oldDimension == 1 && dimensionID == 1) {
				world = server.worldServerForDimension(0);
				entity.dimension = 0;
			}
			entity.worldObj.removeEntity(entity);
			entity.isDead = false;
			entity.worldObj.theProfiler.startSection("reposition");
			Teleporter teleporter = null;
			if(teleporterClass != null) {
				try {
					teleporter = teleporterClass.getConstructor(WorldServer.class).newInstance(world);
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				teleporter = world.getDefaultTeleporter();
			}
			server.getConfigurationManager().transferEntityToWorld(entity, oldDimension, oldWorld, world, teleporter);
			entity.worldObj.theProfiler.endStartSection("reloading");
			Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(entity), world);
			if(newEntity != null) {
				newEntity.copyDataFrom(entity, true);
				if(oldDimension == 1 && dimensionID == 1) {
					ChunkCoordinates chunkcoordinates = world.getSpawnPoint();
					chunkcoordinates.posY = entity.worldObj.getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
					newEntity.setLocationAndAngles((double) chunkcoordinates.posX, (double) chunkcoordinates.posY, (double) chunkcoordinates.posZ, newEntity.rotationYaw, newEntity.rotationPitch);
				}
				world.spawnEntityInWorld(newEntity);
			}
			entity.isDead = true;
			entity.worldObj.theProfiler.endSection();
			oldWorld.resetUpdateEntityTick();
			world.resetUpdateEntityTick();
			entity.worldObj.theProfiler.endSection();
		}
	}
}
