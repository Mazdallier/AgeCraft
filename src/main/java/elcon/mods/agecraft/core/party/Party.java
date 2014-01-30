package elcon.mods.agecraft.core.party;

import java.util.ArrayList;

public class Party {

	public String name;
	public String color;
	public String tag;
	
	public PartyStatus status;
	
	public String leader;
	public ArrayList<String> moderators = new ArrayList<String>();
	public ArrayList<String> players = new ArrayList<String>();
	
	public Party(String name, String color, String tag, PartyStatus status) {
		this.name = name;
		this.color = color;
		this.tag = tag;
		this.status = status;
	}
	
	public void setName(String name, String color, String tag) {
		this.name = name;
		this.color = color;
		this.tag = tag;
	}
	
	public void addPlayer(String player) {
		players.add(player);
	}
	
	public void removePlayer(String player) {
		players.remove(player);
	}
	
	public boolean isLeader(String player) {
		return leader.equalsIgnoreCase(player);
	}
	
	public String getLeader() {
		return leader;
	}
	
	public void setLeader(String player) {
		leader = player;
	}
	
	public boolean isModerator(String player) {
		return moderators.contains(player);
	}
	
	public void addModerator(String player) {
		moderators.add(player);
	}
	
	public void removeModerator(String player) {
		moderators.remove(player);
	}
}
