package elcon.mods.agecraft;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;

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

	public static void damageItem(ItemStack stack, int damage, EntityLivingBase entity) {
		if(!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode) {
			if(isItemStackDamageable(stack)) {
				if(stack.attemptDamageItem(damage, entity.getRNG())) {
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

	public static boolean isItemStackDamageable(ItemStack stack) {
		return Item.itemsList[stack.itemID].getMaxDamage(stack) > 0;
	}
}
