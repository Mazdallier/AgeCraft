package elcon.mods.agecraft.core;

import java.util.HashMap;

import elcon.mods.core.color.Color;

public class RankManager {

	public static class Rank {
		
		public String name;

		public int color;
		public String prefix;
		public String postfix;
		
		public Rank(String name, int color, String prefix, String postfix) {
			this.name = name;
			this.color = color;
			this.prefix = prefix;
			this.postfix = postfix;
		}
	}
	
	public static Rank user = new Rank("user", Color.TEXT_COLOR_WHITE, "", "");
	public static Rank developer = new Rank("developer", Color.TEXT_COLOR_WHITE, "", "");
	public static Rank donator = new Rank("donator", Color.TEXT_COLOR_GREEN, Color.TEXT_COLOR_PREFIX_GREEN, "");
	
	public static Rank defaultRank;
	public static HashMap<String, Rank> ranks = new HashMap<String, Rank>();
	public static HashMap<String, Object[]> playerRanks = new HashMap<String, Object[]>();
	
	public static void init() {
		defaultRank = user;
		
		setRank("ElConquistador", developer);
		setRank("DJPrinceCap", developer);
		setRank("RoryGee", developer);
		setRank("poursa", developer);
		setRank("iTz_Terrible", developer);
		
		setPlayerRank("ElConquistador", Color.TEXT_COLOR_BLUE, Color.TEXT_COLOR_PREFIX_BLUE, "");
		setPlayerRank("DJPrinceCap", Color.TEXT_COLOR_GOLD, Color.TEXT_COLOR_PREFIX_GOLD, "");
		setPlayerRank("RoryGee", Color.TEXT_COLOR_WHITE, Color.TEXT_COLOR_PREFIX_WHITE, "");
		setPlayerRank("poursa", Color.TEXT_COLOR_WHITE, Color.TEXT_COLOR_PREFIX_WHITE, "");
		setPlayerRank("iTz_Terrible", Color.TEXT_COLOR_GREEN, Color.TEXT_COLOR_PREFIX_GREEN, "");
	}
	
	public static int getColor(String player) {
		if(playerRanks.containsKey(player)) {
			return (Integer) playerRanks.get(player)[0];
		} else if(ranks.containsKey(player)) {
			return ranks.get(player).color;
		}
		return defaultRank.color;
	}
	
	public static String getPrefix(String player) {
		if(playerRanks.containsKey(player)) {
			return (String) playerRanks.get(player)[1];
		} else if(ranks.containsKey(player)) {
			return ranks.get(player).prefix;
		}
		return defaultRank.prefix;
	}
	
	public static String getPostfix(String player) {
		if(playerRanks.containsKey(player)) {
			return (String) playerRanks.get(player)[2];
		} else if(ranks.containsKey(player)) {
			return ranks.get(player).postfix;
		}
		return defaultRank.postfix;
	}
	
	public static boolean hasRank(String player) {
		return playerRanks.containsKey(player) || ranks.containsKey(player);
	}
	
	public static Rank getRank(String player) {
		if(ranks.containsKey(player)) {
			return ranks.get(player);
		}
		return defaultRank;
	}
	
	public static void setRank(String player, Rank rank) {
		ranks.put(player, rank);
	}
	
	public static void setPlayerRank(String player, int color, String prefix, String postfix) {
		playerRanks.put(player, new Object[]{color, prefix, postfix});
	}
}
