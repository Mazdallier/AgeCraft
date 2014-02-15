package org.agecraft;

import com.google.common.io.ByteArrayDataInput;

public interface IACPacketHandler {
	
	public void handlePacketServer(int packetID, ByteArrayDataInput dat);
}
