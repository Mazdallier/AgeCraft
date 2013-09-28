package elcon.mods.agecraft;

import net.minecraft.item.ItemStack;

public class ACUtil {

	public static boolean areItemStacksEqualNoSize(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return stack1.itemID != stack2.itemID ? false : (stack1.getItemDamage() != stack2.getItemDamage() ? false : (stack1.stackTagCompound == null && stack2.stackTagCompound != null ? false : stack1.stackTagCompound == null || stack1.stackTagCompound.equals(stack2.stackTagCompound)));
		}
		return false;
	}
	
	public static boolean areItemStacksEqualNoTags(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return stack1.stackSize != stack2.stackSize ? false : (stack1.itemID != stack2.itemID ? false : (stack1.getItemDamage() != stack2.getItemDamage() ? false : (stack1.stackTagCompound == null && stack2.stackTagCompound != null ? false : stack1.stackTagCompound == null || stack1.stackTagCompound.equals(stack2.stackTagCompound))));
		}
		return false;
	}
	
	public static boolean areItemStacksEqualNoSizeNoTags(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null && stack2 == null) {
			return true;
		}
		if(stack1 != null && stack2 != null) {
			return stack1.itemID != stack2.itemID ? false : stack1.getItemDamage() == stack2.getItemDamage();
		}
		return false;
	}
}
