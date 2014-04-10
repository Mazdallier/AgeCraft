package org.agecraft.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class AgeTeleporter extends Teleporter {

	public final WorldServer worldInstance;
	public final Random random;
	public final LongHashMap destinationCoordinateCache = new LongHashMap();
	public final List destinationCoordinateKeys = new ArrayList();
	public Block portalBlock;
	public Block portalFrameBlock;

	public AgeTeleporter(WorldServer world) {
		super(world);
		worldInstance = world;
		random = new Random(worldInstance.getSeed());
		portalBlock = Blocks.air;
		portalFrameBlock = Stone.stone;
	}

	@Override
	public void placeInPortal(Entity entity, double x, double y, double z, float yaw) {
		if(worldInstance.provider.dimensionId != 1) {
			if(!placeInExistingPortal(entity, x, y, z, yaw)) {
				makePortal(entity);
				placeInExistingPortal(entity, x, y, z, yaw);
			}
		} else {
			int blockX = MathHelper.floor_double(entity.posX);
			int blockY = MathHelper.floor_double(entity.posY) - 1;
			int blockZ = MathHelper.floor_double(entity.posZ);
			byte range1 = 1;
			byte range2 = 0;
			for(int i = -2; i <= 2; ++i) {
				for(int j = -2; j <= 2; ++j) {
					for(int k = -1; k < 3; ++k) {
						int xx = blockX + j * range1 + i * range2;
						int yy = blockY + k;
						int zz = blockZ + j * range2 - i * range1;
						boolean flag = k < 0;
						worldInstance.setBlock(xx, yy, zz, flag ? portalFrameBlock : Blocks.air);
					}
				}
			}
			entity.setLocationAndAngles((double) blockX, (double) blockY, (double) blockZ, entity.rotationYaw, 0.0F);
			entity.motionX = entity.motionY = entity.motionZ = 0.0D;
		}
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float yaw) {
		short distance = 128;
		double d3 = -1.0D;
		int portalX = 0;
		int portalY = 0;
		int portalZ = 0;
		int blockX = MathHelper.floor_double(entity.posX);
		int blockZ = MathHelper.floor_double(entity.posZ);
		long chunkIntPair = ChunkCoordIntPair.chunkXZ2Int(blockX, blockZ);
		boolean flag = true;
		double locY;
		if(destinationCoordinateCache.containsItem(chunkIntPair)) {
			Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition) destinationCoordinateCache.getValueByKey(chunkIntPair);
			d3 = 0.0D;
			portalX = portalposition.posX;
			portalY = portalposition.posY;
			portalZ = portalposition.posZ;
			portalposition.lastUpdateTime = worldInstance.getTotalWorldTime();
			flag = false;
		} else {
			for(int i = blockX - distance; i <= blockX + distance; i++) {
				double locX = (double) i + 0.5D - entity.posX;
				for(int j = blockZ - distance; j <= blockZ + distance; j++) {
					double locZ = (double) j + 0.5D - entity.posZ;
					for(int k = worldInstance.getActualHeight() - 1; k >= 0; k--) {
						if(worldInstance.getBlock(i, k, j) == portalBlock) {
							while(worldInstance.getBlock(i, k - 1, j) == portalBlock) {
								k--;
							}
							locY = (double) k + 0.5D - entity.posY;
							double dist = locX * locX + locY * locY + locZ * locZ;
							if(d3 < 0.0D || dist < d3) {
								d3 = dist;
								portalX = i;
								portalY = k;
								portalZ = j;
							}
						}
					}
				}
			}
		}
		if(d3 >= 0.0D) {
			if(flag) {
				destinationCoordinateCache.add(chunkIntPair, new Teleporter.PortalPosition(portalX, portalY, portalZ, worldInstance.getTotalWorldTime()));
				destinationCoordinateKeys.add(Long.valueOf(chunkIntPair));
			}
			double d11 = (double) portalX + 0.5D;
			double d6 = (double) portalY + 0.5D;
			locY = (double) portalZ + 0.5D;
			int rotation = -1;

			if(worldInstance.getBlock(portalX - 1, portalY, portalZ) == portalBlock) {
				rotation = 2;
			}
			if(worldInstance.getBlock(portalX + 1, portalY, portalZ) == portalBlock) {
				rotation = 0;
			}
			if(worldInstance.getBlock(portalX, portalY, portalZ - 1) == portalBlock) {
				rotation = 3;
			}
			if(worldInstance.getBlock(portalX, portalY, portalZ + 1) == portalBlock) {
				rotation = 1;
			}
			int teleportDirection = entity.getTeleportDirection();
			if(rotation > -1) {
				int rotationLeft = Direction.rotateLeft[rotation];
				int offsetX1 = Direction.offsetX[rotation];
				int offsetZ1 = Direction.offsetZ[rotation];
				int offsetX2 = Direction.offsetX[rotationLeft];
				int offsetZ2 = Direction.offsetZ[rotationLeft];
				boolean flag1 = !worldInstance.isAirBlock(portalX + offsetX1 + offsetX2, portalY, portalZ + offsetZ1 + offsetZ2) || !worldInstance.isAirBlock(portalX + offsetX1 + offsetX2, portalY + 1, portalZ + offsetZ1 + offsetZ2);
				boolean flag2 = !worldInstance.isAirBlock(portalX + offsetX1, portalY, portalZ + offsetZ1) || !worldInstance.isAirBlock(portalX + offsetX1, portalY + 1, portalZ + offsetZ1);
				if(flag1 && flag2) {
					rotation = Direction.rotateOpposite[rotation];
					rotationLeft = Direction.rotateOpposite[rotationLeft];
					offsetX1 = Direction.offsetX[rotation];
					offsetZ1 = Direction.offsetZ[rotation];
					offsetX2 = Direction.offsetX[rotationLeft];
					offsetZ2 = Direction.offsetZ[rotationLeft];
					int l3 = portalX - offsetX2;
					d11 -= (double) offsetX2;
					int k1 = portalZ - offsetZ2;
					locY -= (double) offsetZ2;
					flag1 = !worldInstance.isAirBlock(l3 + offsetX1 + offsetX2, portalY, k1 + offsetZ1 + offsetZ2) || !worldInstance.isAirBlock(l3 + offsetX1 + offsetX2, portalY + 1, k1 + offsetZ1 + offsetZ2);
					flag2 = !worldInstance.isAirBlock(l3 + offsetX1, portalY, k1 + offsetZ1) || !worldInstance.isAirBlock(l3 + offsetX1, portalY + 1, k1 + offsetZ1);
				}

				float f1 = 0.5F;
				float f2 = 0.5F;
				if(!flag1 && flag2) {
					f1 = 1.0F;
				} else if(flag1 && !flag2) {
					f1 = 0.0F;
				} else if(flag1 && flag2) {
					f2 = 0.0F;
				}
				d11 += (double) ((float) offsetX2 * f1 + f2 * (float) offsetX1);
				locY += (double) ((float) offsetZ2 * f1 + f2 * (float) offsetZ1);
				float f3 = 0.0F;
				float f4 = 0.0F;
				float f5 = 0.0F;
				float f6 = 0.0F;

				if(rotation == teleportDirection) {
					f3 = 1.0F;
					f4 = 1.0F;
				} else if(rotation == Direction.rotateOpposite[teleportDirection]) {
					f3 = -1.0F;
					f4 = -1.0F;
				} else if(rotation == Direction.rotateRight[teleportDirection]) {
					f5 = 1.0F;
					f6 = -1.0F;
				} else {
					f5 = -1.0F;
					f6 = 1.0F;
				}

				double d9 = entity.motionX;
				double d10 = entity.motionZ;
				entity.motionX = d9 * (double) f3 + d10 * (double) f6;
				entity.motionZ = d9 * (double) f5 + d10 * (double) f4;
				entity.rotationYaw = yaw - (float) (teleportDirection * 90) + (float) (rotation * 90);
			} else {
				entity.motionX = entity.motionY = entity.motionZ = 0.0D;
			}
			entity.setLocationAndAngles(d11, d6, locY, entity.rotationYaw, entity.rotationPitch);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean makePortal(Entity entity) {
		byte b0 = 16;
		double d0 = -1.0D;
		int blockX = MathHelper.floor_double(entity.posX);
		int blockY = MathHelper.floor_double(entity.posY);
		int blockZ = MathHelper.floor_double(entity.posZ);
		int l = blockX;
		int i1 = blockY;
		int j1 = blockZ;
		int k1 = 0;
		int l1 = random.nextInt(4);
		int i;
		double locX;
		double locZ;
		int j;
		int k;
		int k3;
		int j3;
		int i4;
		int l3;
		int k4;
		int j4;
		int i5;
		int l4;
		double d3;
		double d4;
		for(i = blockX - b0; i <= blockX + b0; ++i) {
			locX = (double) i + 0.5D - entity.posX;
			for(j = blockZ - b0; j <= blockZ + b0; ++j) {
				locZ = (double) j + 0.5D - entity.posZ;
				label274:
				for(k = worldInstance.getActualHeight() - 1; k >= 0; --k) {
					if(worldInstance.isAirBlock(i, k, j)) {
						while(k > 0 && worldInstance.isAirBlock(i, k - 1, j)) {
							--k;
						}
						for(j3 = l1; j3 < l1 + 4; ++j3) {
							k3 = j3 % 2;
							l3 = 1 - k3;

							if(j3 % 4 >= 2) {
								k3 = -k3;
								l3 = -l3;
							}
							for(i4 = 0; i4 < 3; ++i4) {
								for(j4 = 0; j4 < 4; ++j4) {
									for(k4 = -1; k4 < 4; ++k4) {
										l4 = i + (j4 - 1) * k3 + i4 * l3;
										i5 = k + k4;
										int j5 = j + (j4 - 1) * l3 - i4 * k3;

										if(k4 < 0 && !worldInstance.getBlock(l4, i5, j5).getMaterial().isSolid() || k4 >= 0 && !worldInstance.isAirBlock(l4, i5, j5)) {
											continue label274;
										}
									}
								}
							}
							d4 = (double) k + 0.5D - entity.posY;
							d3 = locX * locX + d4 * d4 + locZ * locZ;

							if(d0 < 0.0D || d3 < d0) {
								d0 = d3;
								l = i;
								i1 = k;
								j1 = j;
								k1 = j3 % 4;
							}
						}
					}
				}
			}
		}
		if(d0 < 0.0D) {
			for(i = blockX - b0; i <= blockX + b0; ++i) {
				locX = (double) i + 0.5D - entity.posX;
				for(j = blockZ - b0; j <= blockZ + b0; ++j) {
					locZ = (double) j + 0.5D - entity.posZ;
					label222:
					for(k = worldInstance.getActualHeight() - 1; k >= 0; --k) {
						if(worldInstance.isAirBlock(i, k, j)) {
							while(k > 0 && worldInstance.isAirBlock(i, k - 1, j)) {
								--k;
							}
							for(j3 = l1; j3 < l1 + 2; ++j3) {
								k3 = j3 % 2;
								l3 = 1 - k3;
								for(i4 = 0; i4 < 4; ++i4) {
									for(j4 = -1; j4 < 4; ++j4) {
										k4 = i + (i4 - 1) * k3;
										l4 = k + j4;
										i5 = j + (i4 - 1) * l3;
										if(j4 < 0 && !worldInstance.getBlock(k4, l4, i5).getMaterial().isSolid() || j4 >= 0 && !worldInstance.isAirBlock(k4, l4, i5)) {
											continue label222;
										}
									}
								}

								d4 = (double) k + 0.5D - entity.posY;
								d3 = locX * locX + d4 * d4 + locZ * locZ;

								if(d0 < 0.0D || d3 < d0) {
									d0 = d3;
									l = i;
									i1 = k;
									j1 = j;
									k1 = j3 % 2;
								}
							}
						}
					}
				}
			}
		}
		int k5 = l;
		int j2 = i1;
		j = j1;
		int l5 = k1 % 2;
		int l2 = 1 - l5;
		if(k1 % 4 >= 2) {
			l5 = -l5;
			l2 = -l2;
		}
		boolean flag;
		if(d0 < 0.0D) {
			if(i1 < 70) {
				i1 = 70;
			}
			if(i1 > worldInstance.getActualHeight() - 10) {
				i1 = worldInstance.getActualHeight() - 10;
			}
			j2 = i1;
			for(k = -1; k <= 1; ++k) {
				for(j3 = 1; j3 < 3; ++j3) {
					for(k3 = -1; k3 < 3; ++k3) {
						l3 = k5 + (j3 - 1) * l5 + k * l2;
						i4 = j2 + k3;
						j4 = j + (j3 - 1) * l2 - k * l5;
						flag = k3 < 0;
						worldInstance.setBlock(l3, i4, j4, flag ? portalFrameBlock : Blocks.air);
					}
				}
			}
		}
		for(k = 0; k < 4; ++k) {
			for(j3 = 0; j3 < 4; ++j3) {
				for(k3 = -1; k3 < 4; ++k3) {
					l3 = k5 + (j3 - 1) * l5;
					i4 = j2 + k3;
					j4 = j + (j3 - 1) * l2;
					flag = j3 == 0 || j3 == 3 || k3 == -1 || k3 == 3;
					worldInstance.setBlock(l3, i4, j4, (Block) (flag ? portalFrameBlock : portalBlock), 0, 2);
				}
			}

			for(j3 = 0; j3 < 4; ++j3) {
				for(k3 = -1; k3 < 4; ++k3) {
					l3 = k5 + (j3 - 1) * l5;
					i4 = j2 + k3;
					j4 = j + (j3 - 1) * l2;
					worldInstance.notifyBlocksOfNeighborChange(l3, i4, j4, worldInstance.getBlock(l3, i4, j4));
				}
			}
		}

		return true;
	}

	@Override
	public void removeStalePortalLocations(long totalWorldTime) {
		if(totalWorldTime % 100L == 0L) {
			Iterator iterator = destinationCoordinateKeys.iterator();
			long j = totalWorldTime - 600L;
			while(iterator.hasNext()) {
				Long olong = (Long) iterator.next();
				Teleporter.PortalPosition position = (Teleporter.PortalPosition) destinationCoordinateCache.getValueByKey(olong.longValue());
				if(position == null || position.lastUpdateTime < j) {
					iterator.remove();
					destinationCoordinateCache.remove(olong.longValue());
				}
			}
		}
	}
}
