package elcon.mods.agecraft.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import elcon.mods.core.ElConCore;

public class TeleporterAC extends Teleporter {

	public WorldServer world;
	public int dimension = 0;
	public boolean chests = false;
	
	public TeleporterAC(WorldServer worldServer, int dim, boolean c) {
		super(worldServer);
		world = worldServer;
		dimension = dim;
		chests = c;
	}

	@Override
	public void placeInPortal(Entity entity, double xx, double yy, double zz, float par8) {
		ChunkCoordinates spawn = world.getSpawnPoint();
		int x = spawn.posX;
		int z = spawn.posZ;
		int y = ElConCore.getFirstUncoveredBlock(world, x, z) + 3;
		
		EntityPlayerMP player = null;
		if(entity instanceof EntityPlayerMP) {
			player = (EntityPlayerMP) entity;
			player.playerNetServerHandler.setPlayerLocation(x + 0.5D, MathHelper.floor_double(y), z + 0.5D, entity.rotationYaw, entity.rotationPitch);
		}		
		
		if(player != null && chests) {
			AgeTeleport t = AgeTeleport.teleportList.get(player.username);
			if(t != null) {
				t.placeChests(world, x - 2, y - 1, z - 2);
			}
			AgeTeleport.teleportList.remove(t);
		}
	}
}
