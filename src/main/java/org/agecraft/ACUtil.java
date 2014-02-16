package org.agecraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
}
