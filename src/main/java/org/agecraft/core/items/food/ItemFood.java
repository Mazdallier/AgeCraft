package org.agecraft.core.items.food;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.FoodRegistry.Food;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemFood extends Item {

	public ItemFood() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(ACCreativeTabs.food);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player) {
		if(player.canEat(getFood(stack).alwaysEdible)) {
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		return stack;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		stack.stackSize--;
		Food food = getFood(stack);
		player.getFoodStats().addStats(food.health, food.saturation);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(!world.isRemote && food.potionID > 0 && world.rand.nextFloat() < food.potionChance) {
			player.addPotionEffect(new PotionEffect(food.potionID, food.potionDuration * 20, food.potionAmplifier));
		}
		return stack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.eat;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return getFood(stack).itemUseDuration;
	}

	public Food getFood(ItemStack stack) {
		return getFood(stack.getItemDamage());
	}
	
	public abstract Food getFood(int meta);

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
	}
}
