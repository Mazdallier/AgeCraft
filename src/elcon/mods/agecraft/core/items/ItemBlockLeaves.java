package elcon.mods.agecraft.core.items;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.core.items.ItemBlockExtendedMetadata;

public class ItemBlockLeaves extends ItemBlockExtendedMetadata {

	public ItemBlockLeaves(int id) {
		super(id);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return TreeRegistry.trees[(stack.getItemDamage() - (stack.getItemDamage() & 3)) / 4].leavesColor;
	}
}
