package org.agecraft.core.clothing;

import io.netty.buffer.ByteBuf;

import org.agecraft.core.AgeCraftCore;

import cpw.mods.fml.common.FMLCommonHandler;
import elcon.mods.elconqore.network.EQMessage;

public class MessageClothingUnlockRequest extends EQMessage {

	public String player;
	public int dimensionID;
	
	public MessageClothingUnlockRequest() {
		
	}
	
	public MessageClothingUnlockRequest(String player, int dimensionID) {
		this.player = player;
		this.dimensionID = dimensionID;
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		writeString(target, player);
		target.writeInt(dimensionID);
	}

	@Override
	public void decodeFrom(ByteBuf source) {
		player = readString(source);
		dimensionID = source.readInt();
	}

	@Override
	public void handle() {
		AgeCraftCore.instance.packetHandler.sendToPlayer(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dimensionID).getPlayerEntityByName(player), new MessageClothingUnlocks(PlayerClothingServer.getPlayerClothing(player)));
	}
}
