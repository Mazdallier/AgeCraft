package elcon.mods.agecraft.core.items;

import elcon.mods.agecraft.core.blocks.BlockMetadata;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockMetadata extends ItemBlockWithMetadata {

	public ItemBlockMetadata(BlockMetadata block) {
		super(block.blockID - 256, block);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getLocalizedName(stack);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return ((BlockMetadata) Block.blocksList[getBlockID()]).getLocalizedName(stack);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ((BlockMetadata) Block.blocksList[getBlockID()]).getUnlocalizedName(stack);
	}
	
	@Override
	public String getUnlocalizedName() {
		return ((BlockMetadata) Block.blocksList[getBlockID()]).getUnlocalizedName(new ItemStack(getBlockID(), 1, 0));
	}
}
