package elcon.mods.agecraft.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

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
			WrappedStack stack = ((WrappedStack) object);
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
	
	public WrappedStack copy() {
		if(getStackSize() == -1 || getWrappedStack() == null) {
			return new WrappedStack();
		} else {
			
		}
	}

	@Override
	public int compareTo(WrappedStack stack) {
		return 0;
	}
}
