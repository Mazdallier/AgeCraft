package elcon.mods.agecraft;

import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IACPacketHandler {

	@SideOnly(Side.CLIENT)
	public void handlePacketClient(int packetID, ByteArrayDataInput dat, World world);
	
	public void handlePacketServer(int packetID, ByteArrayDataInput dat);
}
