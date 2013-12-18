package elcon.mods.agecraft.core.blocks.stone;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.core.ECUtil;
import elcon.mods.core.blocks.BlockExtendedMetadataOverlay;

public class BlockColoredStoneBrick extends BlockExtendedMetadataOverlay {

	public static final String[] types = new String[]{"normal", "cracked", "mossy", "small", "circle", "creeper", "chiseled", "smooth"};
	
	private Icon[] icons = new Icon[8];
	private Icon iconChiseledTop;
	private Icon iconOverlayMossy;
	
	public BlockColoredStoneBrick(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.stone);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "stone.stoneBrick" + types[stack.getItemDamage() & 7];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return BlockColoredStone.colors[(meta & 120) / 8];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return BlockColoredStone.colors[(getMetadata(blockAccess, x, y, z) & 120) / 8];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return (meta & 7) == 6 && (side == 0 || side == 1) ? iconChiseledTop : icons[meta & 7];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int meta) {
		if((meta & 7) == 2) {
			return iconOverlayMossy;
		}
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 16; j++) {
				icons[i] = iconRegister.registerIcon("agecraft:stone/coloredStoneBrick" + ECUtil.firstUpperCase(types[i]));
			}
		}
		iconChiseledTop = iconRegister.registerIcon("agecraft:stone/coloredStoneBrickChiseledTop");
		iconOverlayMossy = iconRegister.registerIcon("agecraft:stone/coloredStoneBrickMossyOverlay");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 16; j++) {
				list.add(new ItemStack(id, 1, i + j * 8));
			}
		}
	}
}
