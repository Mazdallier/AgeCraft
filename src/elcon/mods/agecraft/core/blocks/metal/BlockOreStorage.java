package elcon.mods.agecraft.core.blocks.metal;

import java.util.List;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.blocks.BlockExtendedMetadata;
import elcon.mods.agecraft.core.items.tool.ItemTool;
import elcon.mods.agecraft.lang.LanguageManager;

public class BlockOreStorage extends BlockExtendedMetadata {

	public BlockOreStorage(int id) {
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
		return LanguageManager.getLocalization("metals." + MetalRegistry.metals[stack.getItemDamage()].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "metals.block";
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
		return MetalRegistry.metals[getMetadata(blockAccess, x, y, z)].redstonePower > 0 && side != -1;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return MetalRegistry.metals[getMetadata(blockAccess, x, y, z)].redstonePower;
	}
	
	@Override
	public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face) {
		return MetalRegistry.metals[getMetadata(world, x, y, z)].fireSpreadSpeed;
	}
	
	@Override
	public int getFlammability(IBlockAccess blockAccess, int x, int y, int z, int metadata, ForgeDirection face) {
		return MetalRegistry.metals[getMetadata(blockAccess, x, y, z)].flammability;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return MetalRegistry.metals[getMetadata(world, x, y, z)].blockHardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return MetalRegistry.metals[getMetadata(world, x, y, z)].blockResistance / 5.0F;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return MetalRegistry.metals[meta].block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasBlock) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
