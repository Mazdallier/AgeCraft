package elcon.mods.agecraft;

import net.minecraft.creativetab.CreativeTabs;
import elcon.mods.agecraft.creativetabs.CreativeTabAgeAgriculture;
import elcon.mods.agecraft.creativetabs.CreativeTabAgeCraft;
import elcon.mods.agecraft.creativetabs.CreativeTabAgePrehistory;
import elcon.mods.agecraft.creativetabs.CreativeTabArmor;
import elcon.mods.agecraft.creativetabs.CreativeTabMetal;
import elcon.mods.agecraft.creativetabs.CreativeTabStone;
import elcon.mods.agecraft.creativetabs.CreativeTabTools;
import elcon.mods.agecraft.creativetabs.CreativeTabWood;

public class ACCreativeTabs {

	public static CreativeTabs ageCraft = new CreativeTabAgeCraft(12);
	
	public static CreativeTabs stone = new CreativeTabStone(13, "Stone");
	public static CreativeTabs metals = new CreativeTabMetal(14, "Metals");
	public static CreativeTabs wood = new CreativeTabWood(15, "Wood");
	public static CreativeTabs tools = new CreativeTabTools(16, "Tools");
	public static CreativeTabs armor = new CreativeTabArmor(17, "Armor");
	
	public static CreativeTabs prehistoryAge = new CreativeTabAgePrehistory(18, "Prehistory");
	public static CreativeTabs agricultureAge = new CreativeTabAgeAgriculture(19, "Agriculture");
	public static CreativeTabs ancientEgyptAge;
	public static CreativeTabs ancientChinaAge;
	public static CreativeTabs romanGreekAge;
	public static CreativeTabs mediavalAge;
	public static CreativeTabs earlyModernAge;
	public static CreativeTabs industrialAge;
	public static CreativeTabs modernAge;
	public static CreativeTabs futureAge;
}
