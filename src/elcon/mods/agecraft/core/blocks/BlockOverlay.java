package elcon.mods.agecraft.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOverlay extends Block {

	public BlockOverlay(int i, Material material) {
		super(i, material);
	}
	
	@Override
	public int getRenderType() {
		return 42;
	}

	@SideOnly(Side.CLIENT)
	public Icon getOverlayTextureForBlock(int side, int metadata) {
		return null;
	}
}
