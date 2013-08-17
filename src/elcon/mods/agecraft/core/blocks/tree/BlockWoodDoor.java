package elcon.mods.agecraft.core.blocks.tree;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.blocks.BlockExtendedMetadata;

public class BlockWoodDoor extends BlockExtendedMetadata {

	public BlockWoodDoor(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	@Override
	public int getMobilityFlag() {
		return 1;
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
		return 110;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				list.add(new ItemStack(id, 1, (i * 64)));
				list.add(new ItemStack(id, 1, (i * 64) | 16));
				list.add(new ItemStack(id, 1, (i * 64) | 32));
				list.add(new ItemStack(id, 1, (i * 64) | 48));
			}
		}
	}
}
