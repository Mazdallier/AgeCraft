package org.agecraft.core.blocks.building;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.Building;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockGrass extends Block {

	private IIcon iconSide;
	private IIcon iconSideOverlay;
	private IIcon iconSideSnow;
	private IIcon iconTop;

	public BlockGrass() {
		super(Material.grass);
		setHardness(0.6F);
		setStepSound(Block.soundTypeGrass);
		setTickRandomly(true);
		setCreativeTab(ACCreativeTabs.building);
	}

	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "building.grass";
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if(!world.isRemote) {
			if(world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2) {
				world.setBlock(x, y, z, Building.dirt);
			} else if(world.getBlockLightValue(x, y + 1, z) >= 9) {
				for(int i = 0; i < 4; i++) {
					int xx = x + random.nextInt(3) - 1;
					int yy = y + random.nextInt(5) - 3;
					int zz = z + random.nextInt(3) - 1;
					if(world.getBlock(xx, yy, zz) == Building.dirt && world.getBlockMetadata(xx, yy, zz) == 0 && world.getBlockLightValue(xx, yy + 1, zz) >= 4 && world.getBlockLightOpacity(xx, yy + 1, zz) <= 2) {
						world.setBlock(xx, yy, zz, Building.grass);
					}
				}
			}
		}
	}
	
	@Override
	public int getRenderType() {
		return 98;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return ColorizerGrass.getGrassColor(0.5D, 1.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return getBlockColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int r = 0;
		int g = 0;
		int b = 0;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int color = blockAccess.getBiomeGenForCoords(x + j, z + i).getBiomeGrassColor(x + j, y, z + i);
				r += (color & 0xFF0000) >> 16;
				g += (color & 0xFF00) >> 8;
				b += color & 0xFF;
			}
		}
		return (r / 9 & 0xFF) << 16 | (g / 9 & 0xFF) << 8 | b / 9 & 0xFF;
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay() {
		return ((BlockGrass) Building.grass).iconSideOverlay;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? iconTop : (side == 0 ? Building.dirt.getBlockTextureFromSide(side) : iconSide);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		if(side == 1) {
			return iconTop;
		} else if(side == 0) {
			return Building.dirt.getBlockTextureFromSide(side);
		} else {
			Material material = blockAccess.getBlock(x, y + 1, z).getMaterial();
			return material != Material.snow && material != Material.craftedSnow ? iconSide : iconSideSnow;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		iconSide = iconRegister.registerIcon("grass_side");
		iconSideOverlay = iconRegister.registerIcon("grass_side_overlay");
		iconSideSnow = iconRegister.registerIcon("grass_side_snowed");
		iconTop = iconRegister.registerIcon("grass_top");
	}
}
