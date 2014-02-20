package org.agecraft.core.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.agecraft.core.registry.CoinRegistry.Coin;

public class CoinRegistry extends Registry<Coin> {

	public static class Coin {
		
		public int id;
		public String name;
		
		public int value;
		public ItemStack stack;
		
		public Coin(int id, String name, int value, ItemStack stack) {
			this.id = id;
			this.name = name;
			this.value = value;
			this.stack = stack;
		}
	}
	
	public static CoinRegistry instance = new CoinRegistry();
	
	public CoinRegistry() {
		super(128);
	}
	
	public static Coin getCoin(ItemStack stack) {
		for(int i = 0; i < instance.getAll().length; i++) {
			if(instance.get(i) != null && Item.getIdFromItem(instance.get(i).stack.getItem()) == Item.getIdFromItem(stack.getItem()) && instance.get(i).stack.getItemDamage() == stack.getItemDamage() && ItemStack.areItemStackTagsEqual(instance.get(i).stack, stack)) {
				return instance.get(i);
			}
		}
		return null;
	}
	
	public static int getValue(ItemStack stack) {
		Coin coin = getCoin(stack);
		if(coin != null) {
			return coin.value * stack.stackSize;
		}
		return 0;
	}
	
	public static void registerCoin(Coin coin) {
		instance.set(coin.id, coin);
	}
}
