package org.agecraft.core.blocks.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.AgeCraft;
import org.agecraft.core.registry.TreeRegistry;
import org.agecraft.core.tileentities.TileEntityWorkbench;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadataOverlay;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockWorkbench extends BlockExtendedMetadataOverlay {

	private IIcon iconFront;
	private IIcon iconSide;
	private IIcon iconTop;

	public BlockWorkbench() {
		super(Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(ACCreativeTabs.crafting);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWorkbench();
	}
	
	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityWorkbench.class;
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(stack.getItemDamage()).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "crafting.workbench";
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(AgeCraft.instance, 11, world, x, y, z);
		}
		return true;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = super.getDrops(world, x, y, z, metadata, fortune);
		TileEntityWorkbench tile = (TileEntityWorkbench) getTileEntity(world, x, y, z);
		if(tile.getStackInSlot(0) != null) {
			list.add(tile.getStackInSlotOnClosing(0));
		}
		if(tile.getStackInSlot(1) != null) {
			list.add(tile.getStackInSlotOnClosing(1));
		}
		return list;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return TreeRegistry.instance.get(meta).planks;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getBlockOverlayTexture(int side, int metadata) {
		if(side == 1) {
			return iconTop;
		} else if(side == 2 || side == 4) {
			return iconSide;
		} else if(side == 3 || side == 5) {
			return iconFront;
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		iconFront = iconRegister.registerIcon("agecraft:crafting/workbenchFront");
		iconSide = iconRegister.registerIcon("agecraft:crafting/workbenchSide");
		iconTop = iconRegister.registerIcon("agecraft:crafting/workbenchTop");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			if(TreeRegistry.instance.get(i) != null) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
