package elcon.mods.agecraft.core.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.TreeRegistry;

public class BlockWoodFence extends BlockExtendedMetadata {

	public BlockWoodFence(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	public boolean canConnectTo(IBlockAccess blockAccess, int x, int y, int z, int meta) {
		int id = blockAccess.getBlockId(x, y, z);
		if(id == blockID) {
			return meta == getMetadata(blockAccess, x, y, z);
		}
		Block block = Block.blocksList[id];
		return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ? block.blockMaterial != Material.pumpkin : false;
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
	public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 108;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return TreeRegistry.trees[meta].planks;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
