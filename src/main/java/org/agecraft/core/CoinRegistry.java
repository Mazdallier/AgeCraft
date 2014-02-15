package org.agecraft.core;

import org.agecraft.ACLog;

import net.minecraft.item.ItemStack;

public class CoinRegistry {

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
	
	public static Coin[] coins = new Coin[128];
	
	public static void registerCoin(Coin coin) {
		if(coins[coin.id] != null) {
			ACLog.warning("[CoinRegistry] Overriding existing coin (" + coins[coin.id].id + ": " + coins[coin.id].name.toUpperCase() + ") with new coin (" + coin.id + ": " + coin.name.toUpperCase() + ")");
		}
		coins[coin.id]= coin;
	}
	
	public static Coin getCoin(ItemStack stack) {
		for(int i = 0; i < coins.length; i++) {
			if(coins[i] != null && coins[i].stack.itemID == stack.itemID && coins[i].stack.getItemDamage() == stack.getItemDamage() && ItemStack.areItemStackTagsEqual(coins[i].stack, stack)) {
				return coins[i];
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
}
