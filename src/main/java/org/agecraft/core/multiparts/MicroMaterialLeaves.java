package org.agecraft.core.multiparts;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import org.agecraft.core.registry.TreeRegistry;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MaterialRenderHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;

public class MicroMaterialLeaves extends BlockMicroMaterial {

	public MicroMaterialLeaves(Block block, int meta) {
		super(block, meta);
	}

	@Override
	public void renderMicroFace(Vector3 pos, int pass, Cuboid6 bounds) {
		int colour = getColour(pass);
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			colour = getColourClient(pos, pass);
		}
		MaterialRenderHelper.start(pos, pass, icont()).blockColour(colour).lighting().render();
	}

	@SideOnly(Side.CLIENT)
	public int getColourClient(Vector3 pos, int pass) {
		World world = EQUtilClient.getWorld();
		if(world != null && pos != null) {
			if(TreeRegistry.instance.get((meta() - (meta() & 3)) / 4).useBiomeColor) {
				int r = 0;
				int g = 0;
				int b = 0;
				for(int i = -1; i <= 1; i++) {
					for(int j = -1; j <= 1; j++) {
						int color = world.getBiomeGenForCoords((int) pos.x + j, (int) pos.z + i).getBiomeFoliageColor((int) pos.x, (int) pos.y, (int) pos.z);
						r += (color & 0xFF0000) >> 16;
						g += (color & 0xFF00) >> 8;
						b += color & 0xFF;
					}
				}
				return ((r / 9 & 0xFF) << 16 | (g / 9 & 0xFF) << 8 | b / 9 & 0xFF) << 8 | 0xFF;
			}
			return TreeRegistry.instance.get((meta() - (meta() & 3)) / 4).leafColor << 8 | 0xFF;
		} else {
			return block().getRenderColor(meta()) << 8 | 0xFF;
		}
	}

	@Override
	public int getColour(int pass) {
		return block().getRenderColor(meta()) << 8 | 0xFF;
	}
}
