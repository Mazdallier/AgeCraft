package org.agecraft.core.entity;

import net.minecraft.item.ItemStack;

public class EntityDrop {

	public ItemStack stack;
	public int min;
	public int max;
	public float chance;
	
	public boolean canLoot = true;
	public boolean shouldBurn = false;
	public boolean shouldNotBurn = false;
	
	public EntityDrop(ItemStack stack) {
		this(stack, 1, 1, 1.0F);
	}
	
	public EntityDrop(ItemStack stack, float chance) {
		this(stack, 1, 1, chance);
	}
	
	public EntityDrop(ItemStack stack, int min, int max) {
		this(stack, min, max, 1.0F);
	}
	
	public EntityDrop(ItemStack stack, int min, int max, float chance) {
		this.stack = stack;
		this.stack.stackSize = 1;
		this.min = min;
		this.max = max;
		this.chance = chance;
	}
	
	public EntityDrop setCanLoot(boolean canLoot) {
		this.canLoot = canLoot;
		return this;
	}
	
	public EntityDrop setShouldBurn(boolean shouldBurn) {
		this.shouldBurn = shouldBurn;
		return this;
	}
	
	public EntityDrop setShouldNotBurn(boolean shouldNotBurn) {
		this.shouldNotBurn = shouldNotBurn;
		return this;
	}
}
