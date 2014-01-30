package elcon.mods.agecraft;

import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

public interface IACPacketHandlerClient {
	
	public void handlePacketClient(int packetID, ByteArrayDataInput dat, World world);
}
