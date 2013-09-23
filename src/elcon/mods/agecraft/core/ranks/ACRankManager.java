package elcon.mods.agecraft.core.ranks;

import java.util.ArrayList;

public class ACRankManager {
	
	public static ArrayList<String> developers = new ArrayList<String>();
	public static ArrayList<String> donators = new ArrayList<String>();
	public static ArrayList<String> users = new ArrayList<String>();
	
	public void init() {
		ACRank.setRankForMember("ElConquistador", ACRank.DEVELOPER);
		ACRank.setRankForMember("DJPrinceCap", ACRank.DEVELOPER);
	}
}
