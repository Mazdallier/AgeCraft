package elcon.mods.agecraft.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.AgeCraft;

public class BlockClothingSelectorTest extends Block {

	public BlockClothingSelectorTest(int id) {
		super(id, Material.iron);
		setCreativeTab(ACCreativeTabs.ageCraft);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		player.openGui(AgeCraft.instance, 3, world, x, y, z);
		return true;
	}
}
