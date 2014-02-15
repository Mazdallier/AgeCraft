package org.agecraft;

import org.agecraft.creativetabs.CreativeTabAgeAgriculture;
import org.agecraft.creativetabs.CreativeTabAgeCraft;
import org.agecraft.creativetabs.CreativeTabAgePrehistory;
import org.agecraft.creativetabs.CreativeTabArmor;
import org.agecraft.creativetabs.CreativeTabCrafting;
import org.agecraft.creativetabs.CreativeTabMetal;
import org.agecraft.creativetabs.CreativeTabStone;
import org.agecraft.creativetabs.CreativeTabTools;
import org.agecraft.creativetabs.CreativeTabWood;

import net.minecraft.creativetab.CreativeTabs;

public class ACCreativeTabs {

	public static CreativeTabs ageCraft = new CreativeTabAgeCraft(12);
	
	public static CreativeTabs stone = new CreativeTabStone(13, "Stone");
	public static CreativeTabs metals = new CreativeTabMetal(14, "Metals");
	public static CreativeTabs wood = new CreativeTabWood(15, "Wood");
	public static CreativeTabs tools = new CreativeTabTools(16, "Tools");
	public static CreativeTabs armor = new CreativeTabArmor(17, "Armor");
	public static CreativeTabs crafting = new CreativeTabCrafting(18, "Crafting");
	
	public static CreativeTabs prehistoryAge = new CreativeTabAgePrehistory(19, "Prehistory");
	public static CreativeTabs agricultureAge = new CreativeTabAgeAgriculture(20, "Agriculture");
	public static CreativeTabs ancientEgyptAge;
	public static CreativeTabs ancientChinaAge;
	public static CreativeTabs romanGreekAge;
	public static CreativeTabs mediavalAge;
	public static CreativeTabs earlyModernAge;
	public static CreativeTabs industrialAge;
	public static CreativeTabs modernAge;
	public static CreativeTabs futureAge;
}
