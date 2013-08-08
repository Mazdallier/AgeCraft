package elcon.mods.agecraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockName extends ItemBlock {

	public ItemBlockName(int i) {
		super(i);
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getLocalizedName(stack);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return Block.blocksList[getBlockID()].getLocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
}
