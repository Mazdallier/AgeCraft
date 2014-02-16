package org.agecraft.core.blocks.stone;

import java.util.List;

import org.agecraft.ACCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.ECUtil;
import elcon.mods.core.blocks.BlockMetadata;

public class BlockStoneBrick extends BlockMetadata {

	public static final String[] types = new String[]{"normal", "cracked", "mossy", "small", "circle", "creeper", "chiseled", "smooth"};
	
	private Icon icons[] = new Icon[8];
	private Icon iconChiseledTop;
	
	public BlockStoneBrick(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "stone.stoneBrick." + types[stack.getItemDamage()];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if((side == 0 || side == 1) && meta == 6) {
			return iconChiseledTop;
		}
		return icons[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 0; i < types.length; i++) {
			icons[i] = iconRegister.registerIcon("agecraft:stone/stoneBrick" + ECUtil.firstUpperCase(types[i]));
		}
		iconChiseledTop = iconRegister.registerIcon("agecraft:stone/stoneBrickChiseledTop");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < types.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
}
