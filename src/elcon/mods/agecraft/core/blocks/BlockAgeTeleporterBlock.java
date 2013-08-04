package elcon.mods.agecraft.core.blocks;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAgeTeleporterBlock extends Block {
	
	public BlockAgeTeleporterBlock(int i) {
		super(i, Material.iron);
		setResistance(6000000.0F);
		setLightValue(0.5F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon("agecraft:ageTeleporterBlock");
	}
}
