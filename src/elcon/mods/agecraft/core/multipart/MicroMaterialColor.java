package elcon.mods.agecraft.core.multipart;

import net.minecraft.block.Block;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.IMicroMaterialRender;

public class MicroMaterialColor extends BlockMicroMaterial {

	public int color = 0xFFFFFF;
	
	public MicroMaterialColor(Block block, int meta) {
		super(block, meta);
		color = block.getRenderColor(meta);
	}
	
	@Override
	public int getColour(IMicroMaterialRender part) {
		return color << 8 | 0xFF;
	}	
}
