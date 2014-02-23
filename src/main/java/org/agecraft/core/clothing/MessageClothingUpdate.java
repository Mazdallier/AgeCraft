package org.agecraft.core.clothing;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import org.agecraft.core.clothing.PlayerClothing.ClothingPiece;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQMessage;

public class MessageClothingUpdate extends EQMessage {

	public PlayerClothing clothing;
	
	public MessageClothingUpdate() {
	
	}
	
	public MessageClothingUpdate(PlayerClothing clothing) {
		this.clothing = clothing;
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
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

	@Override
	@SideOnly(Side.CLIENT)
	public void decodeFrom(ByteBuf source) {
		String player = readString(source);
		clothing = PlayerClothingClient.getPlayerClothing(player);
		if(clothing == null) {
			clothing = new PlayerClothing(player);
		}
		clothing.clothingPiecesOwned.clear();
		clothing.clothingPiecesWorn.clear();
		clothing.clothingPiecesWornColor.clear();
		int types = source.readInt();
		for(int i = 0; i < types; i++) {
			String type = readString(source);
			int pieces = source.readInt();
			for(int j = 0; j < pieces; j++) {
				ClothingPiece piece = new ClothingPiece(type, readString(source), readString(source));
				for(int k = 0; k < 16; k++) {
					piece.colors[k] = source.readBoolean();
				}
				clothing.addClothingPiece(piece);
				if(source.readBoolean()) {
					clothing.setCurrentClothingPiece(piece, source.readInt());
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handle() {
		PlayerClothingClient.addPlayerClothing(clothing);
	}
}
