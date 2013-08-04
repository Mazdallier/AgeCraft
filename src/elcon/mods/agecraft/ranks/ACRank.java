package elcon.mods.agecraft.ranks;

import java.util.ArrayList;

import elcon.mods.agecraft.ACColors;

public enum ACRank {

	DEVELOPER("AgeCraft Developer", ACColors.TEXT_COLOUR_BLUE, ACRankManager.developers), 
	DONATOR("AgeCraft Donator", ACColors.TEXT_COLOUR_GREEN, ACRankManager.donators), 
	DEFAULT("", 0, ACRankManager.users);

	public String description;
	public int nameColor;
	private ArrayList<String> members = new ArrayList<String>();

	ACRank(String description, int nameColor, ArrayList<String> members) {
		this.members = members;
		this.nameColor = nameColor;
		this.members = members;
	}
	
	public static ACRank getRankFromMember(String member) {
		for(int i = 0; i < values().length; i++) {
			if(values()[i].getMembers().contains(member.toLowerCase())) {
				return values()[i];
			}
		}
		return DEFAULT;
	}
	
	public static void setRankForMember(String member, ACRank rank) {
		rank.getMembers().add(member.toLowerCase());
	}
	
	public ArrayList<String> getMembers() {
		return members;
	}
}
