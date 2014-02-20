package org.agecraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockSmelteryFurnace extends ItemBlockWithMetadata {

	public ItemBlockSmelteryFurnace(Block block) {
		super(block, block);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return field_150939_a.getLocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return field_150939_a.getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return field_150939_a.getUnlocalizedName();
	}
}
