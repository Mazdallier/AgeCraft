package elcon.mods.agecraft.core.clothing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerClothing implements Serializable {

	public static class ClothingPiece implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		public int typeID;
		public int categoryID;
		public int clothingID;
		public boolean[] colors = new boolean[16];
		
		public ClothingPiece(int typeID, int categoryID, int clothingID) {
			this.typeID = typeID;
			this.categoryID = categoryID;
			this.clothingID = clothingID;
		}
		
		public ClothingPiece(int typeID, int categoryID, int clothingID, int... colors) {
			this.typeID = typeID;
			this.categoryID = categoryID;
			this.clothingID = clothingID;
			for(int i = 0; i < colors.length; i++) {
				this.colors[colors[i]] = true;
			}
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	public String player;
	public HashMap<Integer, ArrayList<ClothingPiece>> clothingPiecesOwned = new HashMap<Integer, ArrayList<ClothingPiece>>();
	public HashMap<Integer, ClothingPiece> clothingPiecesWorn = new HashMap<Integer, ClothingPiece>();
	
	public PlayerClothing(String player) {
		this.player = player;
	}
	
	public void addClothingPiece(ClothingPiece piece) {
		ArrayList<ClothingPiece> pieces;
		if(!clothingPiecesOwned.containsKey(piece.typeID)) {
			pieces = new ArrayList<ClothingPiece>();
			clothingPiecesOwned.put(piece.typeID, pieces);
		} else {
			pieces = clothingPiecesOwned.get(piece.typeID);
		}
		pieces.add(piece);
	}
	
	public ArrayList<ClothingPiece> getClothingPieces(int typeID) {
		if(clothingPiecesOwned.containsKey(typeID)) {
			return clothingPiecesOwned.get(typeID);
		}
		return new ArrayList<ClothingPiece>();
	}
	
	public void removeClothingPiece(ClothingPiece piece) {
		if(clothingPiecesOwned.containsKey(piece.typeID)) {
			ArrayList<ClothingPiece> pieces = clothingPiecesOwned.get(piece.typeID);
			pieces.remove(piece);
		}
	}
	
	public ClothingPiece getCurrentClothingPiece(int typeID) {
		if(clothingPiecesWorn.containsKey(typeID)) {
			return clothingPiecesWorn.get(typeID);
		}
		return null;
	}
	
	public void setCurrentClothingPiece(ClothingPiece piece) {
		clothingPiecesWorn.put(piece.typeID, piece);
	}
}
