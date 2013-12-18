package elcon.mods.agecraft.core.blocks.stone;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.blocks.IBlockRotated;
import elcon.mods.core.blocks.BlockExtendedMetadata;

public class BlockStoneBrickPillar extends BlockExtendedMetadata implements IBlockRotated {

	private Icon icon;
	private Icon iconTop;
	
	public BlockStoneBrickPillar(int id) {
		super(id, Material.rock);setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "stone.stoneBrick.pillar";
	}
	
	@Override
	public int getBlockRotation(IBlockAccess blockAccess, int x, int y, int z) {
		return getMetadata(blockAccess, x, y, z) & 3;
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta & 60;
	}
	
	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		switch(side) {
		case 0:
		case 1:
			return stack.getItemDamage();
		case 2:
		case 3:
			return stack.getItemDamage() | 1;
		case 4:
		case 5:
			return stack.getItemDamage() | 2;
		}
		return stack.getItemDamage();
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
	public int getRenderColor(int meta) {
		return BlockStone.colors[(meta & 60) / 4];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return BlockStone.colors[(getMetadata(blockAccess, x, y, z) & 60) / 4];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		int direction = meta & 3;
		if(direction == 0) {
			return side == 0 || side == 1 ? iconTop : icon;
		} else if(direction == 1) {
			return side == 2 || side == 3 ? iconTop : icon;
		} else if(direction == 2) {
			return side == 4 || side == 5 ? iconTop : icon;
		}
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:stone/stoneBrickPillar");
		iconTop = iconRegister.registerIcon("agecraft:stone/stoneBrickPillarTop");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(id, 1, i * 4));
		}
	}
}
