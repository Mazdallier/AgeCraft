package elcon.mods.agecraft.core.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import scala.Tuple2;
import codechicken.microblock.ItemMicroPart;
import codechicken.microblock.MicroMaterialRegistry;
import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
import codechicken.microblock.Saw;
import codechicken.microblock.handler.MicroblockProxy;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.gui.InventoryCraftMatrix;
import elcon.mods.agecraft.recipes.Recipe;
import elcon.mods.agecraft.recipes.RecipeType;
import elcon.mods.agecraft.recipes.WrappedStack;

public class RecipeMicroblocks extends Recipe {

	public static HashMap<Integer, Integer> splitMap = new HashMap<Integer, Integer>();
	static {
		splitMap.put(0, 3);
		splitMap.put(1, 3);
		splitMap.put(3, 2);
	}
	
	public boolean matches(InventoryCraftMatrix inventory) {
		return getCraftingResult(inventory) != null;
	}

	public ItemStack getCraftingResult(InventoryCraftMatrix inventory) {
		ItemStack stack = getHollowResult(inventory);
		if(stack != null) {
			return stack;
		}
		stack = getGluingResult(inventory);
		if(stack != null) {
			return stack;
		}
		stack = getThinningResult(inventory);
		if(stack != null) {
			return stack;
		}
		stack = getSplittingResult(inventory);
		if(stack != null) {
			return stack;
		}
		stack = getHollowFillResult(inventory);
		return stack;
	}

	public ItemStack getHollowResult(InventoryCraftMatrix inventory) {
		if(inventory.getStackInRowAndColumn(1, 1) != null) {
			return null;
		}
		ItemStack first = inventory.getStackInRowAndColumn(0, 0);
		if(first == null || first.getItem() != MicroblockProxy.itemMicro() || getMicroClass(first) != 0) {
			return null;
		}
		int size = getMicroSize(first);
		int material = getMicroMaterial(first);
		for(int i = 0; i < 9; i++) {
			if(i != 4) {
				ItemStack stack = inventory.getStackInSlot(i);
				if(stack == null || stack.getItem() != MicroblockProxy.itemMicro() || getMicroMaterial(stack) != material || stack.getItemDamage() != first.getItemDamage()) {
					return null;
				}
			}
		}
		return createMicroblockStack(8, 1, size, material);
	}

	public ItemStack getGluingResult(InventoryCraftMatrix inventory) {
		int size = 0;
		int count = 0;
		int smallest = 0;
		int microClass = 0;
		int material = 0;
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack != null) {
				if(stack.getItem() != MicroblockProxy.itemMicro()) {
					return null;
				}
				if(count == 0) {
					size = getMicroSize(stack);
					microClass = getMicroClass(stack);
					material = getMicroMaterial(stack);
					count = 1;
					smallest = size;
				} else if(getMicroClass(stack) != microClass || getMicroMaterial(stack) != material) {
					return null;
				} else if(microClass >= 2 && getMicroSize(stack) != smallest) {
					return null;
				} else {
					smallest = Math.min(smallest, getMicroSize(stack));
					count += 1;
					size += getMicroSize(stack);
				}
			}
		}
		if(count <= 1) {
			return null;
		}
		String materialName = MicroMaterialRegistry.materialName(material);
		switch(microClass) {
		case 0:
		case 1:
			List<Integer> list = Arrays.asList(1, 2, 4);
			int result = 0;
			for(int s : list) {
				if((s & size) != 0) {
					result = s;
					continue;
				}
			}
			if(result != 0) {
				return createMicroblockStack(size / 8, 0, 8, material);
			} else if(result <= smallest) {
				return null;
			} else {
				createMicroblockStack(size / result, microClass, result, material);
			}
		case 2:
			if(count == 2) {
				return createMicroblockStack(1, 3, smallest, material);
			} else if(count == 4) {
				return createMicroblockStack(1, 0, smallest, material);
			}
			return null;
		case 3:
			if(count == 2) {
				return createMicroblockStack(1, 0, smallest, material);
			}
			return null;
		default:
			return null;
		}
	}
	
	public ItemStack getThinningResult(InventoryCraftMatrix inventory) {
		int sawIndex = getSawIndex(inventory);
		int sawColumn = sawIndex % 3;
		int sawRow = sawIndex / 3;
		ItemStack sawStack = inventory.getStackInRowAndColumn(sawColumn, sawRow);
		if(sawStack == null) {
			return null;
		}
		ItemStack stack = inventory.getStackInRowAndColumn(sawColumn, sawRow + 1);
		if(stack == null) {
			return null;
		}
		int size = getMicroSize(stack);
		int material = getMicroMaterial(stack);
		int microClass = getMicroClass(stack);
		if(size == 1 || material < 0 || !canSawCut((Saw) sawStack.getItem(), sawStack, material)) {
			return null;
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if((i != sawColumn || j != sawRow && j != sawRow + 1) && inventory.getStackInRowAndColumn(i, j) != null) {
					return null;
				}
			}
		}
		return createMicroblockStack(2, microClass, size / 2, material);
	}
	
	public ItemStack getSplittingResult(InventoryCraftMatrix inventory) {
		int sawIndex = getSawIndex(inventory);
		int sawColumn = sawIndex % 3;
		int sawRow = sawIndex / 3;
		ItemStack sawStack = inventory.getStackInRowAndColumn(sawColumn, sawRow);
		if(sawStack == null) {
			return null;
		}
		ItemStack stack = inventory.getStackInRowAndColumn(sawColumn + 1, sawRow);
		if(stack == null || stack.getItem() != MicroblockProxy.itemMicro()) {
			return null;
		}
		int microClass = getMicroClass(stack);
		int material = getMicroMaterial(stack);
		if(!canSawCut((Saw) sawStack.getItem(), sawStack, material)) {
			return null;
		}
		int split = splitMap.containsKey(microClass) ? splitMap.get(microClass) : -1;
		if(split == -1) {
			return null;
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if((j != sawRow || i != sawColumn && i != sawColumn + 1) && inventory.getStackInRowAndColumn(i, j) != null) {
					return null;
				}
			}
		}
		return createMicroblockStack(2, split, getMicroSize(stack), material);
	}
	
	public ItemStack getHollowFillResult(InventoryCraftMatrix inventory) {
		ItemStack cover = null;
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack != null) {
				if(stack.getItem() != MicroblockProxy.itemMicro() || cover != null || getMicroClass(stack) != 1) {
					return null;
				} else {
					cover = stack;
				}
			}
		}
		if(cover == null) {
			return null;
		}
		return createMicroblockStack(1, 0, getMicroSize(cover), getMicroMaterial(cover));
	}

	public ItemStack createMicroblockStack(int amount, int microblockClass, int size, int material) {
		if(size == 8) {
			ItemStack stack = MicroMaterialRegistry.getMaterial(material).getItem().copy();
			stack.stackSize = amount;
			return stack;
		}
		return ItemMicroPart.create(amount, microblockClass << 8 | size, MicroMaterialRegistry.materialName(material));
	}

	public int getMicroMaterial(ItemStack stack) {
		if(stack.getItem() == MicroblockProxy.itemMicro()) {
			return ItemMicroPart.getMaterialID(stack);
		}
		return findMaterial(stack);
	}

	public int getMicroClass(ItemStack stack) {
		if(stack.getItem() == MicroblockProxy.itemMicro()) {
			return stack.getItemDamage() >> 8;
		}
		return 0;
	}

	public int getMicroSize(ItemStack stack) {
		if(stack.getItem() == MicroblockProxy.itemMicro()) {
			return stack.getItemDamage() & 0xFF;
		}
		return 8;
	}

	public int findMaterial(ItemStack stack) {
		Tuple2<String, IMicroMaterial>[] idMap = MicroMaterialRegistry.getIdMap();
		for(int i = 0; i < idMap.length; i++) {
			if(ACUtil.areItemStacksEqualNoSize(stack, idMap[i]._2().getItem())) {
				return MicroMaterialRegistry.materialID(idMap[i]._1());
			}
		}
		return -1;
	}
	
	public int getSawIndex(InventoryCraftMatrix inventory) {
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack != null && stack.getItem() instanceof Saw) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean canSawCut(Saw saw, ItemStack sawStack, int material) {
		int sawStrength = saw.getCuttingStrength(sawStack);
		int materialStrength = MicroMaterialRegistry.getMaterial(material).getCutterStrength();
		return sawStrength >= materialStrength || sawStrength == MicroMaterialRegistry.getMaxCuttingStrength();
	}

	@Override
	public List<List<WrappedStack>> getInputs() {
		return null;
	}

	@Override
	public List<WrappedStack> getOutput(List<WrappedStack> input) {
		return null;
	}

	@Override
	public RecipeType getRecipeType() {
		return RecipeType.CRAFTING;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}
}
