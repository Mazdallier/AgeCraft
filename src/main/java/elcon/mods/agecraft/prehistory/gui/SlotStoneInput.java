package elcon.mods.agecraft.prehistory.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.prehistory.items.ItemRock;

public class SlotStoneInput extends Slot {

	public SlotStoneInput(IInventory inv, int par2, int par3, int par4) {
		super(inv, par2, par3, par4);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack != null) {
			return (stack.getItem() instanceof ItemRock);
		}
		return false;
	}
}
