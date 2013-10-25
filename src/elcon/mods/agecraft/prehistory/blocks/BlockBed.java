package elcon.mods.agecraft.prehistory.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.blocks.BlockExtendedMetadataOverlay;

public class BlockBed extends BlockExtendedMetadataOverlay {

	public Icon[][] icons = new Icon[2][3];
	public Icon[] iconsOverlay = new Icon[3];
	
	public BlockBed(int id) {
		super(id, Material.cloth);
		setHardness(0.2F);
		setStepSound(Block.soundClothFootstep);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}
	
	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		return stack.getItemDamage();
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta & 0xFFFFFF;
	}
	
	public int getBedSide(int side) {
		switch(side) {
		case 0:
		case 1:
			return 0;
		case 2:
		case 4:
			return 1;
		case 3:
		case 5:
			return 2;
		default: 
			return 0;
		}
	}
	
	@Override
	public boolean isBed(World world, int x, int y, int z, EntityLivingBase player) {
		return world.getBlockId(x, y, z) == blockID;
	}
	
	@Override
	public boolean isBedFoot(IBlockAccess world, int x, int y, int z) {
		return !isBedHead(getMetadata(world, x, y, z));
	}
	
	public boolean isBedHead(int meta) {
		return (meta & 16777216) >> 24 == 1;
	}
	
	public boolean isBedOccupied(int meta) {
		return (meta & 268435456) >> 27 == 1;
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.bed.name";
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
		return 203;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return getMetadata(blockAccess, x, y, z) & 0xFFFFFF;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return isBedHead(meta) ? iconsOverlay[getBedSide(side)] : ResourcesCore.emptyTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int meta) {
		return icons[(meta & 16777216) >> 24][getBedSide(side)];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons[0][0] = iconRegister.registerIcon("agecraft:prehistory/bedFeetTop");
		icons[0][1] = iconRegister.registerIcon("agecraft:prehistory/bedFeetSide");
		icons[0][2] = iconRegister.registerIcon("agecraft:prehistory/bedFeetEnd");
		icons[1][0] = iconRegister.registerIcon("agecraft:prehistory/bedHeadTop");
		icons[1][1] = iconRegister.registerIcon("agecraft:prehistory/bedHeadSide");
		icons[1][2] = iconRegister.registerIcon("agecraft:prehistory/bedHeadEnd");
		
		iconsOverlay[0] = iconRegister.registerIcon("agecraft:prehistory/bedHeadTopOverlay");
		iconsOverlay[1] = iconRegister.registerIcon("agecraft:prehistory/bedHeadSideOverlay");
		iconsOverlay[2] = iconRegister.registerIcon("agecraft:prehistory/bedHeadEndOverlay");
	}
}
