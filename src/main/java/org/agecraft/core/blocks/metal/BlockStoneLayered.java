package org.agecraft.core.blocks.metal;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.Stone;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.prehistory.PrehistoryAge;
import org.agecraft.prehistory.items.ItemRockPickaxe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockMetadata;

public class BlockStoneLayered extends BlockMetadata {

	public static final int LAYER_SIZE = 16;

	public BlockStoneLayered() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(ACCreativeTabs.stone);
	}

	public void updateHeight(World world, int x, int y, int z, Random random) {
		if(y < 8) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 0);
		} else {
			int i;
			for(i = 0; y > ((i * LAYER_SIZE) + (-2 + random.nextInt(5))); i++) {}
			world.setBlockMetadataWithNotify(x, y, z, i, 0);
		}
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		float hardness = getBlockHardness(world, x, y, z);
		if(hardness < 0.0F) {
			return 0.0F;
		}
		if(!ForgeHooks.canHarvestBlock(this, player, metadata)) {
			return player.getBreakSpeed(this, true, metadata) / hardness / 100F;
		} else {
			return player.getBreakSpeed(this, false, metadata) / hardness / 30F;
		}
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return true;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
		if(canSilkHarvest(world, player, x, y, z, meta) && EnchantmentHelper.getSilkTouchModifier(player)) {
			ItemStack itemstack = createStackedBlock(meta);
			if(itemstack != null) {
				dropBlockAsItem(world, x, y, z, itemstack);
			}
		} else {
			boolean shouldDropItems = false;
			if(player.getCurrentEquippedItem() != null) {
				if(player.getCurrentEquippedItem().getItem() instanceof ItemTool) {
					shouldDropItems = ((ItemTool) player.getCurrentEquippedItem().getItem()).canHarvestBlock(player.getCurrentEquippedItem(), this, meta);
				} else if(player.getCurrentEquippedItem().getItem() instanceof ItemRockPickaxe) {
					shouldDropItems = false;
					int chance = world.rand.nextInt(4);
					if(chance == 2) {
						dropBlockAsItem(world, x, y, z, new ItemStack(PrehistoryAge.rock));
					} else if(chance == 3) {
						dropBlockAsItem(world, x, y, z, new ItemStack(Stone.stone, 1, 1));
					}
				}
			}
			if(shouldDropItems) {
				dropBlockAsItem(world, x, y, z, meta, EnchantmentHelper.getFortuneModifier(player));
			}
		}
	}
	
	@Override
	public String getUnlocalizedName() {
		return "stone.layeredStone";
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(Stone.stone);
	}

	@Override
	public int damageDropped(int meta) {
		return 1;
	}
	
	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(this, 1, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Stone.stone.getIcon(side, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z);
		switch(meta) {
		case 1:
			return 0x878787;
		case 2:
			return 0xA7A7A7;
		case 3:
			return 0xC7C7C7;
		case 4:
			return 0xE7E7E7;
		default:
			return 0xFFFFFF;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		list.add(new ItemStack(item, 1, 0));
	}
}
