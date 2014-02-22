package org.agecraft.core.blocks.metal;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.registry.MetalRegistry;
import org.agecraft.core.registry.MetalRegistry.Metal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadataOverlay;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockStoneOre extends BlockExtendedMetadataOverlay {

	public BlockStoneOre() {
		super(Material.rock);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(ACCreativeTabs.metals);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.instance.get(stack.getItemDamage()).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "metals.ore";
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
	public Item getItemDropped(int meta, Random random, int fortune) {
		return MetalRegistry.instance.get(meta).drop.getItem();
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		Metal metal = MetalRegistry.instance.get(meta);
		return metal.dropMin + (metal.dropMin == metal.dropMax ? 0 : random.nextInt(metal.dropMax - metal.dropMin)) + fortuneBonus(metal, fortune, random);
	}
	
	public int fortuneBonus(Metal metal, int fortune, Random random) {
		if(fortune > 0 && metal.fortune) {
			int bonus = random.nextInt(fortune + 2) - 1;
			if(bonus < 0) {
				bonus = 0;
			}
			return bonus;
		}
		return 0;
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return MetalRegistry.instance.get(meta).drop.getItemDamage();
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return MetalRegistry.instance.get(getMetadata(world, x, y, z)).hardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return MetalRegistry.instance.get(getMetadata(world, x, y, z)).resistance / 5.0F;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.setBlockMetadataWithNotify(x, y, z, 1, 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {		
		if(blockAccess.getBlockMetadata(x, y, z) == 0) {
			int i;
			for(i = 0; y > (i * BlockStoneLayered.LAYER_SIZE); i++) {}
			switch(i)  {
				case 1: return 0x878787;
				case 2: return 0xA7A7A7;
				case 3: return 0xC7C7C7;
				case 4: return 0xE7E7E7;
				default: return 0xFFFFFF;
			}
		}
		return 0xFFFFFF;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.stone.getIcon(side, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getBlockOverlayTexture(int side, int meta) {
		return MetalRegistry.instance.get(meta).ore;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null && MetalRegistry.instance.get(i).hasOre) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
