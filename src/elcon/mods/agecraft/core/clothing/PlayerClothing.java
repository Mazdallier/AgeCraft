package elcon.mods.agecraft.core.clothing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;

public class PlayerClothing implements Serializable {

	public static class ClothingPiece implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		public String typeID;
		public String categoryID;
		public String clothingID;
		public boolean[] colors = new boolean[16];
		
		public ClothingPiece(String typeID, String categoryID, String clothingID) {
			this.typeID = typeID;
			this.categoryID = categoryID;
			this.clothingID = clothingID;
		}

		public ClothingPiece(String typeID, String categoryID, String clothingID, int... colors) {
			this.typeID = typeID;
			this.categoryID = categoryID;
			this.clothingID = clothingID;
			for(int i = 0; i < colors.length; i++) {
				if(colors[i] >= 0) {
					this.colors[colors[i]] = true;
				}
			}
		}

		public int getActiveColor() {
			for(int i = 0; i < colors.length; i++) {
				if(colors[i]) {
					return i;
				}
			}
			return 0;
		}
		
		@Override
		public String toString() {
			return "[typeID=" + typeID + ", categoryID=" + categoryID + ", clothigID=" + clothingID + ", activeColor=" + getActiveColor() + "]";
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	public String player;
	public int glTextureID = -1;
	public HashMap<String, ArrayList<ClothingPiece>> clothingPiecesOwned = new HashMap<String, ArrayList<ClothingPiece>>();
	public HashMap<String, ClothingPiece> clothingPiecesWorn = new HashMap<String, ClothingPiece>();
	public HashMap<String, Integer> clothingPiecesWornColor = new HashMap<String, Integer>();
	public String clothingTypeExclusive = "";
	public int clothingColorExclusive = -1;
	
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
			
			if(clothingTypeExclusive.length() > 0) {
				ClothingPiece piece = clothingPiecesWorn.get(clothingTypeExclusive);
				if(piece != null) {
					try {
						BufferedImage image = ImageIO.read(new File(clothingDir, piece.categoryID + File.separator + piece.typeID + File.separator + ClothingRegistry.categories.get(piece.categoryID).getClothing(ClothingRegistry.types.get(piece.typeID), piece.clothingID).getFileName(clothingColorExclusive > 0 ? clothingColorExclusive : 0)));
						g.drawImage(image, 0, 0, null);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				for(ClothingType type : ClothingRegistry.typesSorted.values()) {
					if(type != null) {
						try {
							ClothingPiece piece = clothingPiecesWorn.get(type.name);
							if(piece != null) {
								BufferedImage image = ImageIO.read(new File(clothingDir, piece.categoryID + File.separator + piece.typeID + File.separator + ClothingRegistry.categories.get(piece.categoryID).getClothing(ClothingRegistry.types.get(piece.typeID), piece.clothingID).getFileName(clothingPiecesWornColor.get(type.name))));
								g.drawImage(image, 0, 0, null);
							}
						} catch(Exception e) {
							e.printStackTrace();
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
		boolean shouldAdd = true;
		for(ClothingPiece p : pieces) {
			if(p.typeID == piece.typeID && p.categoryID == piece.categoryID && p.clothingID == piece.clothingID) {
				p.colors[piece.getActiveColor()] = true;
				shouldAdd = false;
			}
		}
		if(shouldAdd) {
			pieces.add(piece);
		}
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

	public boolean wearsClothingPiece(ClothingPiece piece2) {
		for(ClothingPiece piece1 : clothingPiecesWorn.values()) {
			if(piece1.typeID == piece2.typeID && piece1.categoryID == piece2.categoryID && piece1.clothingID == piece2.clothingID) {
				return true;
			}
		}
		return false;
	}

	public PlayerClothing copy() {
		PlayerClothing clothing = new PlayerClothing(player);
		clothing.clothingPiecesOwned.putAll(clothingPiecesOwned);
		clothing.clothingPiecesWorn.putAll(clothingPiecesWorn);
		clothing.clothingPiecesWornColor.putAll(clothingPiecesWornColor);
		return clothing;
	}
}
