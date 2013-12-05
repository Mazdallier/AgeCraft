package elcon.mods.agecraft.core.blocks.stone;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.core.blocks.BlockExtendedMetadata;

public class BlockStoneBrick extends BlockExtendedMetadata {

	public BlockStoneBrick(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.stone);
	}
}
