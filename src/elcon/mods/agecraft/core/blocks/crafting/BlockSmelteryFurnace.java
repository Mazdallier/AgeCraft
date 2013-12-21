package elcon.mods.agecraft.core.blocks.crafting;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.Crafting;
import elcon.mods.agecraft.core.Stone;
import elcon.mods.agecraft.core.blocks.stone.BlockColoredStone;
import elcon.mods.agecraft.core.tileentities.TileEntitySmelteryFurnace;
import elcon.mods.core.blocks.BlockStructure;
import elcon.mods.core.lang.LanguageManager;

public class BlockSmelteryFurnace extends BlockStructure {

	private Icon iconOverlay;
	private Icon iconOverlayBurning;
	
	public BlockSmelteryFurnace(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(ACCreativeTabs.crafting);
	}
	
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}

	public String getUnlocalizedName() {
		return "crafting.smelteryFurnace";
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySmelteryFurnace(getStructureName(), blockID);
	}

	@Override
	public String getStructureName() {
		return Crafting.structureSmeltery.name;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if(!world.isRemote) {
			System.out.println("PLACE: " + meta);
			TileEntitySmelteryFurnace tile = (TileEntitySmelteryFurnace) getTileEntity(world, x, y, z);
			tile.color = (byte) (meta - 1);
			System.out.println("FINAL: " + meta);
		}
		return 0;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 115;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntitySmelteryFurnace tile = (TileEntitySmelteryFurnace) getTileEntity(blockAccess, x, y, z);
		return getRenderColor(tile.color + 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return meta == 0 ? 0xFFFFFF : BlockColoredStone.colors[meta - 1];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntitySmelteryFurnace tile = (TileEntitySmelteryFurnace) getTileEntity(blockAccess, x, y, z);
		return getIcon(side, tile.color + 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return meta == 0 ? Stone.stoneBrick.getIcon(side, 0) : Stone.coloredStoneBrick.getIcon(side, 0);
	}

	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntitySmelteryFurnace tile = (TileEntitySmelteryFurnace) getTileEntity(blockAccess, x, y, z);
		return side == 0 || side == 1 ? null : (tile.isBurning() ? iconOverlayBurning : iconOverlay);
	}

	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int meta) {
		return side == 0 || side == 1 ? null : iconOverlay;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconOverlay = iconRegister.registerIcon("agecraft:crafting/smelteryFurnace");
		iconOverlayBurning = iconRegister.registerIcon("agecraft:crafting/smelteryFurnaceBurning");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 17; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
}
