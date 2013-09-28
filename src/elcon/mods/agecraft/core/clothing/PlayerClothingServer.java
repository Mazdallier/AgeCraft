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
		clothing.addClothingPiece(new ClothingPiece(0, 0, 0, 0));
		clothing.addClothingPiece(new ClothingPiece(1, 0, 0, 0));
		clothing.addClothingPiece(new ClothingPiece(2, 0, 0, 0));
		clothing.addClothingPiece(new ClothingPiece(3, 0, 0, 0));
		clothing.addClothingPiece(new ClothingPiece(4, 0, 0, 0));
		clothing.addClothingPiece(new ClothingPiece(5, 0, 0, 0));
		clothing.addClothingPiece(new ClothingPiece(6, 0, 0, 0));
		clothing.addClothingPiece(new ClothingPiece(7, 0, 0, 0));
		players.put(clothing.player, clothing);
	}
}
