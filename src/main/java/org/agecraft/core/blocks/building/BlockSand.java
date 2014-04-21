package org.agecraft.core.blocks.building;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.blocks.IBlockFalling;
import org.agecraft.core.entity.EntityFallingBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockMetadata;

public class BlockSand extends BlockMetadata implements IBlockFalling {

	public static String[] types = new String[]{"sand", "redSand", "gravel"};
	private IIcon[] icons = new IIcon[types.length];
	
	public BlockSand() {
		super(Material.sand);
		setHardness(0.5F);
		setStepSound(Block.soundTypeSand);
		setCreativeTab(ACCreativeTabs.building);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "building." + types[stack.getItemDamage()];
	}
	
	@Override
	public int tickRate(World world) {
		return 2;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if(!world.isRemote) {
			fall(world, x, y, z);
		}
	}

	@Override
	public void fall(World world, int x, int y, int z) {
		if(canFall(world, x, y - 1, z) && y >= 0) {
			byte radius = 32;
			if(!BlockFalling.fallInstantly && world.checkChunksExist(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius)) {
				if(!world.isRemote) {
					EntityFallingBlock entity = new EntityFallingBlock(world, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this, world.getBlockMetadata(x, y, z));
					addFallData(world, x, y, z, entity);
					world.spawnEntityInWorld(entity);
				}
			} else {
				world.setBlockToAir(x, y, z);
				while(canFall(world, x, y - 1, z) && y > 0) {
					y--;
				}
				if(y > 0) {
					world.setBlock(x, y, z, this);
				}
			}
		}
	}

	@Override
	public void addFallData(World world, int x, int y, int z, EntityFallingBlock entity) {
	}

	@Override
	public boolean canFall(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if(block.isAir(world, x, y, z)) {
			return true;
		} else if(block == Blocks.fire) {
			return true;
		} else {
			Material material = block.getMaterial();
			return material == Material.water ? true : material == Material.lava;
		}
	}

	@Override
	public DamageSource getFallDamageSource(EntityFallingBlock entity) {
		return DamageSource.fallingBlock;
	}

	@Override
	public void onFallDamage(EntityFallingBlock entity, float fallDistance, int damagePercentage) {
	}

	@Override
	public void onFallEnded(World world, int x, int y, int z, int meta) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		for(int i = 0; i < types.length; i++) {
			icons[i] = iconRegister.registerIcon("agecraft:building/" + types[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < types.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
