package elcon.mods.agecraft.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import elcon.mods.agecraft.ACUtil;

public class WrappedStack implements Comparable<WrappedStack> {

	private Object wrappedStack;
	private int stackSize;

	public WrappedStack() {
		wrappedStack = null;
		stackSize = -1;
	}

	public WrappedStack(Object object) {
		this();
		if(object instanceof Item) {
			object = new ItemStack((Item) object);
		} else if(object instanceof Block) {
			object = new ItemStack((Block) object);
		} else if(object instanceof Fluid) {
			object = new FluidStack((Fluid) object, FluidContainerRegistry.BUCKET_VOLUME);
		}

		if(object instanceof ItemStack) {
			ItemStack stack = ((ItemStack) object).copy();
			stackSize = stack.stackSize;
			stack.stackSize = 1;
			wrappedStack = stack;
		} else if(object instanceof FluidStack) {
			FluidStack stack = ((FluidStack) object).copy();
			stackSize = stack.amount;
			stack.amount = 1;
			wrappedStack = stack;
		} else if(object instanceof WrappedStack) {
			WrappedStack stack = ((WrappedStack) object).copy();
			if(stack.getWrappedStack() != null) {
				stackSize = stack.getStackSize();
				wrappedStack = stack.getWrappedStack();
			}
		}
	}

	public Object getWrappedStack() {
		return wrappedStack;
	}

	public int getStackSize() {
		return stackSize;
	}
	
	public void setStackSize(int stackSize) {
		this.stackSize = stackSize;
	}

	public boolean isNull() {
		return getWrappedStack() == null || getStackSize() == -1;
	}

	public boolean isItemStack() {
		return !isNull() && getWrappedStack() instanceof ItemStack;
	}

	public ItemStack getItemStack() {
		return (ItemStack) getWrappedStack();
	}

	public boolean isFluidStack() {
		return !isNull() && getWrappedStack() instanceof FluidStack;
	}

	public FluidStack getFluidStack() {
		return (FluidStack) getWrappedStack();
	}

	public WrappedStack copy() {
		if(getStackSize() != -1 && !isNull()) {
			if(isItemStack()) {
				return new WrappedStack(getItemStack().copy());
			} else if(isFluidStack()) {
				return new WrappedStack(getFluidStack().copy());
			}
		}
		return new WrappedStack();
	}
	
	@Override
	public boolean equals(Object object) {
		return object instanceof WrappedStack && compareTo((WrappedStack) object) == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 1;
		hashCode = (37 * hashCode) + getStackSize();
		
		if(isItemStack()) {
			hashCode = (37 * hashCode) + getItemStack().itemID;
			hashCode = (37 * hashCode) + getItemStack().getItemDamage();
			if(getItemStack().hasTagCompound()) {
				hashCode = (37 * hashCode) + getItemStack().getTagCompound().hashCode();
			}
		} else if(isFluidStack()) {
			hashCode = (37 * hashCode) + getFluidStack().fluidID;
			if(getFluidStack().tag != null) {
				hashCode = (37 * hashCode) + getFluidStack().tag.hashCode();
			}
		}
		return hashCode;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(isItemStack()) {
			sb.append(String.format("%sxItemStack[%s:%s:%s]", getStackSize(), getItemStack().itemID, getItemStack().getItemDamage(), getItemStack().getUnlocalizedName()));
		} else if(isFluidStack()) {
			sb.append(String.format("%sxFluidStack[%s:%s]", getStackSize(), getFluidStack().fluidID, getFluidStack().getFluid().getName()));
		} else {
			sb.append("null");
		}
		return sb.toString();
	}
	
	@Override
	public int compareTo(WrappedStack stack) {
		if(isItemStack()) {
			if(stack.isItemStack()) {
				return ACUtil.compareItemStacks(getItemStack(), stack.getItemStack());
			} else {
				return 1;
			}
		} else if(isFluidStack()) {
			if(stack.isItemStack()) {
				return -1;
			} else if(stack.isFluidStack()) {
				return ACUtil.compareFluidStack(getFluidStack(), stack.getFluidStack());
			} else {
				return 1;
			}
		} else if(isNull()) {
			if(!stack.isNull()) {
				return -1;
			} else {
				return 0;
			}
		}
		return 0;
	}

	public static boolean canBeWrapped(Object object) {
		if(object instanceof Item || object instanceof Block) {
			return true;
		} else if(object instanceof ItemStack) {
			return ((ItemStack) object).stackSize > 0;
		} else if(object instanceof Fluid) { 
			return true;
		} else if(object instanceof FluidStack) {
			return ((FluidStack) object).amount > 0;
		} else if(object instanceof WrappedStack) {
			return ((WrappedStack) object).getStackSize() > 0;
		}
		return false;
	}

	public static List<WrappedStack> createList(List<?> objects) {
		ArrayList<WrappedStack> list = new ArrayList<WrappedStack>(objects.size());
		for(Object object : objects) {
			if(canBeWrapped(object)) {
				list.add(new WrappedStack(object));
			}
		}
		return list;
	}
	
	public static List<WrappedStack> createList(Object[] objects) {
		ArrayList<WrappedStack> list = new ArrayList<WrappedStack>(objects.length);
		for(int i = 0; i < objects.length; i++) {
			if(canBeWrapped(objects[i])) {
				list.add(new WrappedStack(objects[i]));
			}
		}
		return list;
	}
	
	public static List<WrappedStack> createList(Object object) {
		ArrayList<WrappedStack> list = new ArrayList<WrappedStack>(1);
		if(canBeWrapped(object)) {
			list.add(new WrappedStack(object));
		}
		return list;
	}
}
