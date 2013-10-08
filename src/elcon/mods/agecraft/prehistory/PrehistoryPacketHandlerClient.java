package elcon.mods.agecraft.prehistory;

import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.IACPacketHandlerClient;

@SideOnly(Side.CLIENT)
public class PrehistoryPacketHandlerClient implements IACPacketHandlerClient {

	@Override
	public void handlePacketClient(int packetID, ByteArrayDataInput dat, World world) {
		switch(packetID) {
		case 200:
			handleTileEntityCampfire(world, dat);
			break;
		}
	}

	private void handleTileEntityCampfire(World world, ByteArrayDataInput dat) {
		
	}
}
