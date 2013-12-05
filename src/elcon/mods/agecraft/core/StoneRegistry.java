package elcon.mods.agecraft.core;

import net.minecraft.util.Icon;
import elcon.mods.agecraft.ACLog;

public class StoneRegistry {

	public static class StoneType {
		
		public int id;
		public String name;
		
		public Icon[] stone;
		public Icon[] stoneBrick;
	}
	
	public static StoneType[] stoneTypes = new StoneType[16];
	
	public static void registerTree(StoneType stoneType) {
		if(stoneTypes[stoneType.id] != null) {
			ACLog.warning("[StoneRegistry] Overriding existing stone type (" + stoneTypes[stoneType.id].id + ": " + stoneTypes[stoneType.id].name.toUpperCase() + ") with new stone type (" + stoneType.id + ": " + stoneType.name.toUpperCase() + ")");
		}
		stoneTypes[stoneType.id]= stoneType;
	}
}
