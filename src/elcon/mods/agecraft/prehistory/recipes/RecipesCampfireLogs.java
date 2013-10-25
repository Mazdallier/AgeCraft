package elcon.mods.agecraft.prehistory.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.prehistory.PrehistoryAge;

public class RecipesCampfireLogs implements IRecipe {

	public ItemStack[] getRecipe(InventoryCrafting inventory) {
		ItemStack[] stacks;
		for(int i = 0; i <= 1; i++) {
			for(int j = 0; j <= 2; j++) {
				stacks = checkMatch(inventory, i, j, true); 
				if(stacks != null && stacks.length > 0) {
					return stacks;
				}
				stacks = checkMatch(inventory, i, j, false); 
				if(stacks != null && stacks.length > 0) {
					return stacks;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		return getRecipe(inventory) != null;
	}

	private ItemStack checkMatch(InventoryCrafting inventory, int x, int y, boolean mirror) {
		ItemStack stackInSlot = inventory.getStackInRowAndColumn(x, y);
		if(stackInSlot != null) {
			if(stackInSlot.itemID == Trees.log.blockID) {
				ItemStack stack = stackInSlot.copy();
				stack.stackSize = 1;
				return stack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		System.out.println("getCraftingResult");
		ItemStack stack = getRecipeOutput().copy();
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		ItemStack[] stacks = getRecipe(inventory);
		if(stack != null) {
			for(int i = 0; i < stacks.length; i++) {
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
		System.out.println("getRecipeOutput");
		return new ItemStack(PrehistoryAge.campfire);
	}
}
