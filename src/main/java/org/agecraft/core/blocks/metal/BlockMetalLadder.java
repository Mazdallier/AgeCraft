package org.agecraft.core.blocks.metal;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.registry.MetalRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockMetalLadder extends BlockExtendedMetadata {

	private IIcon icon;

	public BlockMetalLadder() {
		super(Material.iron);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(ACCreativeTabs.metals);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 7)) / 8).blockHardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 7)) / 8).blockResistance / 5.0F;
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
		return LanguageManager.getLocalization("metals." + MetalRegistry.instance.get((stack.getItemDamage() - (stack.getItemDamage() & 7)) / 8).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "metals.ladder";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int direction = getMetadata(blockAccess, x, y, z) & 7;
		float f = 0.125F;
		if(direction == 2) {
			setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		}
		if(direction == 3) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		}
		if(direction == 4) {
			setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		if(direction == 5) {
			setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		}
	}

	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return (meta - (meta & 7));
	}

	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		int direction = 0;
		if((direction == 0 || side == 2) && world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH)) {
			direction = 2;
		}
		if((direction == 0 || side == 3) && world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH)) {
			direction = 3;
		}
		if((direction == 0 || side == 4) && world.isSideSolid(x + 1, y, z, ForgeDirection.WEST)) {
			direction = 4;
		}
		if((direction == 0 || side == 5) && world.isSideSolid(x - 1, y, z, ForgeDirection.EAST)) {
			direction = 5;
		}
		return direction | stack.getItemDamage();
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) || world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) || world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) || world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		int meta = getMetadata(world, x, y, z);
		int direction = meta & 7;
		boolean removed = false;

		if(direction == 2 && world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH)) {
			removed = true;
		}
		if(direction == 3 && world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH)) {
			removed = true;
		}
		if(direction == 4 && world.isSideSolid(x + 1, y, z, ForgeDirection.WEST)) {
			removed = true;
		}
		if(direction == 5 && world.isSideSolid(x - 1, y, z, ForgeDirection.EAST)) {
			removed = true;
		}
		if(!removed) {
			dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
		}
		super.onNeighborBlockChange(world, x, y, z, block);
	}

	@Override
	public boolean isLadder(IBlockAccess blockAccess, int x, int y, int z, EntityLivingBase entity) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 103;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return MetalRegistry.instance.get((meta - (meta & 7)) / 8).metalColor;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 7)) / 8).metalColor;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null && MetalRegistry.instance.get(i).hasDoor) {
				list.add(new ItemStack(item, 1, (i * 8)));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:metalLadder");
	}
}
