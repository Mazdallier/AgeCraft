package org.agecraft.core.blocks.stone;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.agecraft.ACClientProxy;
import org.agecraft.ACCreativeTabs;
import org.agecraft.AgeCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockMetadata;

public class BlockStone extends BlockMetadata {

	private IIcon icon;
	private IIcon iconCracked;
	private IIcon iconMossy;
	
	public BlockStone() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta == 2 ? 2 : 1;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 0 ? 1.5F : 2.0F;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		switch(stack.getItemDamage()) {
		default:
		case 0:
			return "stone.stone";
		case 1:
			return "stone.stoneCracked";
		case 2:
			return "stone.stoneMossy";
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch(meta) {
		default:
		case 0:
			return icon;
		case 1:
			return iconCracked;
		case 2:
			return iconMossy;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:stone/stone");
		iconCracked = iconRegister.registerIcon("agecraft:stone/stoneCracked");
		iconMossy = iconRegister.registerIcon("agecraft:stone/stoneMossy");
		
		((ACClientProxy) AgeCraft.proxy).registerBlockIcons(iconRegister);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 3; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
