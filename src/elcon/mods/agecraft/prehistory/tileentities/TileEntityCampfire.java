package elcon.mods.agecraft.prehistory.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.items.ItemWoodStick;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfire;

public class TileEntityCampfire extends TileEntity {

	public int[] logs = new int[TreeRegistry.trees.length];
	public int logCount;
	public int currentLogIndex = 0;
	
	@Override
	public void updateEntity() {
		//TODO: stuffs
	}
	
	public boolean onBlockActivated(int side, ItemStack stack) {
		if(stack != null) {
			if(RecipesCampfire.getRecipe(stack) != null) {
				//TODO: stuffs
				return true;
			} else if(stack.getItem() instanceof ItemWoodStick && TreeRegistry.trees[stack.getItemDamage()] != null) {
				//TODO: stuffs
				return true;
			}
		}
		return false;
	}
	
	public boolean isBurning() {
		return false;
	}

	public void countLogs() {
		logCount = 0;
		for(int i = 0; i < logs.length; i++) {
			logCount += logs[i];
		}
	}
	
	public int getLogCount() {
		return Math.min(logCount, 8);
	}
}
