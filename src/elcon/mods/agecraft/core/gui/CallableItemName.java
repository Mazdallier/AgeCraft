package elcon.mods.agecraft.core.gui;

import java.util.concurrent.Callable;
import net.minecraft.item.ItemStack;

public class CallableItemName implements Callable {
	
	public final ItemStack stack;
	public final InventoryPlayer inventory;

	public CallableItemName(InventoryPlayer inventory, ItemStack stack) {
		this.inventory = inventory;
		this.stack = stack;
	}

	public String callItemDisplayName() {
		return stack.getDisplayName();
	}

	public Object call() {
		return callItemDisplayName();
	}
}
