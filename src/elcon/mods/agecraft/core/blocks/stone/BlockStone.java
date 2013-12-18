package elcon.mods.agecraft.core.blocks.stone;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.core.blocks.BlockMetadata;

public class BlockStone extends BlockMetadata {

	private Icon icon;
	private Icon iconCracked;
	private Icon iconMossy;
	
	public BlockStone(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta == 2 ? 2 : 1;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 0 ? 1.5F : 2.0F;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		switch(stack.getItemDamage()) {
		default:
		case 0:
			return "stone.stone";
		case 1:
			return "stone.stoneCracked";
		case 2:
			return "stone.stoneMossy";
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		switch(meta) {
		default:
		case 0:
			return icon;
		case 1:
			return iconCracked;
		case 2:
			return iconMossy;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:stone/stone");
		iconCracked = iconRegister.registerIcon("agecraft:stone/stoneCracked");
		iconMossy = iconRegister.registerIcon("agecraft:stone/stoneMossy");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 3; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
}
