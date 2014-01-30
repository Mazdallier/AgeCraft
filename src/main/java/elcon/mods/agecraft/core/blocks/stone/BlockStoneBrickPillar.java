package elcon.mods.agecraft.core.blocks.stone;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.blocks.IBlockRotated;
import elcon.mods.core.blocks.BlockMetadata;

public class BlockStoneBrickPillar extends BlockMetadata implements IBlockRotated {

	private Icon icon;
	private Icon iconTop;
	
	public BlockStoneBrickPillar(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public int getBlockRotation(IBlockAccess blockAccess, int x, int y, int z) {
		return blockAccess.getBlockMetadata(x, y, z);
	}
	
	@Override
	public int damageDropped(int meta) {
		return 0;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		switch(side) {
		default:
		case 0:
		case 1:
			return meta;
		case 2:
		case 3:
			return meta | 1;
		case 4:
		case 5:
			return meta | 2;
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "stone.stoneBrick.pillar";
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 91;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if(meta == 0 && (side == 0 || side == 1)) {
			return iconTop;
		} else if(meta == 1 && (side == 2 || side == 3)) {
			return iconTop;
		} else if(meta == 2 && (side == 4 || side == 5)) {
			return iconTop;
		}
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:stone/stoneBrickPillar");
		iconTop = iconRegister.registerIcon("agecraft:stone/stoneBrickPillarTop");
	}
}
