package elcon.mods.agecraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.prehistory.PrehistoryAge;

public class Age {

	public static Age[] ages = new Age[32];
	
	public static Age prehistory = new PrehistoryAge(0);
	public static Age agriculture;
	public static Age ancientEgypt;
	public static Age ancientChina;
	public static Age romanGreek;
	public static Age medieval;
	public static Age earlyModern;
	public static Age industrial;
	public static Age modern;
	public static Age future;	
	
	public int ageID;
	public String ageName;
	
	public Age(int id, String name) {
		ageID = id;
		ageName = name;
		
		ages[ageID] = this;
	}
	
	public void preInit() {
		
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
	
	public IACPacketHandler getPacketHandler() {
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public IACPacketHandlerClient getPacketHandlerClient() {
		return null;
	}
}
