package org.agecraft.core.blocks.metal;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.MetalRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.blocks.IBlockRotated;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockMetalPillar extends BlockExtendedMetadata implements IBlockRotated {

	public BlockMetalPillar() {
		super(Material.iron);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(ACCreativeTabs.metals);
	}
	
	@Override
	public boolean shouldDropItems(World world, int x, int y, int z, int meta, EntityPlayer player, ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() instanceof ItemTool) {
				return ((ItemTool) stack.getItem()).canHarvestBlock(stack, this, meta);
			}
		}
		return false;
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.metals[(stack.getItemDamage() - (stack.getItemDamage() & 3)) / 4].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "metals.pillar";
	}

	@Override
	public int getBlockRotation(IBlockAccess blockAccess, int x, int y, int z) {
		return getMetadata(blockAccess, x, y, z) & 3;
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return (meta - (meta & 3)) / 4;
	}
	
	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		switch(side) {
		default:
		case 0:
		case 1:
			return stack.getItemDamage();
		case 2:
		case 3:
			return stack.getItemDamage() | 1;
		case 4:
		case 5:
			return stack.getItemDamage() | 2;
		}
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 91;
	}
	
	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return blockMaterial.isOpaque() && renderAsNormalBlock();
	}
	
	@Override
	public boolean canConnectRedstone(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 3)) / 4].redstonePower > 0 && side != -1;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 3)) / 4].redstonePower;
	}
	
	@Override
	public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 3)) / 4].fireSpreadSpeed;
	}
	
	@Override
	public int getFlammability(IBlockAccess blockAccess, int x, int y, int z, int metadata, ForgeDirection face) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 3)) / 4].flammability;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 3)) / 4].blockHardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 3)) / 4].blockResistance / 5.0F;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		int direction = meta & 3;
		if(direction == 0 && (side == 0 || side == 1)) {
			return MetalRegistry.metals[(meta - (meta & 3)) / 4].blockPillarTop;
		} else if(direction == 1 && (side == 2 || side == 3)) {
			return MetalRegistry.metals[(meta - (meta & 3)) / 4].blockPillarTop;
		} else if(direction == 2 && (side == 4 || side == 5)) {
			return MetalRegistry.metals[(meta - (meta & 3)) / 4].blockPillarTop;
		}
		return MetalRegistry.metals[(meta - (meta & 3)) / 4].blockPillar;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasBlock) {
				list.add(new ItemStack(id, 1, i * 4));
			}
		}
	}
}
