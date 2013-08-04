package elcon.mods.agecraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
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
	
	public CreativeTabs tab;
	
	public Age(int id, String name) {
		ageID = id;
		ageName = name;
		
		ages[ageID] = this;
	}
	
	public Age(int id, String name, CreativeTabs creativeTab) {
		this(id, name);
		tab = creativeTab;
	}
	
	public void clientProxy() {
		
	}
	
	public void serverProxy() {
		
	}
	
	public void preInit() {
		
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
	
	public void registerBlockIcons(IconRegister iconRegister) {
		
	}
	
	public void registerItemIcons(IconRegister iconRegister) {
		
	}
}
