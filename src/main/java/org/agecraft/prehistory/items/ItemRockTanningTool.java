package org.agecraft.prehistory.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.ACCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemRockTanningTool extends Item {

	private IIcon icon;
	
	public ItemRockTanningTool() {
		setMaxDamage(32);
		setMaxStackSize(1);
		setDamage(new ItemStack(this), 1);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.rockTanningTool.name";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/rockTanningTool");
	}
	
	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		return false;
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return 0.0F;
	}
}
