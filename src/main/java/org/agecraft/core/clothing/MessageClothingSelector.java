package org.agecraft.core.clothing;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;

import org.agecraft.core.AgeCraftCore;
import org.agecraft.core.clothing.PlayerClothing.ClothingPiece;

import elcon.mods.elconqore.network.EQMessage;

public class MessageClothingSelector extends EQMessage {

	public String player;
	public ArrayList<ClothingPiece> clothingPieces;
	
	public MessageClothingSelector() {
		clothingPieces = new ArrayList<ClothingPiece>();
	}
	
	public MessageClothingSelector(String player, HashMap<String, ClothingPiece> clothingPieces) {
		this();
		this.player = player;
		this.clothingPieces.addAll(clothingPieces.values());
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		writeString(target, player);
		target.writeInt(clothingPieces.size());
		for(ClothingPiece piece : clothingPieces) {
			writeString(target, piece.typeID);
			writeString(target, piece.categoryID);
			writeString(target, piece.clothingID);
			target.writeInt(piece.getActiveColor());
		}
	}

	@Override
	public void decodeFrom(ByteBuf source) {
		player = readString(source);
		int size = source.readInt();
		for(int i = 0; i < size; i++) {
			ClothingPiece piece = new ClothingPiece(readString(source), readString(source), readString(source), source.readInt());
			clothingPieces.add(piece);
		}
	}

	@Override
	public void handle() {
		PlayerClothing clothing = PlayerClothingServer.getPlayerClothing(player);
		for(ClothingPiece piece : clothingPieces) {
			clothing.addClothingPieceAndWear(piece, piece.getActiveColor());
		}
		//TODO: make the player actually pay
		AgeCraftCore.instance.packetHandler.sendToAllPlayers(new MessageClothingUpdate(clothing));
	}
}
