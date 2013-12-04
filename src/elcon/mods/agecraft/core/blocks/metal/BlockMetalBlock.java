package elcon.mods.agecraft.core.blocks.metal;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.items.tools.ItemTool;
import elcon.mods.core.blocks.BlockExtendedMetadata;
import elcon.mods.core.lang.LanguageManager;

public class BlockMetalBlock extends BlockExtendedMetadata {

	public BlockMetalBlock(int id) {
		super(id, Material.iron);
		setStepSound(Block.soundMetalFootstep);
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
		return LanguageManager.getLocalization("metals." + MetalRegistry.metals[(stack.getItemDamage() - (stack.getItemDamage() & 7)) / 8].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int type = stack.getItemDamage() & 7;
		switch(type) {
		default:
		case 0:
			return "metals.block";
		case 1:
			return "metals.bricks";
		case 2:
			return "metals.smallBricks";
		case 3:
			return "metals.blockCircle";
		case 4:
			return "metals.pillar";
		}
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
		return MetalRegistry.metals[(meta - (meta & 7)) / 8].redstonePower > 0 && side != -1;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 7)) / 8].redstonePower;
	}
	
	@Override
	public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 7)) / 8].fireSpreadSpeed;
	}
	
	@Override
	public int getFlammability(IBlockAccess blockAccess, int x, int y, int z, int metadata, ForgeDirection face) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 7)) / 8].flammability;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 7)) / 8].blockHardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.metals[(meta - (meta & 7)) / 8].blockResistance / 5.0F;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return MetalRegistry.metals[(meta - (meta & 7)) / 8].blocks[meta & 7];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasBlock) {
				for(int j = 0; j < 5; j++) {
					list.add(new ItemStack(id, 1, i * 8 + j));
				}
			}
		}
	}
}
