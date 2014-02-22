package org.agecraft.core.blocks.stone;

import java.util.List;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.StoneTypeRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockStoneBrick extends BlockExtendedMetadata {

	public static final String[] types = new String[]{"normal", "cracked", "mossy", "small", "circle", "creeper", "chiseled", "smooth"};

	public BlockStoneBrick() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return String.format(super.getLocalizedName(stack), LanguageManager.getLocalization("stone.types." + StoneTypeRegistry.instance.get((stack.getItemDamage() - (stack.getItemDamage() & 7)) / 8).name));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "stone.stoneBrick." + types[stack.getItemDamage() & 7];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if((side == 0 || side == 1) && (meta & 7) == 6) {
			return StoneTypeRegistry.instance.get((meta - (meta & 7)) / 8).iconChiseledTop;
		}
		return StoneTypeRegistry.instance.get((meta - (meta & 7)) / 8).iconsBrick[meta & 7];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < types.length; i++) {
			for(int j = 0; j < StoneTypeRegistry.instance.getAll().length; j++) {
				if(StoneTypeRegistry.instance.get(j) != null) {
					list.add(new ItemStack(item, 1, i + j * 8));
				}
			}
		}
	}
}
