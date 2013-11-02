package elcon.mods.agecraft.core.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.items.armor.ItemArmor;

public class SlotArmor extends Slot {
	
	public int armorType;
	public ContainerInventory container;

	public SlotArmor(ContainerInventory container, IInventory inventory, int id, int x, int y, int armorType) {
		super(inventory, id, x, y);
		this.container = container;
		this.armorType = armorType;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		Item item = (stack == null ? null : stack.getItem());
		return item != null && item.isValidArmor(stack, armorType, container.getPlayer());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBackgroundIconIndex() {
		return ItemArmor.getBackgroundIcon(armorType);
	}
}
