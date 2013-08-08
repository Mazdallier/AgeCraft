package elcon.mods.agecraft.core.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStoneLayered extends BlockStone {

	public static final int LAYER_SIZE = 16;
	
	public BlockStoneLayered(int i) {
		super(i);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		blockIcon = Block.stone.getIcon(0, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z);
		switch(meta) {
			case 1: return 0x878787;
			case 2: return 0xA7A7A7;
			case 3: return 0xC7C7C7;
			case 4: return 0xE7E7E7;
			default: return 0xFFFFFF;
		}
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
	public int idDropped(int i, Random random, int j) {
		return Block.cobblestone.blockID;
	}
}
