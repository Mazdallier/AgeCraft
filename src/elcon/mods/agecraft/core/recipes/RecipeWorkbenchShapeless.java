package elcon.mods.agecraft.core.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.gui.InventoryCraftMatrix;
import elcon.mods.agecraft.recipes.RecipeSimple;
import elcon.mods.agecraft.recipes.RecipeType;
import elcon.mods.agecraft.recipes.WrappedStack;

public class RecipeWorkbenchShapeless extends RecipeSimple {

	public ArrayList<ItemStack> input;
	public ItemStack output;
	public boolean requiresHammer;
	public boolean requiresSaw;
	public int hammerDamage;
	public int sawDamage;
	public int hammerHarvestLevel;
	public int sawHarvestLevel;
	
	public RecipeWorkbenchShapeless(ItemStack output, int hammerDamage, int sawDamage, int hammerHarvestLevel, int sawHarvestLevel, Object... input) {
		this(output, hammerDamage, sawDamage, input);
		this.hammerHarvestLevel = hammerHarvestLevel;
		this.sawHarvestLevel = sawHarvestLevel;
	}
	
	public RecipeWorkbenchShapeless(ItemStack output, int hammerDamage, int sawDamage, Object... input) {
		super();
		this.output = output;
		this.hammerDamage = hammerDamage;
		this.sawDamage = sawDamage;
		this.requiresHammer = hammerDamage > 0;
		this.requiresSaw = sawDamage > 0;
		this.hammerHarvestLevel = 0;
		this.sawHarvestLevel = 0;
		
		this.input = new ArrayList<ItemStack>();
		for(int i = 0; i < input.length; i++) {
			if(input[i] instanceof ItemStack) {
				this.input.add((ItemStack) input[i]);
			} else if(input[i] instanceof Block) {
				this.input.add(new ItemStack((Block) input[i]));
			} else if(input[i] instanceof Item) {
				this.input.add(new ItemStack((Item) input[i]));
			}
		}
	}
	
	public boolean matches(InventoryCraftMatrix inventory) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>(input);
		for(int i = 0; i < inventory.width; i++) {
			for(int j = 0; j < inventory.height; j++) {
				ItemStack stack = inventory.getStackInRowAndColumn(i, j);
				if(stack != null) {
					boolean flag = false;
					Iterator<ItemStack> iterator = list.iterator();
					while(iterator.hasNext()) {
						ItemStack currentStack = iterator.next();
						if(ACUtil.areItemStacksEqualNoSize(stack, currentStack)) {
							flag = true;
							list.remove(currentStack);
							break;
						}
					}
					if(!flag) {
						return false;
					}
				}
			}
		}
		return list.isEmpty();
	}
	
	@Override
	public List<WrappedStack> getInput() {
		return WrappedStack.createList(input);
	}

	@Override
	public List<WrappedStack> getOutput() {
		return WrappedStack.createList(output);
	}

	@Override
	public RecipeType getRecipeType() {
		return RecipeType.CRAFTING;
	}

	@Override
	public int getRecipeSize() {
		return input.size();
	}
}
