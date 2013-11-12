package elcon.mods.agecraft.core.clothing;

import java.io.File;
import java.util.HashMap;

import cpw.mods.fml.common.network.PacketDispatcher;
import elcon.mods.agecraft.ACPacketHandler;
import elcon.mods.agecraft.core.clothing.PlayerClothing.ClothingPiece;

public class PlayerClothingServer {

	public static File clothingDir;
	public static File clothingFileDir;
	public static HashMap<String, PlayerClothing> players = new HashMap<String, PlayerClothing>();
	
	public static PlayerClothing getPlayerClothing(String username) {
		if(players.containsKey(username)) {
			return players.get(username);
		}
		return null;
	}
	
	public static void addPlayerClothing(PlayerClothing clothing) {
		players.put(clothing.player, clothing);
		PacketDispatcher.sendPacketToAllPlayers(ACPacketHandler.getClothingUpdatePacket(clothing));
	}
	
	public static void removePlayerClothing(String username) {
		if(players.containsKey(username)) {
			players.remove(username);
		}
	}

	public static void createDefaultClothing(String username) {
		PlayerClothing clothing = new PlayerClothing(username);
		clothing.addClothingPieceAndWear(new ClothingPiece("skin", "general", "steve", 0), 0);
		clothing.addClothingPieceAndWear(new ClothingPiece("hair", "general", "steve", 3), 3);
		clothing.addClothingPieceAndWear(new ClothingPiece("eyes", "general", "steve", 0), 0);
		clothing.addClothingPieceAndWear(new ClothingPiece("mouth", "general", "steve", 1), 1);
		clothing.addClothingPieceAndWear(new ClothingPiece("facialHair", "general", "steve", 3), 3);
		clothing.addClothingPieceAndWear(new ClothingPiece("hat", "general", "steve", 0), 0);
		clothing.addClothingPieceAndWear(new ClothingPiece("shirt", "general", "steve", 12), 12);
		clothing.addClothingPieceAndWear(new ClothingPiece("pants", "general", "steve", 4), 4);
		clothing.addClothingPieceAndWear(new ClothingPiece("boots", "general", "steve", 3), 3);
		players.put(clothing.player, clothing);
	}
}
