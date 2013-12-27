package elcon.mods.agecraft.core.multipart;

import net.minecraft.block.Block;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.IMicroMaterialRender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MicroMaterialColor extends BlockMicroMaterial {

	public MicroMaterialColor(Block block, int meta) {
		super(block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColour(IMicroMaterialRender part) {
		return block().getRenderColor(meta()) << 8 | 0xFF;
	}
}
