package elcon.mods.agecraft.core.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.MetalRegistry.Metal;

public class BlockStoneOre extends BlockExtendedMetadataOverlay {

	public BlockStoneOre(int i) {
		super(i, Material.rock);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.metals);
	}
	
	@Override
	public int idDropped(int meta, Random random, int fortune) {
		return MetalRegistry.metals[meta].drop.itemID;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		Metal metal = MetalRegistry.metals[meta];
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
		return MetalRegistry.metals[meta].drop.getItemDamage();
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return MetalRegistry.metals[getMetadata(world, x, y, z)].hardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return MetalRegistry.metals[getMetadata(world, x, y, z)].resistance / 5.0F;
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
	public Icon getIcon(int side, int meta) {
		return Block.stone.getIcon(side, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int meta) {
		return MetalRegistry.metals[meta].ore;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasOre) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
