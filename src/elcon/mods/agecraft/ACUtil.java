package elcon.mods.agecraft;

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
import net.minecraftforge.oredict.OreDictionary;

public class ACUtil {

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
}
