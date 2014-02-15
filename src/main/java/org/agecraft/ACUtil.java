package org.agecraft;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.oredict.OreDictionary;

public class ACUtil {

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

	public static boolean areItemStacksEqualNoSize(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return stack1.itemID != stack2.itemID ? false : (!areItemStacksDamageEqual(stack1, stack2) ? false : (stack1.stackTagCompound == null && stack2.stackTagCompound != null ? false : stack1.stackTagCompound == null || stack1.stackTagCompound.equals(stack2.stackTagCompound)));
		}
		return false;
	}

	public static boolean areItemStacksEqualNoTags(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return stack1.stackSize != stack2.stackSize ? false : (stack1.itemID != stack2.itemID ? false : (!areItemStacksDamageEqual(stack1, stack2) ? false : (stack1.stackTagCompound == null && stack2.stackTagCompound != null ? false : stack1.stackTagCompound == null || stack1.stackTagCompound.equals(stack2.stackTagCompound))));
		}
		return false;
	}

	public static boolean areItemStacksEqualNoSizeNoTags(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return stack1.itemID != stack2.itemID ? false : areItemStacksDamageEqual(stack1, stack2);
		}
		return false;
	}

	public static boolean areItemStacksDamageEqual(ItemStack stack1, ItemStack stack2) {
		return stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack1.getItemDamage() == stack2.getItemDamage();
	}

	public static int compareItemStacks(ItemStack stack1, ItemStack stack2) {
		if(stack1 != null && stack2 != null) {
			if(stack1.itemID == stack2.itemID) {
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
				return (stack1.itemID - stack2.itemID);
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
		return Item.itemsList[stack.itemID].getMaxDamage(stack) > 0;
	}

	public static void damageItem(ItemStack stack, int damage, EntityLivingBase entity) {
		if(!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode) {
			if(isItemStackDamageable(stack)) {
				if(attemptDamageItem(stack, damage, entity.getRNG())) {
					entity.renderBrokenItemStack(stack);
					stack.stackSize--;
					if(entity instanceof EntityPlayer) {
						EntityPlayer entityplayer = (EntityPlayer) entity;
						entityplayer.addStat(StatList.objectBreakStats[stack.itemID], 1);
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
			if(stack.getItem().hasContainerItem()) {
				return stack.getItem().getContainerItemStack(stack);
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
}
