package org.agecraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import org.agecraft.core.registry.TreeRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.items.ItemBlockExtendedMetadata;

public class ItemBlockLeaves extends ItemBlockExtendedMetadata {

	public ItemBlockLeaves(Block block) {
		super(block);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return TreeRegistry.instance.get((stack.getItemDamage() - (stack.getItemDamage() & 3)) / 4).leafColor;
	}
}
