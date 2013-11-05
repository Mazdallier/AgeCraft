package elcon.mods.agecraft.core.clothing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import elcon.mods.agecraft.ACLog;

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
	public int glTextureID = -1;
	public HashMap<Integer, ArrayList<ClothingPiece>> clothingPiecesOwned = new HashMap<Integer, ArrayList<ClothingPiece>>();
	public HashMap<Integer, ClothingPiece> clothingPiecesWorn = new HashMap<Integer, ClothingPiece>();
	public HashMap<Integer, Integer> clothingPiecesWornColor = new HashMap<Integer, Integer>();
	public int clothingPieceExclusive = -1;
	
	public PlayerClothing(String player) {
		this.player = player;
	}
	
	public String getClothingFileName() {
		return player + ".png";
	}
	
	public File getClothingFile(File clothingFileDir) {
		return new File(clothingFileDir, getClothingFileName());
	}
	
	public void createClothingFile(File clothingDir, File clothingFileDir) {
		try {
			if(!clothingFileDir.exists()) {
				clothingFileDir.mkdirs();
			}
			File outputFile = new File(clothingFileDir, getClothingFileName());
			BufferedImage outputImage = new BufferedImage(64, 32, BufferedImage.TYPE_INT_ARGB);
			Graphics g = outputImage.getGraphics();
			
			if(clothingPieceExclusive > -1) {
				ClothingPiece piece = clothingPiecesWorn.get(clothingPieceExclusive);
				if(piece != null) {
					BufferedImage image = ImageIO.read(new File(clothingDir, ClothingRegistry.categories[piece.categoryID].name + File.separator + ClothingRegistry.types[piece.typeID].name + File.separator + ClothingRegistry.categories[piece.categoryID].getClothing(ClothingRegistry.types[piece.typeID], piece.clothingID).getFileName(clothingPiecesWornColor.get(clothingPieceExclusive))));
					g.drawImage(image, 0, 0, null);
				}
			} else {
				for(int i = 0; i < ClothingRegistry.typesSorted.length; i++) {
					if(ClothingRegistry.typesSorted[i] != null) {
						ClothingPiece piece = clothingPiecesWorn.get(ClothingRegistry.typesSorted[i].id);
						if(piece != null) {
							BufferedImage image = ImageIO.read(new File(clothingDir, ClothingRegistry.categories[piece.categoryID].name + File.separator + ClothingRegistry.types[piece.typeID].name + File.separator + ClothingRegistry.categories[piece.categoryID].getClothing(ClothingRegistry.types[piece.typeID], piece.clothingID).getFileName(clothingPiecesWornColor.get(ClothingRegistry.typesSorted[i].id))));
							g.drawImage(image, 0, 0, null);
						}
					}
				}
			}
			if(outputFile.exists()) {
				outputFile.delete();
			}
			outputFile.createNewFile();
			ImageIO.write(outputImage, "PNG", outputFile);
			ACLog.info("[Clothing] Created clothing file for " + player);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addClothingPieceAndWear(ClothingPiece piece, int color) {
		addClothingPiece(piece);
		setCurrentClothingPiece(piece, color);
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
	
	public int getCurrentClothingPieceColor(int typeID) {
		if(clothingPiecesWornColor.containsKey(typeID)) {
			return clothingPiecesWornColor.get(typeID);
		}
		return 0;
	}
	
	public void setCurrentClothingPiece(ClothingPiece piece, int color) {
		clothingPiecesWorn.put(piece.typeID, piece);
		clothingPiecesWornColor.put(piece.typeID, color);
	}

	public PlayerClothing copy() {
		PlayerClothing clothing = new PlayerClothing(player);
		clothing.clothingPiecesOwned = (HashMap<Integer, ArrayList<ClothingPiece>>) clothingPiecesOwned.clone();
		clothing.clothingPiecesWorn = (HashMap<Integer, ClothingPiece>) clothingPiecesWorn.clone();
		clothing.clothingPiecesWornColor = (HashMap<Integer, Integer>) clothingPiecesWornColor.clone();
		return clothing;
	}
}
