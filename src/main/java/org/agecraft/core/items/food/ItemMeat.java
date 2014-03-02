package org.agecraft.core.items.food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.agecraft.core.registry.FoodRegistry.Food;
import org.agecraft.core.registry.MeatRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemMeat extends ItemFood {

	public FoodStage foodStage;
	
	public ItemMeat(FoodStage foodStage) {
		this.foodStage = foodStage;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		stack.stackSize--;
		Food food = getFood(stack);
		if(foodStage == FoodStage.RAW) {
			player.getFoodStats().addStats((int) (food.health * 0.5F), food.saturation * 0.5F);
		} else if(foodStage == FoodStage.BURNED) {
			player.getFoodStats().addStats(0, 0.0F);
		} else {
			player.getFoodStats().addStats(food.health, food.saturation);
		}		
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote) {
			if(foodStage == FoodStage.RAW) {
				player.addPotionEffect(new PotionEffect(Potion.hunger.id, 1200, 0));
			} else if(foodStage == FoodStage.RAW) {
				player.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 0));
			}
			if(food.potionID > 0 && world.rand.nextFloat() < food.potionChance) {
				player.addPotionEffect(new PotionEffect(food.potionID, food.potionDuration * 20, food.potionAmplifier));
			}
		}
		return stack;
	}
	
	@Override
	public Food getFood(int meta) {
		return MeatRegistry.instance.get(meta);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization("food." + foodStage.name) + "" + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "food.meat." + getFood(stack).name;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return MeatRegistry.instance.get(meta).getIcon(foodStage);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MeatRegistry.instance.getAll().length; i++) {
			if(MeatRegistry.instance.get(i) != null) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
