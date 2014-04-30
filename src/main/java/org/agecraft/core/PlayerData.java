package org.agecraft.core;

import java.io.Serializable;
import java.util.HashMap;

public class PlayerData {

	public static class Player implements Serializable {

		private static final long serialVersionUID = 1L;

		public String name;
		
		public int loginCount = 0;
		
		public Player(String name) {
			this.name = name;
		}
	}
	
	public static HashMap<String, Player> players = new HashMap<String, Player>();
	
	public static boolean hasPlayer(String name) {
		return players.containsKey(name) && players.get(name).loginCount > 0;
	}
	
	public static Player getPlayer(String name) {
		return players.get(name);
	}
	
	public static void addPlayer(Player player) {
		players.put(player.name, player);
	}
	
	public static void removePlayer(String name) {
		players.remove(name);
	}
}
