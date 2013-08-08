package elcon.mods.agecraft.prehistory;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

import elcon.mods.agecraft.IACPacketHandler;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

public class PrehistoryPacketHandler implements IACPacketHandler {

	@Override
	public void handlePacketClient(int packetID, ByteArrayDataInput dat, World world) {
		switch(packetID) {
		case 200:
			handleTileEntityCampfire(world, dat);
			break;
		}
	}

	@Override
	public void handlePacketServer(int packetID, ByteArrayDataInput dat) {
		
	}
	
	private void handleTileEntityCampfire(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();

		TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityCampfire();
			world.setBlockTileEntity(x, y, z, tile);
		}
		tile.tick = dat.readInt();
		tile.timeLeft = dat.readInt();
		tile.fuel = dat.readInt();
		tile.hasFuel = dat.readBoolean();
		tile.isBurning = dat.readBoolean();
		tile.canBurn = dat.readBoolean();
		tile.logType = dat.readByte();

		tile.hasSpit = dat.readBoolean();
		tile.spitStage = dat.readByte();
		tile.spitDirection = dat.readByte();
		if(dat.readBoolean()) {
			tile.spitStack = new ItemStack(dat.readInt(), 1, dat.readInt());
		}
		tile.cookTime = dat.readInt();
		tile.cooked = dat.readBoolean();
		world.markBlockForUpdate(x, y, z);
	}
}
