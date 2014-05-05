package org.agecraft.core;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class AgeTeleporter extends Teleporter {

	public final WorldServer world;

	public AgeTeleporter(WorldServer world) {
		super(world);
		this.world = world;
	}

	@Override
	public void placeInPortal(Entity entity, double x, double y, double z, float yaw) {
		ChunkCoordinates chunkcoordinates = world.getSpawnPoint();
		chunkcoordinates.posY = world.getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
		entity.setLocationAndAngles((double) chunkcoordinates.posX, (double) chunkcoordinates.posY, (double) chunkcoordinates.posZ, entity.rotationYaw, entity.rotationPitch);
		entity.motionX = entity.motionY = entity.motionZ = 0.0D;
		
		int blockX = MathHelper.floor_double(entity.posX);
		int blockY = MathHelper.floor_double(entity.posY);
		int blockZ = MathHelper.floor_double(entity.posZ);
		byte b0 = 1;
		byte b1 = 0;
		for(int k = -2; k <= 2; k++) {
			for(int i = -2; i <= 2; i++) {
				for(int j = -1; j < 3; j++) {
					int xx = blockX + i * b0 + k * b1;
					int yy = blockY + j;
					int zz = blockZ + i * b1 - k * b0;
					boolean flag = j < 0;
					world.setBlock(xx, yy, zz, flag ? AgeCraftCore.ageTeleporterBlock : Blocks.air);
				}
			}
		}		
	}
}
