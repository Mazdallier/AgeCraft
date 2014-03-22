package org.agecraft.core.items.farming;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.Farming;
import org.agecraft.core.registry.FoodRegistry;
import org.agecraft.core.registry.FoodRegistry.Food;
import org.agecraft.core.registry.FoodRegistry.FoodProperties;
import org.agecraft.core.registry.FoodRegistry.FoodStage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemFood extends Item {

	public ItemFood() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(ACCreativeTabs.food);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player) {
		if(player.canEat(getFoodProperties(stack.getItemDamage()).alwaysEdible)) {
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		return stack;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		stack.stackSize--;
		FoodProperties foodProperties = getFoodProperties(stack.getItemDamage());
		player.getFoodStats().addStats(foodProperties.health, foodProperties.saturation);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote && foodProperties.potionID > 0 && world.rand.nextFloat() < foodProperties.potionChance) {
			player.addPotionEffect(new PotionEffect(foodProperties.potionID, foodProperties.potionDuration * 20, foodProperties.potionAmplifier));
		}
		return stack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.eat;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return getFoodProperties(stack.getItemDamage()).itemUseDuration;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		FoodProperties foodProperties = getFoodProperties(stack.getItemDamage());
		String name = LanguageManager.getLocalization(foodProperties.prefix);
		if(!foodProperties.prefix.isEmpty()) {
			name += " ";
		}
		name += LanguageManager.getLocalization(getUnlocalizedName(stack));
		if(!foodProperties.postfix.isEmpty()) {
			name += " ";
		}
		name += LanguageManager.getLocalization(foodProperties.postfix);
		return name;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "food." + getFood(stack.getItemDamage()).type.toString().toLowerCase() + "." + getFood(stack.getItemDamage()).name;
	}

	public Food getFood(int meta) {
		return FoodRegistry.instance.get((meta - (meta & 7)) / 8);
	}
	
	public FoodStage getFoodStage(int meta) {
		return FoodStage.values()[meta & 7];
	}
	
	public FoodProperties getFoodProperties(int meta) {
		return getFood(meta).properties.get(getFoodStage(meta));
	}
	
	public static boolean isFood(ItemStack stack, String food) {
		return stack != null && stack.getItem() == Farming.food && ((ItemFood) Farming.food).getFood(stack.getItemDamage()).name.equals(food);
	}
	
	public static ItemStack createFood(String food, FoodStage foodStage) {
		return new ItemStack(Farming.food, 1, foodStage.ordinal() + FoodRegistry.instance.get(food).id * 8);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return getFoodProperties(meta).icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < FoodRegistry.instance.getAll().length; i++) {
			Food food = FoodRegistry.instance.get(i);
			if(food != null) {
				for(FoodStage foodStage : food.properties.keySet()) {
					list.add(new ItemStack(item, 1, foodStage.ordinal() + i * 8));
				}
			}
		}
	}
}
