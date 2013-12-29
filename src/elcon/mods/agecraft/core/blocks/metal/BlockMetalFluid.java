package elcon.mods.agecraft.core.blocks.metal;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.core.blocks.BlockFluidMetadata;

public class BlockMetalFluid extends BlockFluidMetadata {

	public BlockMetalFluid(int id) {
		super(id, Material.lava);
		setTickRate(30);
		setCreativeTab(ACCreativeTabs.metals);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
