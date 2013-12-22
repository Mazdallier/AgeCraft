package elcon.mods.agecraft.core.blocks.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.AgeCraft;
import elcon.mods.agecraft.core.Crafting;
import elcon.mods.agecraft.core.Stone;
import elcon.mods.agecraft.core.blocks.stone.BlockColoredStone;
import elcon.mods.agecraft.core.tileentities.TileEntitySmelteryFurnace;
import elcon.mods.core.blocks.BlockStructure;
import elcon.mods.core.lang.LanguageManager;
import elcon.mods.core.tileentities.TileEntityStructure;

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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntitySmelteryFurnace tile = (TileEntitySmelteryFurnace) getTileEntity(world, x, y, z);
		if(world.isRemote) {
			return tile.hasStructure;
		} else {
			if(tile.isMaster()) {
				tile.validateStructure();
			} else {
				if(!tile.hasMasterTile) {
					return false;
				}
				TileEntityStructure tileCenter = tile.getStructureCenter();
				if(tileCenter != null) {
					tileCenter.validateStructure();
				} else {
					tile.hasMasterTile = false;
					return false;
				}
			}
			if(tile.hasStructure()) {
				player.openGui(AgeCraft.instance, 12, world, tile.masterX, tile.masterY, tile.masterZ);
				return true;
			}
			return false;
		}
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		return 0;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntitySmelteryFurnace tile = (TileEntitySmelteryFurnace) getTileEntity(world, x, y, z);
		tile.color = (byte) (stack.getItemDamage() - 1);
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return ((TileEntitySmelteryFurnace) getTileEntity(world, x, y, z)).color + 1;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		int id = idDropped(metadata, world.rand, fortune);
		if(id > 0) {
			list.add(new ItemStack(id, quantityDropped(metadata, fortune, world.rand), getDamageValue(world, x, y, z)));
		}
		return list;
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
