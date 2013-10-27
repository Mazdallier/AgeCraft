package elcon.mods.agecraft.prehistory.blocks;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumStatus;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.blocks.BlockContainerOverlay;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBed;

public class BlockBed extends BlockContainerOverlay {

	public static final int[][] bedBlockMap = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

	public Icon[][] iconsOverlay = new Icon[2][4];
	public Icon[] icons = new Icon[4];

	public BlockBed(int id) {
		super(id, Material.cloth);
		setHardness(0.2F);
		setStepSound(Block.soundClothFootstep);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBed();
	}

	public int getBedSide(int side, int direction) {
		if(side == 0 || side == 1) {
			return 0;
		}
		if(direction == 0) {
			switch(side) {
			case 2:
			case 3:
				return 3;
			case 4:
				return 1;
			case 5:
				return 2;
			}
		} else if(direction == 1) {
			switch(side) {
			case 2:
				return 1;
			case 3:
				return 2;
			case 4:
			case 5:
				return 3;
			}
		} else if(direction == 2) {
			switch(side) {
			case 2:
			case 3:
				return 3;
			case 4:
				return 2;
			case 5:
				return 1;
			}
		} else if(direction == 3) {
			switch(side) {
			case 2:
				return 2;
			case 3:
				return 1;
			case 4:
			case 5:
				return 3;
			}
		}
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(isBedFoot(world, x, y, z)) {
				int direction = getBedDirection(world, x, y, z);
				x += bedBlockMap[direction][0];
				z += bedBlockMap[direction][1];
				if(world.getBlockId(x, y, z) != blockID) {
					return true;
				}
			}
			if(world.provider.canRespawnHere() && world.getBiomeGenForCoords(x, z) != BiomeGenBase.hell) {
				if(((TileEntityBed) getTileEntity(world, x, y, z)).isOccupied) {
					EntityPlayer player1 = null;
					Iterator iterator = world.playerEntities.iterator();
					while(iterator.hasNext()) {
						EntityPlayer player2 = (EntityPlayer) iterator.next();
						if(player2.isPlayerSleeping()) {
							ChunkCoordinates coords = player2.playerLocation;
							if(coords.posX == x && coords.posY == y && coords.posZ == z) {
								player1 = player2;
							}
						}
					}
					if(player1 != null) {
						player.addChatMessage("tile.bed.occupied");
						return true;
					}
					setBedOccupied(world, x, y, z, player, false);
				}
				EnumStatus status = player.sleepInBedAt(x, y, z);
				if(status == EnumStatus.OK) {
					setBedOccupied(world, x, y, z, player, true);
					return true;
				} else {
					if(status == EnumStatus.NOT_POSSIBLE_NOW) {
						player.addChatMessage("tile.bed.noSleep");
					} else if(status == EnumStatus.NOT_SAFE) {
						player.addChatMessage("tile.bed.notSafe");
					}
					return true;
				}
			} else {
				double xx = (double) x + 0.5D;
				double yy = (double) y + 0.5D;
				double zz = (double) z + 0.5D;
				world.setBlockToAir(x, y, z);
				int direction = getBedDirection(world, x, y, z);
				x += bedBlockMap[direction][0];
				z += bedBlockMap[direction][1];
				if(world.getBlockId(x, y, z) == blockID) {
					world.setBlockToAir(x, y, z);
					xx = (xx + (double) x + 0.5D) / 2.0D;
					yy = (yy + (double) y + 0.5D) / 2.0D;
					zz = (zz + (double) z + 0.5D) / 2.0D;
				}
				world.newExplosion((Entity) null, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), 5.0F, true, true);
				return true;
			}
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		int direction = getBedDirection(world, x, y, z);
		if(!isBedFoot(world, x, y, z)) {
			if(world.getBlockId(x - bedBlockMap[direction][0], y, z - bedBlockMap[direction][1]) != blockID) {
				world.setBlockToAir(x, y, z);
			}
		} else if(world.getBlockId(x + bedBlockMap[direction][0], y, z + bedBlockMap[direction][1]) != blockID) {
			if(!world.isRemote) {
				dropBlockAsItem(world, x, y, z, 0, 0);
			}
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public boolean isBed(World world, int x, int y, int z, EntityLivingBase player) {
		return world.getBlockId(x, y, z) == blockID;
	}

	@Override
	public boolean isBedFoot(IBlockAccess blockAccess, int x, int y, int z) {
		return ((TileEntityBed) getTileEntity(blockAccess, x, y, z)).isFoot;
	}

	@Override
	public int getBedDirection(IBlockAccess world, int x, int y, int z) {
		return ((TileEntityBed) getTileEntity(world, x, y, z)).direction;
	}

	@Override
	public void setBedOccupied(World world, int x, int y, int z, EntityPlayer player, boolean occupied) {
		((TileEntityBed) getTileEntity(world, x, y, z)).isOccupied = occupied;
	}

	@Override
	public ChunkCoordinates getBedSpawnPosition(World world, int x, int y, int z, EntityPlayer player) {
		return getNearestEmptyChunkCoordinates(world, x, y, z, 0);
	}

	public ChunkCoordinates getNearestEmptyChunkCoordinates(World world, int x, int y, int z, int found) {
		int direction = getBedDirection(world, x, y, z);
		for(int i = 0; i <= 1; ++i) {
			int minX = x - bedBlockMap[direction][0] * i - 1;
			int minZ = z - bedBlockMap[direction][1] * i - 1;
			int maxX = minX + 2;
			int maxZ = minZ + 2;
			for(int j = minX; j <= maxX; ++j) {
				for(int k = minZ; k <= maxZ; ++k) {
					if(world.doesBlockHaveSolidTopSurface(j, y - 1, k) && !world.getBlockMaterial(j, y, k).isOpaque() && !world.getBlockMaterial(j, y + 1, k).isOpaque()) {
						if(found <= 0) {
							return new ChunkCoordinates(j, y, k);
						}
						found--;
					}
				}
			}
		}
		return null;
	}

	@Override
	public int getMobilityFlag() {
		return 1;
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
		return ((TileEntityBed) getTileEntity(blockAccess, x, y, z)).color;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return iconsOverlay[0][0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return !isBedFoot(blockAccess, x, y, z) ? icons[getBedSide(side, getBedDirection(blockAccess, x, y, z))] : ResourcesCore.emptyTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return iconsOverlay[isBedFoot(blockAccess, x, y, z) ? 0 : 1][getBedSide(side, getBedDirection(blockAccess, x, y, z))];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons[0] = iconRegister.registerIcon("agecraft:ages/prehistory/bedHeadTop");
		icons[1] = iconRegister.registerIcon("agecraft:ages/prehistory/bedHeadSideLeft");
		icons[2] = iconRegister.registerIcon("agecraft:ages/prehistory/bedHeadSideRight");
		icons[3] = iconRegister.registerIcon("agecraft:ages/prehistory/bedHeadEnd");

		iconsOverlay[0][0] = iconRegister.registerIcon("agecraft:ages/prehistory/bedFeetTopOverlay");
		iconsOverlay[0][1] = iconRegister.registerIcon("agecraft:ages/prehistory/bedFeetSideLeftOverlay");
		iconsOverlay[0][2] = iconRegister.registerIcon("agecraft:ages/prehistory/bedFeetSideRightOverlay");
		iconsOverlay[0][3] = iconRegister.registerIcon("agecraft:ages/prehistory/bedFeetEndOverlay");
		iconsOverlay[1][0] = iconRegister.registerIcon("agecraft:ages/prehistory/bedHeadTopOverlay");
		iconsOverlay[1][1] = iconRegister.registerIcon("agecraft:ages/prehistory/bedHeadSideLeftOverlay");
		iconsOverlay[1][2] = iconRegister.registerIcon("agecraft:ages/prehistory/bedHeadSideRightOverlay");
		iconsOverlay[1][3] = iconRegister.registerIcon("agecraft:ages/prehistory/bedHeadEndOverlay");
	}
}
