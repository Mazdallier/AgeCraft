package org.agecraft.prehistory.recipes;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.agecraft.core.Trees;
import org.agecraft.prehistory.PrehistoryAge;

public class RecipesCampfireLogs implements IRecipe {

	public ItemStack[] getRecipe(InventoryCrafting inventory) {
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack != null && Item.getIdFromItem(stack.getItem()) != Block.getIdFromBlock(Trees.log) && Item.getIdFromItem(stack.getItem()) != Block.getIdFromBlock(PrehistoryAge.campfire)) {
				return null;
			}
		}
		ItemStack[] stacks = new ItemStack[3];
		int index = 1;
		for(int i = 0; i <= 1; i++) {
			for(int j = 0; j <= 2; j++) {
				ItemStack stack = checkMatch(inventory, i, j); 
				if(stack != null) {
					if(Item.getIdFromItem(stack.getItem()) == Block.getIdFromBlock(PrehistoryAge.campfire) && stacks[0] != null) {
						return null;
					}
					stacks[Item.getIdFromItem(stack.getItem()) == Block.getIdFromBlock(PrehistoryAge.campfire) ? 0 : index] = stack;
					index++;
					if(index > 2) {
						return null;
					}
				}
			}
		}
		if(stacks[0] == null) {
			if(stacks[1] == null || stacks[2] == null) {
				return null;
			}
		} else {
			if(stacks[1] == null && stacks[2] == null) {
				return null;
			}
		}		
		return stacks;
	}
	
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		return getRecipe(inventory) != null;
	}

	private ItemStack checkMatch(InventoryCrafting inventory, int x, int y) {
		ItemStack stackInSlot = inventory.getStackInRowAndColumn(x, y);
		if(stackInSlot != null) {
			if(Item.getIdFromItem(stackInSlot.getItem()) == Block.getIdFromBlock(Trees.log) || Item.getIdFromItem(stackInSlot.getItem()) == Block.getIdFromBlock(PrehistoryAge.campfire)) {
				ItemStack stack = stackInSlot.copy();
				stack.stackSize = 1;
				return stack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		ItemStack stack = getRecipeOutput().copy();
		ItemStack[] stacks = getRecipe(inventory);
		NBTTagCompound nbt;
		NBTTagList list;
		if(stacks[0] != null) {
			stack = stacks[0];
			nbt = stack.getTagCompound();
			if(nbt.hasKey("Logs")) {
				list = nbt.getTagList("Logs", 10);
			} else {
				list = new NBTTagList();
			}
		} else {
			nbt = new NBTTagCompound();
			list = new NBTTagList();
		}
		for(int i = 1; i < stacks.length; i++) {
			if(stacks[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				stacks[i].writeToNBT(tag);
				list.appendTag(tag);
			}
		}
		nbt.setTag("Logs", list);
		stack.setTagCompound(nbt);
		return stack;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(PrehistoryAge.campfire);
	}
}
