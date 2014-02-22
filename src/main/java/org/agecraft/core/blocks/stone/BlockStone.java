package org.agecraft.core.blocks.stone;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.ACClientProxy;
import org.agecraft.ACCreativeTabs;
import org.agecraft.AgeCraft;
import org.agecraft.core.Stone;
import org.agecraft.core.registry.StoneTypeRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockStone extends BlockMetadata {

	public BlockStone() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return String.format(super.getLocalizedName(stack), LanguageManager.getLocalization("stone.types." + StoneTypeRegistry.instance.get(stack.getItemDamage())));
	}
	
	@Override
	public String getUnlocalizedName() {
		return "stone.stone";
	}
	
	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return Item.getItemFromBlock(Stone.stoneCracked);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return StoneTypeRegistry.instance.get(meta).icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		((ACClientProxy) AgeCraft.proxy).registerBlockIcons(iconRegister);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < StoneTypeRegistry.instance.getAll().length; i++) {
			if(StoneTypeRegistry.instance.get(i) != null) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
