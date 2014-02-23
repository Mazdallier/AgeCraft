package org.agecraft.core.clothing;

import java.util.ArrayList;

import org.agecraft.core.clothing.PlayerClothing.ClothingPiece;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import io.netty.buffer.ByteBuf;
import elcon.mods.elconqore.network.EQMessage;

public class MessageClothingAllUpdate extends EQMessage {

	public ArrayList<PlayerClothing> players;
	
	public MessageClothingAllUpdate() {
		players = new ArrayList<PlayerClothing>();
	}
	
	public MessageClothingAllUpdate(ArrayList<String> playersOnline) {
		this();
		for(String player : playersOnline) {
			players.add(PlayerClothingServer.getPlayerClothing(player));
		}
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		target.writeInt(players.size());
		for(PlayerClothing clothing : players) {
			writeString(target, clothing.player);
			target.writeInt(clothing.clothingPiecesOwned.size());
			for(String type : clothing.clothingPiecesOwned.keySet()) {
				ArrayList<ClothingPiece> pieces = clothing.clothingPiecesOwned.get(type);
				writeString(target, type);
				target.writeInt(pieces.size());
				for(ClothingPiece piece : pieces) {
					writeString(target, piece.categoryID);
					writeString(target, piece.clothingID);
					for(int i = 0; i < 16; i++) {
						target.writeBoolean(piece.colors[i]);
					}
					if(clothing.wearsClothingPiece(piece)) {
						target.writeBoolean(true);
						target.writeInt(clothing.clothingPiecesWornColor.get(type));
					} else {
						target.writeBoolean(false);
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void decodeFrom(ByteBuf source) {
		int playerCount = source.readInt();
		for(int i = 0; i < playerCount; i++) {
			String player = readString(source);
			PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(player);
			if(clothing == null) {
				clothing = new PlayerClothing(player);
			}
			int types = source.readInt();
			for(int j = 0; j < types; j++) {
				String type = readString(source);
				int pieces = source.readInt();
				for(int k = 0; k < pieces; k++) {
					ClothingPiece piece = new ClothingPiece(type, readString(source), readString(source));
					for(int l = 0; l < 16; l++) {
						piece.colors[l] = source.readBoolean();
					}
					clothing.addClothingPiece(piece);
					if(source.readBoolean()) {
						clothing.setCurrentClothingPiece(piece, source.readInt());
					}
				}
			}
			players.add(clothing);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handle() {
		for(PlayerClothing clothing : players) {
			PlayerClothingClient.addPlayerClothing(clothing);
		}
	}
}
