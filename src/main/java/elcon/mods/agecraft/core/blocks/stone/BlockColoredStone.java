package elcon.mods.agecraft.core.blocks.stone;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.Stone;
import elcon.mods.core.blocks.BlockMetadata;

public class BlockColoredStone extends BlockMetadata {

	public static int[] colors = new int[]{
		0xE6E6E6, 0xA65833, 0x9948BB, 0x769FC9, 0xD3D045, 0x72AB32, 0xE989AB, 0x4D4D4D, 0x999999, 0x5C7C89, 0x7752A0, 0x334CB2, 0x5E4D3C, 0x68753E, 0x8D413F, 0x1A1A1A
	};
	
	private Icon icon;
	
	public BlockColoredStone(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "stone.stone";
	}
	
	@Override
	public int idDropped(int meta, Random random, int fortune) {
		return Stone.coloredStoneCracked.blockID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return colors[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return colors[blockAccess.getBlockMetadata(x, y, z)];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:stone/coloredStone");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
}
