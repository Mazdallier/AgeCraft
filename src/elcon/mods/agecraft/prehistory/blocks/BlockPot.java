package elcon.mods.agecraft.prehistory.blocks;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityPot;
import elcon.mods.core.lang.LanguageManager;

public class BlockPot extends BlockContainer {

	public boolean renderSolid = false;
	private Icon iconsSide[] = new Icon[2];
	private Icon iconsTop[] = new Icon[2];
	
	public BlockPot(int id) {
		super(id, Material.clay);
		setBlockBounds(0.125F, 0F, 0.125F, 0.875F, 0.625F, 0.875F);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return true;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntityPot tile = (TileEntityPot) blockAccess.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityPot();
		}
		setBlockBounds(0.125F, 0F, 0.125F, 0.875F, tile.hasLid ? 0.6875F : 0.625F, 0.875F);
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.pot.name";
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityPot();
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
		return 202;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if(side == 0) {
			return iconsTop[1];
		} else if(side == 1) {
			return iconsTop[0];
		}
		return iconsSide[0];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		if((side == 0 || side == 1) && renderSolid) {
			return iconsTop[1];
		}
		TileEntityPot tile = (TileEntityPot) blockAccess.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityPot();
		}
		if(side == 0) {
			return iconsTop[1];
		} else if(side == 1) {
			return iconsTop[tile.hasLid ? 1 : 0];
		}
		return iconsSide[tile.hasLid ? 1 : 0];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconsSide[0] = iconRegister.registerIcon("agecraft:ages/prehistory/potSide");
		iconsTop[0] = iconRegister.registerIcon("agecraft:ages/prehistory/potTop");
		iconsSide[1] = iconRegister.registerIcon("agecraft:ages/prehistory/potLidSide");
		iconsTop[1] = iconRegister.registerIcon("agecraft:ages/prehistory/potLidTop");
	}
}
