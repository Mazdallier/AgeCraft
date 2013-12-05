package elcon.mods.agecraft.core.blocks.stone;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.core.blocks.BlockMetadataOverlay;

public class BlockStoneCracked extends BlockMetadataOverlay {

	@SideOnly(Side.CLIENT)
	private Icon overlay;
	
	public BlockStoneCracked(int id) {
		super(id, Material.rock);
		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int metadata) {
		return overlay;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		overlay = iconRegister.registerIcon("agecraft:stone/stoneMossyOverlay");
	}
}
