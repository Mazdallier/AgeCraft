package elcon.mods.agecraft.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockMetadata extends Block {

	public BlockMetadata(int i, Material material) {
		super(i, material);
	}
	
	public String getLocalizedName(ItemStack stack) {
		return super.getLocalizedName();
	}

	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName();
	}
}
