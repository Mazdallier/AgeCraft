package elcon.mods.agecraft.core.clothing;

import java.io.File;
import java.util.HashMap;

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
	}
	
	public static void removePlayerClothing(String username) {
		if(players.containsKey(username)) {
			players.remove(username);
		}
	}
}
